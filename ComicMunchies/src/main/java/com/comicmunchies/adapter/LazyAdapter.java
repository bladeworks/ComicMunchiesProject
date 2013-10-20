package com.comicmunchies.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comicmunchies.R;
import com.comicmunchies.common.Constants;
import com.comicmunchies.pojo.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by blade on 10/19/13.
 */
public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private List<Book> data;
    private static LayoutInflater inflater = null;
    private ImageLoader imageLoader = null;

    public LazyAdapter(Activity a, List<Book> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) v = inflater.inflate(R.layout.list_row, null);
        TextView titleView = (TextView) v.findViewById(R.id.book_title);
        TextView descView = (TextView) v.findViewById(R.id.book_desc);
        ImageView imageView = (ImageView) v.findViewById(R.id.book_image);
        Book itemData = data.get(position);
        titleView.setText(itemData.getTitle());
        descView.setText(itemData.getDesc());
        imageView.setImageBitmap(BitmapFactory.decodeFile(itemData.getThumbUrl()));
//        imageLoader.DisplayImage(itemData.getThumbUrl(), imageView);
        return v;
    }
}
