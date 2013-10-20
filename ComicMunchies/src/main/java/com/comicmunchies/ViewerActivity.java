package com.comicmunchies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comicmunchies.common.Global;
import com.comicmunchies.pojo.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blade on 10/19/13.
 */
public class ViewerActivity extends Activity{

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer_activity);
        pager = (ViewPager)findViewById(R.id.viewPager);
        Bitmap bitmap = BitmapFactory.decodeFile(Global.currentReadingBook.getThumbUrl());
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

        ImagePagerAdapter adapter = new ImagePagerAdapter(pages);
        pager.setAdapter(adapter);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        List<Page> pages;
        List<String> frames = new ArrayList<String>();
        int pagePosition = -1;

        public ImagePagerAdapter(List<Page> p) {
            pages = p;
        }

        @Override
        public int getCount() {
            if (pagePosition < (pages.size() - 1)) {
                return frames.size() + 10;
            }
            return frames.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = ViewerActivity.this;
            if (position >= frames.size()) {
                // frame extraction
                if (pagePosition < (pages.size() - 1)) {
                    pagePosition++;
                    frames.add(pages.get(pagePosition).getPath());
                    frames.add(pages.get(pagePosition).getPath());
                    notifyDataSetChanged();
                }
            }
            ImageView imageView = new ImageView(context);
            int padding = context.getResources().getDimensionPixelOffset(R.dimen.padding_medium);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setImageBitmap(BitmapFactory.decodeFile(frames.get(position)));
            container.addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
