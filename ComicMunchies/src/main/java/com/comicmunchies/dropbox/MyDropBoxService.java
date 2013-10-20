package com.comicmunchies.dropbox;

import com.comicmunchies.itf.DropBoxServiceInterface;
import com.comicmunchies.pojo.Book;
import com.comicmunchies.pojo.Page;

import java.util.List;

/**
 * Created by blade on 10/20/13.
 */
public class MyDropBoxService implements DropBoxServiceInterface{
    @Override
    public List<Book> getBookList() {
        return null;
    }

    @Override
    public List<Page> getPageList(Book book) {
        return null;
    }
}
