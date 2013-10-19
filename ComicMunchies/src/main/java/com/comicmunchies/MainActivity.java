package com.comicmunchies;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.comicmunchies.adapter.LazyAdapter;
import com.comicmunchies.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    private ListView listView;
    private LazyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 5; i++ ){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(Constants.KEY_TITLE, "title " + i);
            map.put(Constants.KEY_DESC, "Here is the desc");
            map.put(Constants.KEY_IMAGE, "http://www.google.com/favicon.ico");
            data.add(map);
        }

        listView = (ListView) findViewById(R.id.book_list);
        adapter = new LazyAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}
