package com.possystems.kingcobra.redditer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView maxCharIndicator,minLengthHeader,minLengthDesc;
    String TAG = "AddArticleActivity", headerFetched, descriptionFetched;
    Button submitButton;
    Context context;

    private final TextWatcher headerTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length()<4) {
                maxCharIndicator.setVisibility(View.VISIBLE);
                submitButton.setEnabled(true);
            }
            else {
                minLengthHeader.setVisibility(View.INVISIBLE);

            }


        }

        public void afterTextChanged(Editable s) {
        }
    };
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()<10)minLengthDesc.setVisibility(View.VISIBLE);
            else minLengthDesc.setVisibility(View.INVISIBLE);
            //This sets a textview to the current length
            if (s.length()>255) {
                maxCharIndicator.setVisibility(View.VISIBLE);
                maxCharIndicator.setText("Maximum text length reached " + s.length());
                submitButton.setEnabled(false);
            }
            else {
                maxCharIndicator.setVisibility(View.INVISIBLE);
                submitButton.setEnabled(true);
            }


        }

        public void afterTextChanged(Editable s) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        context = getApplicationContext();
        minLengthDesc = findViewById(R.id.min_length_desc_text);
        minLengthHeader = findViewById(R.id.min_length_header_text);
        headerText = findViewById(R.id.header);
        descriptionText = findViewById(R.id.desc);
        descriptionText.addTextChangedListener(mTextEditorWatcher);
        headerText.addTextChangedListener(headerTextEditorWatcher);
        maxCharIndicator = findViewById(R.id.max_char_indicator);
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerFetched = headerText.getText().toString();
                descriptionFetched = descriptionText.getText().toString();
                if(headerFetched.length()<4) minLengthHeader.setVisibility(View.VISIBLE);
                else minLengthHeader.setVisibility(View.INVISIBLE);
                if(descriptionFetched.length()<10) minLengthDesc.setVisibility(View.VISIBLE);
                else minLengthDesc.setVisibility(View.INVISIBLE);
                if (headerFetched.length() < 4 || descriptionFetched.length() < 10) {

                }
                else{
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
                obj.setImageSize(" ");
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
            }
            }
        });

    }
}
