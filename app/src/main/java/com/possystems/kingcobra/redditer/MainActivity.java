package com.possystems.kingcobra.redditer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.possystems.kingcobra.redditer.App_Constants.RedditAPIConstants;
import com.possystems.kingcobra.redditer.DataModels.DataModel;
import com.possystems.kingcobra.redditer.HTTP_Requests.CustomVolley;
import com.possystems.kingcobra.redditer.POJO.LikesResponse;
import com.possystems.kingcobra.redditer.adapters.CustomAdapter;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    WeakReference<MainActivity> weakReference;
    WeakReference<ListView> weakReferenceList;
    String TAG = "MainActivity";
    Context context;
    CustomAdapter adapter;
    ArrayList<DataModel>  dataModelFromRest;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//
        list = findViewById(R.id.list);
        weakReference = new WeakReference<>(this);
        weakReferenceList = new WeakReference<>(list);
        makeRestAPICall();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewId = view.getId();
                DataModel dataModel;//
                CustomAdapter adapter;
                if (viewId == R.id.up_arrow) {
                    dataModel = (DataModel) parent.getItemAtPosition(position);
                    dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) + 1));
                    adapter = (CustomAdapter) list.getAdapter();
                    adapter.notifyDataSetChanged();
                    likeOrDislikeThisItem(dataModel, RedditAPIConstants.REDDIT_APP_CONSTANTS_LIKE);
                } else if (viewId == R.id.down_arrow) {
                    dataModel = (DataModel) parent.getItemAtPosition(position);
                    dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) - 1));
                    adapter = (CustomAdapter) list.getAdapter();
                    adapter.notifyDataSetChanged();
                    likeOrDislikeThisItem(dataModel, RedditAPIConstants.REDDIT_APP_CONSTANTS_DISLIKE);
                } else {
                    Log.i(TAG, "list view item clicked - > " + position);
                }
            }
        });
    }

    private void likeOrDislikeThisItem(DataModel dataModel, String likeOrDislike) {
        CustomVolley customVolley = new CustomVolley( weakReference, weakReferenceList);
        customVolley.sendLikeRequest(likeOrDislike, dataModel, RedditAPIConstants.REDDIT_API_DEFAULT_END_POINT + RedditAPIConstants.REDDIT_API_PUSH_END_POINT_PARAMETER_LIKE);
    }
    private void makeRestAPICall() {
        CustomVolley customVolley = new CustomVolley( weakReference, weakReferenceList);
        customVolley.makeRequest(RedditAPIConstants.REDDIT_API_DEFAULT_END_POINT + RedditAPIConstants.REDDIT_API_PULL_END_POINT_PARAMETER);
    }


    public void notifyAdapter(ArrayList<DataModel> dataModelFromRest, Context context, WeakReference<ListView> weakReferenceList) {
        adapter = new CustomAdapter(dataModelFromRest, context);
        ListView list = weakReferenceList.get();
        list.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.toString().equals(RedditAPIConstants.REDDIT_APP_CONSTANTS_ADD_ARTICLE)){
            Intent addArticleActivity = new Intent(MainActivity.this, AddArticleActivity.class);
            startActivityForResult(addArticleActivity,1);
        }
    return true;
    }

    public void revertLike(boolean like,ListView list, JSONObject response, DataModel dataModel) {
        Gson gson = new Gson();
        LikesResponse likesResponse = gson.fromJson( response.toString(), LikesResponse.class);
        if(!dataModel.getLikes().equals(likesResponse.getLikes()) && like) {
            Log.i(TAG, "Reverting Likes to previous value because values don't match");
            dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) - 1));
            CustomAdapter adapter = (CustomAdapter) list.getAdapter();
            adapter.notifyDataSetChanged();
        }
        else if ((!dataModel.getLikes().equals(likesResponse.getDislikes())) && !like){
            Log.i(TAG, "Reverting disLikes to previous value because values don't match");
//            Log.i(TAG, "Like in device -> " + dataModel.getLikes());
//            Log.i(TAG, "Likes from server -> " + likesResponse.getDislikes());

            dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) + 1));
            CustomAdapter adapter = (CustomAdapter) list.getAdapter();
            adapter.notifyDataSetChanged();
        }

    }

    public void revertLike(String likeOrDislike,ListView list, DataModel dataModel) {
        Log.i(TAG, "Reverting Likes to previous value because error occured");

        if(likeOrDislike.equals("like"))
            dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) - 1));
        else
            dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) + 1));
        CustomAdapter adapter = (CustomAdapter) list.getAdapter();
        adapter.notifyDataSetChanged();
    }
}

