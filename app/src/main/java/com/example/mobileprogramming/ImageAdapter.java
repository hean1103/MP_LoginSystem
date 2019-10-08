package com.example.mobileprogramming;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{

    private Context mContext;

    public Integer[] mThumbIds = {
            R.drawable.cat1, R.drawable.cat2,
            R.drawable.cat3, R.drawable.cat4,
            R.drawable.cat5, R.drawable.cat6,
            R.drawable.cat7, R.drawable.cat8,
            R.drawable.cat9, R.drawable.cat10,
            R.drawable.cat11, R.drawable.cat12,
            R.drawable.cat13, R.drawable.cat14,
            R.drawable.cat15, R.drawable.cat16,
            R.drawable.cat17, R.drawable.cat18,
            R.drawable.cat19, R.drawable.cat20,
            R.drawable.cat21, R.drawable.cat22,
            R.drawable.cat23, R.drawable.cat24,
            R.drawable.cat25, R.drawable.cat26,
            R.drawable.cat27
    };

    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(350,350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }else{
            imageView = (ImageView)convertView;
        }

        imageView.setImageResource(mThumbIds[position]);

        return imageView;
    }

}