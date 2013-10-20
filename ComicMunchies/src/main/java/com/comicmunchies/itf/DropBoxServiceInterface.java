package com.comicmunchies.itf;

import com.comicmunchies.pojo.Book;
import com.comicmunchies.pojo.Page;

import java.util.List;

/**
 * Created by blade on 10/19/13.
 */
public interface DropBoxServiceInterface {

    public List<Book> getBookList();

    public List<Page> getPageList(Book book);

}
