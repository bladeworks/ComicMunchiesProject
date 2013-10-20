package com.comicmunchies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comicmunchies.common.Global;
import com.comicmunchies.ip.FrameExtractor;
import com.comicmunchies.pojo.Page;
import com.comicmunchies.widget.ScaleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by blade on 10/19/13.
 */
public class ViewerActivity extends Activity{

    private ViewPager pager;
    List<Bitmap> frames = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer_activity);
        pager = (ViewPager)findViewById(R.id.viewPager);
        Bitmap bitmap = BitmapFactory.decodeFile(Global.currentReadingBook.getThumbUrl());
        List<Page> pages = new ArrayList<Page>();

        String name = Global.currentReadingBook.getTitle();

        Page page = new Page();
        page.setNo(1);
        page.setPath("/sdcard/Pictures/test/" + name + "/1.jpg");
        pages.add(page);

        page = new Page();
        page.setNo(2);
        page.setPath("/sdcard/Pictures/test/" + name + "/2.jpg");
        pages.add(page);

        page = new Page();
        page.setNo(2);
        page.setPath("/sdcard/Pictures/test/" + name + "/3.jpg");
        pages.add(page);

        ImagePagerAdapter adapter = new ImagePagerAdapter(pages);
        pager.setAdapter(adapter);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        List<Page> pages;
        int pagePosition = -1;

        public ImagePagerAdapter(List<Page> p) {
            pages = p;
        }

        @Override
        public int getCount() {
            if (frames.size() == 0) return pages.size();
            return frames.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context   = ViewerActivity.this;
            final ScaleImageView imageView = new ScaleImageView(context);
            int padding = context.getResources().getDimensionPixelOffset(R.dimen.padding_medium);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setImageBitmap(BitmapFactory.decodeFile("/sdcard/Pictures/test/loading.png"));
            container.addView(imageView,0);

            if (position >= frames.size()) {
                // frame extraction
//                if (pagePosition < (pages.size() - 10)) {
                    pagePosition++;
                    final ImagePagerAdapter ipa = this;
                    final int cPosition = position;
                    ExtractAsyncTask task = new ExtractAsyncTask(ipa, imageView, cPosition);
                    try {
                        task.execute(pages.get(pagePosition)).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
//                }
            }
            // if we didn't have to start a bg process just use the already processed image
            imageView.setImageBitmap(frames.get(position));
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    private class ExtractAsyncTask extends AsyncTask<Page, Void, List<Bitmap>> {

        private ImagePagerAdapter ipa;
        private ScaleImageView imageView;
        private int cPosition;

        public ExtractAsyncTask(ImagePagerAdapter ipa, ScaleImageView imageView, int position) {
            this.ipa = ipa;
            this.imageView = imageView;
            this.cPosition = position;
        }

        protected List<Bitmap> doInBackground(Page... p) {
            FrameExtractor fe = new FrameExtractor();
            return fe.getFrameList(p[0]);
        }

        protected void onPostExecute(List<Bitmap> newFrames) {
            frames.addAll(newFrames);
            ipa.notifyDataSetChanged();
            imageView.setImageBitmap(frames.get(cPosition));
            System.err.println("New frame:" + frames.get(cPosition));
        }

    }
}