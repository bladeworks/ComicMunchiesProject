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

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

public class MainActivity extends Activity {

    private ListView listView;
    private LazyAdapter adapter;
    private List<Book> data;

    private DbxAccountManager mDbxAcctMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), "bqmodvepz6wz8mh", "nsb9k29gdqfdz0u");

        data = new ArrayList<Book>();
        {
            Book book = new Book("breaker");
            book.setTitle("Breaker: New waves");
            book.setDesc("Continuation of The Breaker with new allies and enemies.");
            book.setThumbUrl("/sdcard/Pictures/test/breaker/cover.jpg");
            data.add(book);
        }
        {
            Book book = new Book("naruto");
            book.setTitle("Naruto");
            book.setDesc("Follow Naruto on his way to become a Ninja!");
            book.setThumbUrl("/sdcard/Pictures/test/naruto/cover.jpg");
            data.add(book);
        }
        {
            Book book = new Book("onepiece");
            book.setTitle("A new era of pirate, can they find the one piece");
            book.setDesc("Desc goes to here");
            book.setThumbUrl("/sdcard/Pictures/test/onepiece/cover.jpg");
            data.add(book);
        }
        {
            Book book = new Book("bleach");
            book.setTitle("Bleach");
            book.setDesc("Desc goes to here");
            book.setThumbUrl("/sdcard/Pictures/test/bleach/cover.jpg");
            data.add(book);
        }

        {
            Book book = new Book("vampire");
            book.setTitle("Vampire Knight");
            book.setDesc("Desc goes to here");
            book.setThumbUrl("/sdcard/Pictures/test/vampire/cover.jpg");
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

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    //    static final int REQUEST_LINK_TO_DBX = 0;  // This value is up to you
//
//    public void onClickLinkToDropbox(View view) {
//        mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_LINK_TO_DBX) {
//            if (resultCode == Activity.RESULT_OK) {
//                // ... Start using Dropbox files.
//            } else {
//                // ... Link failed or was cancelled by the user.
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

}
