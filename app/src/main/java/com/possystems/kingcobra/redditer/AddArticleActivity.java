package com.possystems.kingcobra.redditer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.possystems.kingcobra.redditer.App_Constants.RedditAPIConstants;
import com.possystems.kingcobra.redditer.HTTP_Requests.CustomVolley;
import com.possystems.kingcobra.redditer.POJO.Articles;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddArticleActivity extends AppCompatActivity {
    EditText headerText, descriptionText;
    String TAG = "AddArticleActivity", headerFetched, descriptionFetched;
    Button submitButton;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        context = getApplicationContext();
        headerText = findViewById(R.id.header);
        descriptionText = findViewById(R.id.desc);
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerFetched = headerText.getText().toString();
                descriptionFetched = descriptionText.getText().toString();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                Gson gson = new Gson();
                Articles obj = new Articles();
                obj.setHeader(headerFetched);
                obj.setDescription(descriptionFetched);
                obj.setSubHeader(" ");
                obj.setImageURL(" ");
                obj.setTimePublishedAt(formattedDate);
                obj.setTitle(" ");
                obj.setUpdatedTime(" ");
                obj.setQueryClass(" ");
                obj.setImageSize("large");
                obj.setQuery(" ");

                // 2. Java object to JSON, and assign to a String
                String jsonInString = gson.toJson(obj);
                Log.i(TAG, "Jsoned--> " + jsonInString);
                Toast.makeText(context, "Article Posted", Toast.LENGTH_LONG).show();

                CustomVolley volley = new CustomVolley(context);
                try {
                    volley.sendRequest(new JSONObject(jsonInString), RedditAPIConstants.REDDIT_API_DEFAULT_END_POINT + RedditAPIConstants.REDDIT_API_PUSH_END_POINT_PARAMETER);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //finish();
            }
        });

    }
}
