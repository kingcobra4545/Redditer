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

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by KingCobra on 10/12/17.
 */

public class CustomVolley {
    JSONObject pushResponse;
    ArrayList<DataModel> dataModel = new ArrayList<>();
    Context context;
    ListView list;
    String TAG = "CustomVolley";
    public CustomVolley(Context context){
        this.context = context;
    }
    public CustomVolley(Context context, ListView list){
        this.context = context;
        this.list = list;
    }



    public ArrayList<DataModel> makeRequest(String url){

        Log.i(TAG, "Making a http req using volley");
        RequestQueue queue = Volley.newRequestQueue(context);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.i(TAG, "Got the JSON bud" + "\n - >" + response );
                        if(list!=null) {
                            RedditJsonResponseParser affiliateURLJsonParser = new RedditJsonResponseParser(context, response, list);
                            dataModel = affiliateURLJsonParser.responseParser();
                        }
                        else {
                            Log.i(TAG, "Json sent, response is  - > " + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "No JSON for you");
                RedditJsonResponseParser affiliateURLJsonParser = new RedditJsonResponseParser();
                affiliateURLJsonParser.noResponseHandler();
            }
        }){

        };
        queue.add(stringRequest);
        return dataModel;
    }

    public JSONObject sendRequest(JSONObject data, String url){
        Log.i(TAG, "data going -> " + data);
        Log.i(TAG, "data going to url -> " + url);
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Response after push - > " +  response);
                        pushResponse = response;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //...
                    }
                })
        {

        };
        queue.add(request);
        return pushResponse;
    }

    private void writeDataToDB(ArrayList<HashMap<String, String>> mainList) {

    }


    public JSONObject sendLikeRequest(final DataModel dataModel, final ListView list, String id, final String likes, String url  ) {
//        {"id":"4","likes":"1"}
        String jsonTobeSent = "{\"id\":\"" + id + "\",\"likes\":\"" + 1 + "\"}";
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
                            MainActivity m = new MainActivity();
                            m.revertLike(list, response, dataModel);
                            pushResponse = response;

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MainActivity m = new MainActivity();
                            m.revertLike(list, dataModel);
                        }
                    })
            {

            };

        queue.add(request);
        return pushResponse;
    }
}
