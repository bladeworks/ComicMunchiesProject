package com.comicmunchies.pojo;

import java.util.List;

/**
 * Created by blade on 10/19/13.
 */
public class Book {

    private String title;
    private String desc;

    /*
    This is the local file path for the thumb such as /data/data/com.comicmunchies/thumb.jpg.
     */
    private String thumbUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
