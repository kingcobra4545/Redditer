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
    ListView list;
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
        Log.i(TAG, "data going -> " + data);
        Log.i(TAG, "data going to url -> " + url);
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
            jsonTobeSent = "{\"id\":\"" + id + "\",\"likes\":\"" + 1 + "\"}";
        else if(likeOrDislike.equals("dislike"))
            jsonTobeSent = "{\"id\":\"" + id + "\",\"dislikes\":\"" + 1 + "\"}";
        Log.i(TAG, "json being sent is --->>" + jsonTobeSent + "<--");

        Log.i(TAG, "Making a http req using volley");
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = null;
        JSONObject data = null;
        try {
            data = new JSONObject(jsonTobeSent);
            Log.i(TAG, "Jsoned - > " +  data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            request = new JsonObjectRequest(
                    Request.Method.POST,url, data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(TAG, "Response after push - > " +  response);
//                            {"id":"3","dislikes":"3"}

                                if(String.valueOf(response).contains("dislikes")){
                                    MainActivity m = new MainActivity();
                                    m.revertLike(false,list, response, dataModel);
                                }
                                else {
                                    MainActivity m = new MainActivity();
                                    m.revertLike(true,list, response, dataModel);
                                }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MainActivity m = new MainActivity();
                            m.revertLike(likeOrDislike, list, dataModel);
                        }
                    })
            {

            };
        queue.add(request);
    }
}
