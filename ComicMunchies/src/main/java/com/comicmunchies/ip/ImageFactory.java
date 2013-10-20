package com.comicmunchies.ip;

import android.graphics.Bitmap;

import static android.graphics.Bitmap.*;

/**
 * Created by Simon.Edwardsson on 10/19/13.
 */
public class ImageFactory {
    public static Image makeImage(int width, int height) {
        return new BitMapImage(width, height);
    }
}
