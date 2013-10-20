package com.comicmunchies.itf;

import android.graphics.Bitmap;

import com.comicmunchies.pojo.Page;

import java.util.List;

/**
 * Created by blade on 10/19/13.
 */
public interface FrameExtractInterface {

    public List<Bitmap> getFrameList(Page page);

}
