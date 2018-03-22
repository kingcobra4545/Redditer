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

import java.util.ArrayList;



/**
 * Created by KingCobra on 24/11/17.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{
    String TAG = "CustomAdapter";

    private ArrayList<DataModel> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView txtHeader,txtSubHeader, txtDesc, txtTimePosterAt;
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
            viewHolder.txtHeader = (TextView) convertView.findViewById(R.id.header);
            viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            viewHolder.txtSubHeader = (TextView) convertView.findViewById(R.id.sub_header);
            viewHolder.txtTimePosterAt= (TextView) convertView.findViewById(R.id.time_posted);
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
        viewHolder.txtHeader.setText(dataModel.getHeader());
        viewHolder.txtHeader.setTextColor(Color.WHITE);

        viewHolder.txtDesc.setText(dataModel.getDescription());
        viewHolder.txtDesc.setTextColor(Color.WHITE);

        viewHolder.txtSubHeader.setText(dataModel.getSubHeader());
        viewHolder.txtSubHeader.setTextColor(Color.WHITE);

        viewHolder.txtTimePosterAt.setText(dataModel.getTimePostedAt());
        viewHolder.txtTimePosterAt.setTextColor(Color.WHITE);
        Log.i(TAG, "Loading up image from - > " +dataModel.getImageURL());
        String imageUrl = dataModel.getImageURL();
        if(imageUrl!=null){
            imageUrl = imageUrl.replaceAll("\\s","");
            if(!imageUrl.isEmpty() && !imageUrl.equals("") && !imageUrl.equals(" ")) {
                Log.i(TAG,  " image URL is ->" + imageUrl + "<--");
                Picasso.with(mContext).load(imageUrl).into(viewHolder.info);
            }
        }


        Logger.i(TAG, "get view finished");
        return convertView;
    }




}