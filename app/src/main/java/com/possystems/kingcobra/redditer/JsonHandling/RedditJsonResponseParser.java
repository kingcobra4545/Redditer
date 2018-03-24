package com.possystems.kingcobra.redditer.JsonHandling;

import android.content.Context;
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
    private  Context context;
    private  String response;
    private WeakReference<ListView> weakReferenceList;
    public RedditJsonResponseParser( String response, WeakReference<MainActivity> weakReference, WeakReference<ListView> weakReferenceList){
        this.context = weakReference.get();
        this.response = response;
        this.weakReferenceList = weakReferenceList;
    }

    public ArrayList<DataModel> responseParser(){

        Gson gson = new Gson();
        ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
        DataModel dataModel;
        try {
            JSONArray arrayOfElements = new JSONArray(response);
            for (int i=0;i<arrayOfElements.length();i++){
                Articles articles = gson.fromJson( arrayOfElements.get(i).toString(), Articles.class);
                dataModel = new DataModel(
                        articles.getHeader(),
                        articles.getSubHeader(),
                        articles.getImageURL(), articles.getMainImageURL(),
                        articles.getTimePublishedAt(),
                        articles.getTitle(),
                        articles.getDescription(),
                        articles.getLikes(),
                        articles.getID());
                dataModelArrayList.add(dataModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainActivity m = new MainActivity();
        WeakReference<MainActivity> weakReference  = new WeakReference<MainActivity>(m);
        weakReference.get().notifyAdapter(dataModelArrayList, context, weakReferenceList);
        return dataModelArrayList;

    }
}
