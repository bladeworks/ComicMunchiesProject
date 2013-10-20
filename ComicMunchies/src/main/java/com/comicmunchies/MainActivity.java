package com.comicmunchies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.comicmunchies.adapter.LazyAdapter;
import com.comicmunchies.common.Constants;
import com.comicmunchies.common.Global;
import com.comicmunchies.pojo.Book;
import com.comicmunchies.pojo.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listView;
    private LazyAdapter adapter;
    private List<Book> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        data = new ArrayList<Book>();
        for (int i = 0; i < 5; i++ ){

            Book book = new Book();
            book.setTitle("title " + i);
            book.setDesc("Desc goes to here");
            book.setThumbUrl("/sdcard/mofunenglish/data/level0/poster_80x80/406.jpg");
            List<Page> pages = new ArrayList<Page>();

            Page page = new Page();
            page.setNo(1);
            page.setPath("/sdcard/mofunenglish/data/level0/poster_80x80/406.jpg");
            pages.add(page);

            page = new Page();
            page.setNo(2);
            page.setPath("/sdcard/mofunenglish/data/level0/poster_80x80/405.jpg");
            pages.add(page);

            page = new Page();
            page.setNo(2);
            page.setPath("/sdcard/mofunenglish/data/level0/poster_80x80/555.jpg");
            pages.add(page);

            book.setPages(pages);

            data.add(book);
        }

        listView = (ListView) findViewById(R.id.book_list);
        adapter = new LazyAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book itemData = data.get(position);
                Global.currentReadingBook = itemData;
                Intent intent = new Intent(view.getContext(), ViewerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}
