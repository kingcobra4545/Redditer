package com.possystems.kingcobra.redditer.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.possystems.kingcobra.redditer.DataModels.DataModel;
import com.possystems.kingcobra.redditer.DebuggingTools.Logger;
import com.possystems.kingcobra.redditer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by KingCobra on 24/11/17.
 */

public class CustomAdapter extends ArrayAdapter<DataModel>  {
    String TAG = "CustomAdapter";


    private ArrayList<DataModel> dataSet;
    Context mContext;





    @Override
    public long getItemId(int position) {
        Log.i(TAG, "get Item id - > " + position);
        return super.getItemId(position);

    }

    @Nullable
    @Override
    public DataModel getItem(int position) {

        return super.getItem(position);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtHeader,txtSubHeader, txtDesc, txtTimePosterAt, txtLikes;
        TextView txtType;
        TextView txtVersion, vehicleUsed;
        ImageView info, upVoteImageButton, downVoteImageButton, smallIcon;
        String url;
        Button optionButton;
    }

    /*private static final CustomAdapter instance = new CustomAdapter();

    private CustomAdapter(){}

    public static CustomAdapter getInstance(){
        return instance;
    }*/

    public CustomAdapter(ArrayList<DataModel> data, Context context, Activity activity) {

        super(context, R.layout.activity_main_reddit, data);
        this.dataSet = data;
        this.mContext=context;
        Logger.i(TAG, "Adapter Called" + "\n Data Size - > " + data.size());


    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {




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
            convertView = inflater.inflate(R.layout.activity_main_reddit, parent, false);
            viewHolder.txtHeader = (TextView) convertView.findViewById(R.id.header);
            viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.main_description);
            viewHolder.txtSubHeader = (TextView) convertView.findViewById(R.id.sub_header);
            viewHolder.txtLikes = (TextView) convertView.findViewById(R.id.num_of_likes);
//            viewHolder.txtTimePosterAt= (TextView) convertView.findViewById(R.id.time_posted);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.main_image);
            viewHolder.smallIcon = (ImageView) convertView.findViewById(R.id.small_icon_left_top);
            viewHolder.upVoteImageButton = (ImageView) convertView.findViewById(R.id.up_arrow);
            Log.i(TAG, "When CV = null DB ID - > " + dataModel.getID());
            Log.i(TAG, "When CV = null UI ID - > " + position);
            convertView.setTag(position);


            viewHolder.downVoteImageButton = (ImageView) convertView.findViewById(R.id.down_arrow);
            viewHolder.downVoteImageButton.setTag(position);

//            viewHolder.optionButton = (Button) convertView.findViewById(R.id.options_button_on_image);
//            viewHolder.url = "";
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.downVoteImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
                Log.i(TAG, "DownVote Item ID clicked --> "  + dataModel.getID()+ "\n at position - > " + position +
                        "\n Button tag - >" + viewHolder.upVoteImageButton.getTag());

            }
        });
        viewHolder.upVoteImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
                Log.i(TAG, "UpVote Item ID clicked --> "  + dataModel.getID()+ "\n at position - > " + position);

            }
        });

        /*Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);*/
        viewHolder.txtHeader.setText(dataModel.getHeader());
//        viewHolder.txtHeader.setTextColor(Color.WHITE);

        viewHolder.txtDesc.setText(dataModel.getDescription());
        viewHolder.txtLikes.setText(dataModel.getLikes());
//        viewHolder.txtDesc.setTextColor(Color.WHITE);

        viewHolder.txtSubHeader.setText(
                dataModel.getSubHeader() + " • " + dataModel.getPublishedAT()
        );


//        viewHolder.txtSubHeader.setTextColor(Color.WHITE);

//        viewHolder.txtTimePosterAt.setText(dataModel.getTimePostedAt());
//        viewHolder.txtTimePosterAt.setTextColor(Color.WHITE);
//        Log.i(TAG, "Loading up image from - > " +dataModel.getMainImageURL());
        String imageUrl = dataModel.getMainImageURL();
        if(imageUrl!=null){
            imageUrl = imageUrl.replaceAll("\\s","");
            if(!imageUrl.isEmpty() && !imageUrl.equals("") && !imageUrl.equals(" ")) {
//                Log.i(TAG,  " image URL is ->" + imageUrl + "<--");
                Picasso.with(mContext).load(imageUrl).fit().into(viewHolder.info);
            }
        }
        imageUrl = dataModel.getHeaderIconImageURL();
        if(imageUrl!=null){
            imageUrl = imageUrl.replaceAll("\\s","");
            if(!imageUrl.isEmpty() && !imageUrl.equals("") && !imageUrl.equals(" ")) {
//                Log.i(TAG,  " image URL is ->" + imageUrl + "<--");
                Picasso.with(mContext).load(imageUrl).fit().into(viewHolder.smallIcon);
            }
        }
        Log.i(TAG, "When CV != null DB ID - > " + dataModel.getID());
        Log.i(TAG, "When CV != null UI ID - > " + position);

        Logger.i(TAG, "get view finished");
        return convertView;
    }



}