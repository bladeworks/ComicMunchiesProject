package com.comicmunchies.ip;

import android.graphics.Bitmap;

import static android.graphics.Bitmap.createBitmap;

/**
 * Created by Simon.Edwardsson on 10/19/13.
 */
public class BitMapImage extends Image {
    Bitmap image;

    public BitMapImage(int width, int height) {
        super(width, height);
        image = createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }
    public BitMapImage(Bitmap image) {
        super(image.getWidth(), image.getHeight());
        this.image = image;
    }

    public int getRGB(int x, int y) {
        return image.getPixel(x,y);
    }

    public void setRGB(int x, int y, int bgColor) {
        image.setPixel(x,y,bgColor);
    }

    public Object getSource() {
        return image;
    }
}
