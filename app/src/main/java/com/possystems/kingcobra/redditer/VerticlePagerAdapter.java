package com.possystems.kingcobra.redditer;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.possystems.kingcobra.redditer.DataModels.DataModel;
import com.possystems.kingcobra.redditer.DebuggingTools.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by KingCobra on 17/01/18.
 */

public class VerticlePagerAdapter extends PagerAdapter {
    String TAG = "VerticlePagerAdapter";

    //String mResources[] = {"To start off lets understand what exactly Android CardView is? Its a new widget for Android, which can be used to display a card sort of a layout in android. As you may know Android material design is inspired from paper and ink concept. Mostly it displays views on top of each other, with shadows. In simple terms, Android CardView is such a view which has all material design properties, most importantly showing shadows according the elevation. The best part about this view is that it extends FrameLayout and it can be displayed on all the platforms of android since it’s available through the Support v7 library. Lets have a look at some of its properties:","To start off lets understand what exactly Android CardView is? Its a new widget for Android, which can be used to display a card sort of a layout in android. As you may know Android material design is inspired from paper and ink concept. Mostly it displays views on top of each other, with shadows. In simple terms, Android CardView is such a view which has all material design properties, most importantly showing shadows according the elevation. The best part about this view is that it extends FrameLayout and it can be displayed on all the platforms of android since it’s available through the Support v7 library. Lets have a look at some of its properties:"};

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<DataModel> mDataModel;

    public VerticlePagerAdapter(Context context, ArrayList<DataModel> dataModel) {
        Logger.i(TAG, "Constructor");
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDataModel = dataModel;
        Logger.i(TAG, "Data model - > " + mDataModel.get(0));
    }

    @Override
    public int getCount() {
        return mDataModel.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Logger.i(TAG, "Instantiating view");
        final DataModel dataModel = mDataModel.get(position);
        View itemView = mLayoutInflater.inflate(R.layout.main_row_item, container, false);

        TextView txtTitle = (TextView) itemView.findViewById(R.id.header);
        TextView txtDesc = (TextView) itemView.findViewById(R.id.desc);
        TextView txtSourceAndTime = (TextView) itemView.findViewById(R.id.sub_header);
        ImageView info = (ImageView) itemView.findViewById(R.id.imageView);
        Button optionButton = (Button) itemView.findViewById(R.id.options_button_on_image);

        txtDesc.setText(dataModel.getDescription());
        txtTitle.setText(dataModel.getHeader());
        txtSourceAndTime.setText(dataModel.getTimePostedAt());
        Picasso.with(mContext).load(dataModel.getImageURL()).into(info);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}