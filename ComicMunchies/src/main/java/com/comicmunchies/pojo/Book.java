package com.comicmunchies.pojo;

import java.util.List;

/**
 * Created by blade on 10/19/13.
 */
public class Book {

    private String title;
    private String desc;

    private String bookName;
    public Book(String name) { this.bookName = name; }
    /*
    This is the local file path for the thumb such as /data/data/com.comicmunchies/thumb.jpg.
     */

    private String thumbUrl;

    private int readingPage = -1;

    public int getReadingPage() {
        return readingPage;
    }

    public void setReadingPage(int readingPage) {
        this.readingPage = readingPage;
    }

    public String getTitle() {
        return title;
    }

    public String getBookName() {return bookName;}

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
