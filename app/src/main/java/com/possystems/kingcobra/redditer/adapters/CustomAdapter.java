package com.possystems.kingcobra.redditer.adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.possystems.kingcobra.redditer.DataModels.DataModel;
import com.possystems.kingcobra.redditer.DebuggingTools.Logger;
import com.possystems.kingcobra.redditer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class CustomAdapter extends ArrayAdapter<DataModel>  {
    String TAG = "CustomAdapter";
    private Context mContext;
    @Override
    public long getItemId(int position) {
        Log.i(TAG, "get Item id - > " + position);
        return super.getItemId(position);
    }
    @Nullable
    @Override
    public DataModel getItem(int position) {return super.getItem(position);}
    private static class ViewHolder {
        TextView txtHeader,txtSubHeader, txtDesc, txtLikes;
        ImageView info, upVoteImageButton, downVoteImageButton, smallIcon;
    }
    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.activity_main_reddit, data);
        this.mContext=context;
        Logger.i(TAG, "Adapter Called" + "\n Data Size - > " + data.size());
    }
    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull  final ViewGroup parent) {
        // Get the data item for this position
        final DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        /*Logger.i(TAG, "get view called" + "\nfrom thread - > " +Thread.currentThread().getId() +
                "\n For position" + position +  " category -- > " + dataModel.getTitle());*/
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_main_reddit, parent, false);
            viewHolder.txtHeader =          convertView.findViewById(R.id.header);
            viewHolder.txtDesc =            convertView.findViewById(R.id.main_description);
            viewHolder.txtSubHeader =       convertView.findViewById(R.id.sub_header);
            viewHolder.txtLikes =           convertView.findViewById(R.id.num_of_likes);
            viewHolder.info =               convertView.findViewById(R.id.main_image);
            viewHolder.smallIcon =          convertView.findViewById(R.id.small_icon_left_top);
            viewHolder.upVoteImageButton =  convertView.findViewById(R.id.up_arrow);
            viewHolder.downVoteImageButton =convertView.findViewById(R.id.down_arrow);
            viewHolder.downVoteImageButton.setTag(position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
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
        viewHolder.txtHeader.setText(dataModel.getHeader());
        viewHolder.txtDesc.setText(dataModel.getDescription());
        viewHolder.txtLikes.setText(dataModel.getLikes());
        String subHeader = dataModel.getSubHeader() + " • " + dataModel.getTimePostedAt();
        viewHolder.txtSubHeader.setText(subHeader);
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
            imageUrl = imageUrl.replaceAll("\\s","");//To remove any white space if at all present
            if(!imageUrl.isEmpty() && !imageUrl.equals("") && !imageUrl.equals(" ")) {
                Picasso.with(mContext).load(imageUrl).fit().into(viewHolder.smallIcon);
            }
        }
        return convertView;
    }



}