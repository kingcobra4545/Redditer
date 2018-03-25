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
//MainActivity class which hosts the launcher activity
public class MainActivity extends AppCompatActivity  {
    //Weakreferences used to pass the reference of the class to another class
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
        makeRestAPICall();// Call local method to make REST API calls to backend

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Click Listener which is called upon clicking on a button on each UI row item
            //This Listener picks up the id/position of the row item which the user clicked on and performs actions on that item
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewId = view.getId();
                DataModel dataModel;//
                CustomAdapter adapter;
                if (viewId == R.id.up_arrow) {//UpArrow clicked, the button which means user has liked/upVoted the row item is clicked
                    dataModel = (DataModel) parent.getItemAtPosition(position);//Get the data model of row item at the position where the click was fired
                    dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) + 1));//Increment the number of likes associated with this row item which later on gets updated on UI also
                    adapter = (CustomAdapter) list.getAdapter();//Get the adapter associated with the listview
                    adapter.notifyDataSetChanged();//Notify the adapter that the dataset associated with it has updated and the UI also to updated with new dataset
                    likeOrDislikeThisItem(dataModel, RedditAPIConstants.REDDIT_APP_CONSTANTS_LIKE);//Call local method to make network request to send data to backend that the row item is upvoted/downvoted by the user. Boolean parameter is passed to distinguish between upvotes and downvotes.
                } else if (viewId == R.id.down_arrow) {//DownArrow clicked, the button which means user has disliked/downVoted the row item is clicked
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

    private void likeOrDislikeThisItem(DataModel dataModel, String likeOrDislike) {//Local method to make REST calls to notify upvote/downvote to the backend
        CustomVolley customVolley = new CustomVolley( weakReference, weakReferenceList);
        customVolley.sendLikeRequest(likeOrDislike, dataModel, RedditAPIConstants.REDDIT_API_DEFAULT_END_POINT + RedditAPIConstants.REDDIT_API_PUSH_END_POINT_PARAMETER_LIKE);
    }
    private void makeRestAPICall() {
        //Use Volley library to make REST API backend calls, URL and its parameters are set @RedditAPIConstants class.
        //Volley internally makes network calls on different thread and not on UI thread so the UI won't be blocked while making
        //network calls
        CustomVolley customVolley = new CustomVolley( weakReference, weakReferenceList);
        customVolley.makeRequest(RedditAPIConstants.REDDIT_API_DEFAULT_END_POINT + RedditAPIConstants.REDDIT_API_PULL_END_POINT_PARAMETER);
    }


    public void notifyAdapter(ArrayList<DataModel> dataModelFromRest, Context context, WeakReference<ListView> weakReferenceList) {
        //Local method which updates the adapter with updated DataModel inturn updates the UI with the latest dataset
        //Uses Weakreferences to update the initial listview object that was created upon launch of the activity
        adapter = new CustomAdapter(dataModelFromRest, context);
        ListView list = weakReferenceList.get();//Gets the list object that was initially created using weakreference
        list.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        //Called upon clicking three dots button on the taskbar of the app. Inflates the menu with the items included in menu_main xml file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        //The is a listener to the menu items, when the item clicked matches the string given the intended action will be performed
        if(item.toString().equals(RedditAPIConstants.REDDIT_APP_CONSTANTS_ADD_ARTICLE)){
            Intent addArticleActivity = new Intent(MainActivity.this, AddArticleActivity.class);
            startActivityForResult(addArticleActivity,1);
        }
    return true;
    }

    public void revertLike(boolean like,ListView list, JSONObject response, DataModel dataModel) {
        //Local method to revert Likes/Dislike(Upvotes/DownVotes) which was not received by the backend
        Gson gson = new Gson();
        LikesResponse likesResponse = gson.fromJson( response.toString(), LikesResponse.class);
        if(!dataModel.getLikes().equals(likesResponse.getLikes()) && like) {
            //Reduce the number of votes/likes by 1 on local dataset if the like/upvote was not received by the backend
            dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) - 1));
            CustomAdapter adapter = (CustomAdapter) list.getAdapter();
            adapter.notifyDataSetChanged();
        }
        else if ((!dataModel.getLikes().equals(likesResponse.getDislikes())) && !like){
            //Increase the number of votes/likes by 1 on local dataset if the dislike/downvote was not received by the backend
            dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) + 1));
            CustomAdapter adapter = (CustomAdapter) list.getAdapter();
            adapter.notifyDataSetChanged();
        }

    }
    //Revert Likes/Upvotes or dislikes/DownVotes when there is an error in network request
    public void revertLike(String likeOrDislike,ListView list, DataModel dataModel) {
        Log.i(TAG, "Reverting Likes to previous value because error occured");
        //Reduce number of likes/upvotes by 1 if network request was made for like/upvote
        if(likeOrDislike.equals(RedditAPIConstants.REDDIT_APP_CONSTANTS_LIKE))
            dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) - 1));
        else//Increase number of likes/upvotes by 1 if network request was made for dislike/down vote
            dataModel.setLikes(String.valueOf(Integer.parseInt(dataModel.getLikes()) + 1));
        //Notify the adapter of the change in dataset
        CustomAdapter adapter = (CustomAdapter) list.getAdapter();
        adapter.notifyDataSetChanged();
    }
}

