package com.example.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

//public class GridAdapter extends BaseAdapter {
//
//    Context context;
//    private List<Data_set_class> DATASET;
//    public GridAdapter(Context applicationContext, List<Data_set_class> mdata) {
//
//        this.context = applicationContext;
//        this.DATASET = mdata;
//    }
//
//    @Override
//    public int getCount() {
//        return DATASET.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//
//        if (convertView == null) {
//            imageView = new ImageView(context);
//            imageView.setLayoutParams(new GridView.LayoutParams(500,500));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            imageView.setPadding(10, 15, 0, 15);
//        }
//        else
//        {
//            imageView = (ImageView) convertView;
//        }
//        //imageView.setImageBitmap(images[position]);
//        return imageView;
//
//    }
//}
//

