package com.comicmunchies.pojo;

/**
 * Created by blade on 10/19/13.
 */
public class Page {
    private int no;

    /*
    The local file path such as /data/data/com.comicmunchies/cache/page1.jpg
     */
    private String path;


    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
