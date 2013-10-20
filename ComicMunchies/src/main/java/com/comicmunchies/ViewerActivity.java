package com.comicmunchies;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.comicmunchies.common.Global;

/**
 * Created by blade on 10/19/13.
 */
public class ViewerActivity extends Activity{

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer_activity);
        imageView = (ImageView)findViewById(R.id.viewer_img);
        Bitmap bitmap = BitmapFactory.decodeFile(Global.currentReadingBook.getThumbUrl());
        imageView.setImageBitmap(bitmap);
    }
}
