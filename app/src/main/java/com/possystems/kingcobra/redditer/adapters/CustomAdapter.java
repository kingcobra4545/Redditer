package com.possystems.kingcobra.redditer.adapters;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.possystems.kingcobra.redditer.DataModels.DataModel;
import com.possystems.kingcobra.redditer.DebuggingTools.Logger;
import com.possystems.kingcobra.redditer.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



/**
 * Created by KingCobra on 24/11/17.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{
    String TAG = "CustomAdapter";

    private ArrayList<DataModel> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle, txtDesc, txtAuthor, txtSourceAndTime;
        TextView txtType;
        TextView txtVersion, vehicleUsed;
        ImageView info;
        String url;
        Button optionButton;
    }

    /*private static final CustomAdapter instance = new CustomAdapter();

    private CustomAdapter(){}

    public static CustomAdapter getInstance(){
        return instance;
    }*/

    public CustomAdapter(ArrayList<DataModel> data, Context context) {

        super(context, R.layout.main_row_item, data);
        this.dataSet = data;
        this.mContext=context;
        Logger.i(TAG, "Adapter Called" + "\n Data Size - > " + data.size());

    }
    /*@Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
    {

        int pos=(Integer) v.getTag();
        Logger.i(TAG, "pos -- " + pos);
        Logger.i(TAG, "position -- " + position);
        // Get Person "behind" the clicked item
        //Person p = (Person) myListView.getItemAtPosition(position);

        // Logger the fields to check if we got the info we want
        //Logger.i("SomeTag", "Persons name: " + p.name);
        //Logger.i("SomeTag", "Persons id : " + p.person_id);

        // Do something with the data. For example, passing them to a new Activity

        *//*Intent i = new Intent(MainActivity.this, NewActivity.class);
        i.putExtra("person_id", p.person_id);
        i.putExtra("person_name", p.name);

        MainActivity.this.startActivity(i);*//*
    }*/
    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Logger.i(TAG, "pos -- " + position);
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.layout.main_row_item:
                //((MainActivity) mContext).openCameraForActivityResultAnotherMethod2((Integer) v.getTag());
                Logger.i(TAG, "tag--" + v.getTag());

                break;
        }
    }

    private int lastPosition = -1;

    public interface OnDataChangeListener{
        public void onDataChanged(DataModel dataModel);
    }

    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }
    private void doButtonOneClickActions(DataModel dataModel) {

        if(mOnDataChangeListener != null){
            mOnDataChangeListener.onDataChanged(dataModel);
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {




        // Get the data item for this position
        final DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        Logger.i(TAG, "get view called" + "\nfrom thread - > " +Thread.currentThread().getId() +
                "\n For position" + position +  " category -- > " + dataModel.getTitle());
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.main_row_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.news_desc);
            viewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.news_author);
            viewHolder.txtSourceAndTime= (TextView) convertView.findViewById(R.id.news_source_and_time);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.optionButton = (Button) convertView.findViewById(R.id.options_button_on_image);
            viewHolder.url = "";
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        //Logger.i(TAG, "\nsetting as follows - >  " + dataModel.getTitle() + "\n" + dataModel.getDescription());
        viewHolder.txtTitle.setText(dataModel.getTitle());
        //viewHolder.txtAuthor.setText("- " + dataModel.getAuthor());
        viewHolder.txtSourceAndTime.setText(dataModel.getAuthor() + " • " + getTimeDifference(dataModel.getPublishedAT()));
        viewHolder.txtAuthor.setTextColor(Color.BLACK);
        viewHolder.txtDesc.setText(dataModel.getDescription());
        viewHolder.txtDesc.setTextColor(Color.BLACK);
        viewHolder.url = dataModel.getUrl();
  /*      viewHolder.optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.post(new Runnable() {
                    @Override
                    public void run() {
                        showPopupMenu(v);
                    }
                });



            }

            private void showPopupMenu(View v) {

                PopupMenu menu = new PopupMenu(mContext, v);
                menu.getMenuInflater().inflate(R.menu.option_menu_each_item_in_list, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.i(TAG, "item clicked - >   " + item.getTitle());
                        return false;
                    }
                });
                menu.show();
            }
        });*/

        /*MenuPopupHelper menuHelper = new MenuPopupHelper(getContext(), (MenuBuilder) menu.getMenu(), viewHolder.info);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();*/

      /*  if(dataModel.getAuthor()==null)
            viewHolder.txtAuthor.setVisibility(View.GONE);
        if(dataModel.getTitle()==null)
            viewHolder.txtTitle.setVisibility(View.GONE);
        if(dataModel.getDescription()==null)
            viewHolder.txtDesc.setVisibility(View.GONE);*/
        try {
            Logger.i(TAG, "for image url - >>" + dataModel.getImageURL());
            Picasso.with(mContext).load(dataModel.getImageURL()).into(viewHolder.info);
           /* PopupMenu menu = new PopupMenu(getContext(), viewHolder.info);
            menu.inflate(R.menu.option_menu_each_item_in_list);
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.i(TAG, "item clicked - >   " + item.getTitle());
                    return false;
                }
            });*/

            /*Picasso.with(mContext).load(dataModel.getImageURL()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    assert viewHolder.info != null;
                    viewHolder.info.setImageBitmap(bitmap);
                    Palette.from(bitmap)
                            .generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                    if (textSwatch == null) {
                                        //Toast.makeText(mContext, "Null swatch :(", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    //viewHolder.txtTitle.setTextColor(textSwatch.getBodyTextColor());
                                    //viewHolder.txtTitle.setTextColor(palette.getLightMutedColor(Color.BLACK));
                                    viewHolder.txtTitle.setTextColor(Color.WHITE);

                                }
                            });
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });*/


        }
        catch (Exception e)
        {
            Logger.i(TAG, "for image url - >>" + dataModel.getImageURL());

            e.printStackTrace();
        }
        Logger.i(TAG, "get view finished");
        return convertView;
    }

    private String getTimeDifference(String publishedAT) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date pubDate = null;
        try {
            pubDate = sdf.parse(publishedAT);

            Long pubTimeDiffToNow = - pubDate.getTime() + System.currentTimeMillis();
            Long pubHours = pubTimeDiffToNow/(1000 * 60 * 60);
            Long mins = pubTimeDiffToNow % (1000*60*60);
            if(pubHours<1)
                if (mins>59 || mins<1) {
                    Log.i(TAG, " Hours - " + pubHours + "   mins - " + mins  );
                    return "Sometime Ago";
                }
                else
                    return mins.toString()+"m";
            else if (pubHours>24)
            {
                Log.i(TAG, " Hours - " + pubHours + "   mins - " + mins  );
                return String.valueOf(pubHours/24) + "d";
            }
            else
                return pubHours.toString()+"h";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*private void getThumbNailFromPhotoLocation(final String photoLocation, final ImageView thumbNailImageView) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                Logger.i(TAG, "Compression being");
                final int THUMBSIZE = 64;
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(photoLocation), THUMBSIZE, THUMBSIZE);
                Logger.i(TAG, "Compression end");
                imageLoader.DisplayImage();
                thumbNailImageView.setImageBitmap(ThumbImage);
            }
        }).start();


    }*/
}