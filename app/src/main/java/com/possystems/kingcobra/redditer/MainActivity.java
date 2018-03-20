package com.possystems.kingcobra.redditer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.possystems.kingcobra.redditer.DataModels.DataModel;
import com.possystems.kingcobra.redditer.adapters.CustomAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    CustomAdapter adapter;
    ArrayList<DataModel> d;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        DataModel d1 = new DataModel("header", "subHeader", "headerIconImageURL", "timePostedAt", "postSourcedFrom", "title");

        d = new ArrayList<>();
        d.add(d1);
        adapter = new CustomAdapter(d, context);
        list = (ListView) findViewById(R.id.list);

        list.setAdapter(adapter);
    }
}
