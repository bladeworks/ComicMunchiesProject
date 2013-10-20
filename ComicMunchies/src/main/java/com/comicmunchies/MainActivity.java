package com.comicmunchies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.comicmunchies.adapter.LazyAdapter;
import com.comicmunchies.common.Constants;
import com.comicmunchies.common.Global;
import com.comicmunchies.pojo.Book;
import com.comicmunchies.pojo.Page;

import java.io.IOException;
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
    private TextView mTestOutput;
    private Button mLinkButton;

    private DbxAccountManager mDbxAcctMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        mTestOutput = (TextView) findViewById(R.id.test_output);
        mLinkButton = (Button) findViewById(R.id.link_button);

        mLinkButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLinkToDropbox();
            }
        });

        mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), "bqmodvepz6wz8mh", "nsb9k29gdqfdz0u");

        data = new ArrayList<Book>();
        for (int i = 0; i < 5; i++ ){

            Book book = new Book();
            book.setTitle("title " + i);
            book.setDesc("Desc goes to here");
            book.setThumbUrl("/sdcard/mofunenglish/data/level0/poster_80x80/406.jpg");
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
        if (mDbxAcctMgr.hasLinkedAccount()) {
            doDropboxTest();
        }
    }

    static final int REQUEST_LINK_TO_DBX = 0;  // This value is up to you

    public void onClickLinkToDropbox() {
        mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LINK_TO_DBX) {
            if (resultCode == Activity.RESULT_OK) {
                // ... Start using Dropbox files.
            } else {
                // ... Link failed or was cancelled by the user.
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void doDropboxTest() {
        mTestOutput.setText("Dropbox Sync API Version "+DbxAccountManager.SDK_VERSION_NAME+"\n");
//        try {
//            final String TEST_DATA = "Hello Dropbox";
//            final String TEST_FILE_NAME = "hello_dropbox.txt";
//            DbxPath testPath = new DbxPath(DbxPath.ROOT, TEST_FILE_NAME);
//
//            // Create DbxFileSystem for synchronized file access.
//            DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
//
//            // Print the contents of the root folder.  This will block until we can
//            // sync metadata the first time.
//            List<DbxFileInfo> infos = dbxFs.listFolder(DbxPath.ROOT);
//            mTestOutput.append("\nContents of app folder:\n");
//            for (DbxFileInfo info : infos) {
//                mTestOutput.append("    " + info.path + ", " + info.modifiedTime + '\n');
//            }
//
//            // Create a test file only if it doesn't already exist.
//            if (!dbxFs.exists(testPath)) {
//                DbxFile testFile = dbxFs.create(testPath);
//                try {
//                    testFile.writeString(TEST_DATA);
//                } finally {
//                    testFile.close();
//                }
//                mTestOutput.append("\nCreated new file '" + testPath + "'.\n");
//            }
//
//            // Read and print the contents of test file.  Since we're not making
//            // any attempt to wait for the latest version, this may print an
//            // older cached version.  Use getSyncStatus() and/or a listener to
//            // check for a new version.
//            if (dbxFs.isFile(testPath)) {
//                String resultData;
//                DbxFile testFile = dbxFs.open(testPath);
//                try {
//                    resultData = testFile.readString();
//                } finally {
//                    testFile.close();
//                }
//                mTestOutput.append("\nRead file '" + testPath + "' and got data:\n    " + resultData);
//            } else if (dbxFs.isFolder(testPath)) {
//                mTestOutput.append("'" + testPath.toString() + "' is a folder.\n");
//            }
//        } catch (IOException e) {
//            mTestOutput.setText("Dropbox test failed: " + e);
//        }
    }
}
