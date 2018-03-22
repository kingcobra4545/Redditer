package com.possystems.kingcobra.redditer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.possystems.kingcobra.redditer.App_Constants.RedditAPIConstants;
import com.possystems.kingcobra.redditer.DataModels.DataModel;
import com.possystems.kingcobra.redditer.HTTP_Requests.CustomVolley;
import com.possystems.kingcobra.redditer.adapters.CustomAdapter;

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

        d = new ArrayList<>();//
        list = (ListView) findViewById(R.id.list);
        dataModelFromRest = makeRestAPICall(list);
        DataModel d1 = new DataModel("header",
                "subHeader",
                "headerIconImageURL",
                "timePostedAt",
                "postSourcedFrom",
                "desc");
        d.add(d1);
        d1 = new DataModel("header1",
                "subHeader1",
                "headerIconImageURL1",
                "timePostedAt1",
                "postSourcedFrom1",
                "desc1");
        d.add(d1);
        d1 = new DataModel("header2",
                "subHeader2",
                "headerIconImageURL2",
                "timePostedAt2",
                "postSourcedFrom2",
                "desc2");
        d.add(d1);

        adapter = new CustomAdapter(dataModelFromRest, context);


        list.setAdapter(adapter);

        //adapterSet();
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
        if(adapter!=null) list.setAdapter(adapter);
        else Log.i(TAG, "Adapter is null");
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

}

