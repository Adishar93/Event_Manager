package com.asaproject.eventnotifier;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private  ArrayList<String> arrayList;
    private Context mContext;

    public ImageAdapter(Context context, ArrayList<String> arrayList)
    {
        this.mContext = context;
        this.arrayList = arrayList;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


   // private int[] sliderImageId = new int[];



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView( mContext);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        imageView.setImageResource(sliderImageId[position]);
        Picasso.get().load(arrayList.get(position)).fit().centerCrop()
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if(arrayList!=null){
            return arrayList.size();
        }
        return 0;
    }
}
