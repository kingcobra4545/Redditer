package com.possystems.kingcobra.redditer.HTTP_Requests;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.possystems.kingcobra.redditer.DataModels.DataModel;
import com.possystems.kingcobra.redditer.JsonHandling.RedditJsonResponseParser;
import com.possystems.kingcobra.redditer.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * Created by KingCobra on 10/12/17.
 */

public class CustomVolley {
    private ArrayList<DataModel> dataModel = new ArrayList<>();
    private Context context;
    private String TAG = "CustomVolley";
    private WeakReference<MainActivity> weakReference;
    private WeakReference<ListView> weakReferenceList;
    private ListView list;
    public CustomVolley(Context context){
        this.context = context;
    }
    public CustomVolley( WeakReference<MainActivity> weakReference, WeakReference<ListView> weakReferenceList){
        this.context = weakReference.get();
        this.weakReference = weakReference;
        this.weakReferenceList = weakReferenceList;
        this.list = weakReferenceList.get();
    }
    public void makeRequest(String url){

        Log.i(TAG, "Making a http req using volley");
        RequestQueue queue = Volley.newRequestQueue(context);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "Got the JSON bud" + "\n - >" + response );
                        RedditJsonResponseParser affiliateURLJsonParser = new RedditJsonResponseParser(response, weakReference, weakReferenceList);
                        dataModel = affiliateURLJsonParser.responseParser();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "No JSON for you");
            }
        }){};
        queue.add(stringRequest);
    }
    public void sendRequest(JSONObject data, String url){
//        Log.i(TAG, "data going -> " + data);
//        Log.i(TAG, "data going to url -> " + url);
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Response after push - > " +  response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}})
        {

        };
        queue.add(request);
    }


    public void sendLikeRequest(final String likeOrDislike, final DataModel dataModel, String url  ) {
        String id = dataModel.getID();
        String jsonTobeSent = null;
        if(likeOrDislike.equals("like"))
            jsonTobeSent = "{\"id\":\"" + id + "\",\"likes\":\"" + 1 + "\"}";//Json format that the backend understands for upvotes/likes Endpoint
        else if(likeOrDislike.equals("dislike"))
            jsonTobeSent = "{\"id\":\"" + id + "\",\"dislikes\":\"" + 1 + "\"}";//Json format that the backend understands for downvotes/dislikes Endpoint

        RequestQueue queue = Volley.newRequestQueue(context);//Volley requests are queued in this object and are sent one by one
        JsonObjectRequest request;//Object used to send as a JsonObject through Volley
        JSONObject data = null;//Data that is added into the above object and sent over the network
        try {
            data = new JSONObject(jsonTobeSent);
//            Log.i(TAG, "Jsoned - > " +  data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            request = new JsonObjectRequest(
                    Request.Method.POST,url, data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Response received from backend after upvote/downvote notification is sent to backend

                                if(String.valueOf(response).contains("dislikes")){
                                    //If response contains dislikes and if there is a mis-match between local value and server value then the recent update on local device will be reverted back
                                    //Similarly if there is no mis-match then nothing is done
                                    MainActivity m = new MainActivity();
                                    m.revertLike(false,list, response, dataModel);
                                }
                                else {
                                    //If response contains likes and if there is a mis-match between local value and server value then the recent update on local device will be reverted back
                                    //Similarly if there is no mis-match then nothing is done
                                    MainActivity m = new MainActivity();
                                    m.revertLike(true,list, response, dataModel);
                                }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //If there is an error then revert back like/dislike on local device determined by the boolean value 'likeOrDislike'
                            MainActivity m = new MainActivity();
                            m.revertLike(likeOrDislike, list, dataModel);
                        }
                    })
            {

            };
        queue.add(request);//Queue that makes requests according to the items queued up in the order that it was added following a FIFO method
    }
}
