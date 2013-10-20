package com.comicmunchies.ip;
import android.graphics.Color;

/**
 * Created by Simon.Edwardsson on 10/19/13.
 */
public abstract class Image {
    private int width;
    private int height;

    public Image(int width, int height) {
        this.width  = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract int getRGB(int x, int y);

    public abstract void setRGB(int x, int y, int bgColor);

    public boolean[][] toBW(int color) {
        boolean ff[][] = new boolean[getWidth()][getHeight()];

        for(int x = 0; x < getWidth(); x++) {
            for(int y = 0; y < getHeight(); y++) {
                //if(img.getRGB(x, y) == matchingColor) {
                if(getRGB(x, y) == color) {
                    ff[x][y] = true;
                } else {
                    ff[x][y] = false;
                }
            }
        }
        return ff;
    }

    public boolean[][] toBW(int color, double colorDistance) {
        boolean ff[][] = new boolean[getWidth()][getHeight()];

        for(int x = 0; x < getWidth(); x++) {
            for(int y = 0; y < getHeight(); y++) {
                //if(img.getRGB(x, y) == matchingColor) {
                if(colorDistance(getRGB(x, y),color) < colorDistance) {
                    ff[x][y] = true;
                } else {
                    ff[x][y] = false;
                }
            }
        }
        return ff;
    }

    private double colorDistance(int x, int y) {
        int r    = Color.red(x) - Color.red(y);
        int g    = Color.green(x) - Color.green(y);
        int b    = Color.blue(x) - Color.blue(y);

        return r*r + g*g + b*b;
    }

    public abstract Object getSource();

}
