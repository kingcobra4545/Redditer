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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    Context context;
    CustomAdapter adapter;
    ArrayList<DataModel> d, dataModelFromRest;
    ListView list;
    VerticalViewPager verticalViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        d = new ArrayList<>();
        adapter = new CustomAdapter(d, context);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        dataModelFromRest = makeRestAPICall(list);
        d = dataModelFromRest;
        adapter.notifyDataSetChanged();



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "POsition-> " + position);
                DataModel dataModel = (DataModel) parent.getItemAtPosition(position);
                Log.i(TAG, dataModel.getHeader());
                dataModel.getLikes();
                dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) + 1));
                CustomAdapter adapter = (CustomAdapter) list.getAdapter();
                adapter.notifyDataSetChanged();
                boolean success;
                 likeThisItem(dataModel);
                /*if (!success) {
                    Log.i(TAG, "Reverting Likes to previous value");
                    dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) - 1));
                }*/
            }
        });

        //adapterSet();
    }

    private void likeThisItem(DataModel dataModel) {
        JSONObject response = null;
        CustomVolley customVolley = new CustomVolley(context, list);
        customVolley.sendLikeRequest( dataModel, list,dataModel.getID(), dataModel.getLikes(), RedditAPIConstants.REDDIT_API_DEFAULT_END_POINT + RedditAPIConstants.REDDIT_API_PUSH_END_POINT_PARAMETER_LIKE);
        /*Gson gson = new Gson();
        LikesResponse likesResponse = gson.fromJson( response.toString(), LikesResponse.class);
        likesResponse.getID();
        likesResponse.getLikes();*/
        /*if(!dataModel.getLikes().equals(likesResponse.getLikes())) return false;
        return true;*/

    }

    private ArrayList<DataModel> makeRestAPICall(ListView list) {
        ArrayList<DataModel> dataModel = new ArrayList<>();
        CustomVolley customVolley = new CustomVolley(context, list);
        customVolley.makeRequest(RedditAPIConstants.REDDIT_API_DEFAULT_END_POINT + RedditAPIConstants.REDDIT_API_PULL_END_POINT_PARAMETER);

        return dataModel;
    }

    /*private void adapterSet() {
        CustomAdapter customAdapter;


    }*/

    public void notifyAdapter(ArrayList<DataModel> dataModelFromRest, Context context, ListView list) {
        adapter = new CustomAdapter(dataModelFromRest, context);
//        list = (ListView) findViewById(R.id.list);
        /*if(adapter!=null) list.setAdapter(adapter);
        else Log.i(TAG, "Adapter is null");*/
        list.setAdapter(adapter);
//        ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
//        adapter.notifyDataSetChanged();
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.toString().equals("Add Article")){
            Intent addArticleActivity = new Intent(MainActivity.this, AddArticleActivity.class);
            startActivityForResult(addArticleActivity,1);
        }

    return true;
    }

    public void revertLike(ListView list, JSONObject response, DataModel dataModel) {
        Gson gson = new Gson();
        LikesResponse likesResponse = gson.fromJson( response.toString(), LikesResponse.class);
        likesResponse.getID();
        likesResponse.getLikes();
        if(!dataModel.getLikes().equals(likesResponse.getLikes())) {
            Log.i(TAG, "Reverting Likes to previous value because values dont match");
            dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) - 1));
            CustomAdapter adapter = (CustomAdapter) list.getAdapter();
            adapter.notifyDataSetChanged();
        }

    }

    public void revertLike(ListView list, DataModel dataModel) {
        Log.i(TAG, "Reverting Likes to previous value because error occured");
        dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) - 1));
        CustomAdapter adapter = (CustomAdapter) list.getAdapter();
        adapter.notifyDataSetChanged();

    }
}

