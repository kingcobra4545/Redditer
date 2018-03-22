package com.possystems.kingcobra.redditer.JsonHandling;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.possystems.kingcobra.redditer.DataModels.DataModel;
import com.possystems.kingcobra.redditer.MainActivity;
import com.possystems.kingcobra.redditer.POJO.Articles;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by KingCobra on 22/03/18.
 */

public class RedditJsonResponseParser {
    Context context;
    String response;
    ListView list;
    String TAG = "RedditJsonResponseParser";
    public RedditJsonResponseParser(){}
    public RedditJsonResponseParser(Context context, String response, ListView list){
        this.context = context;
        this.response = response;
        this.list = list;
    }

    public ArrayList<DataModel> responseParser(){
        WeakReference<MainActivity> weakReference = null;
        Gson gson = new Gson();
        ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
        DataModel dataModel;
        try {
            /*String jsonInString = "{'name' : 'mkyong'}";
            Staff staff = gson.fromJson(jsonInString, Staff.class);*/
            JSONArray arrayOfElements = new JSONArray(response);
            for (int i=0;i<arrayOfElements.length();i++){
                Articles articles = gson.fromJson( arrayOfElements.get(i).toString(), Articles.class);
                dataModel = new DataModel(
                        articles.getHeader(),
                        articles.getSubHeader(),
                        articles.getImageURL(),
                        articles.getTimePublishedAt(),
                        articles.getTitle(),
                        articles.getDescription());
                dataModelArrayList.add(dataModel);
                if(i<3) Log.i(TAG, articles.getHeader() + " " + articles.getDescription() + " " + articles.getSubHeader());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, dataModelArrayList.toString());

        MainActivity m = new MainActivity();
        m.notifyAdapter(dataModelArrayList, context,list);

        return dataModelArrayList;

    }
    public  void noResponseHandler(){

    }
}
