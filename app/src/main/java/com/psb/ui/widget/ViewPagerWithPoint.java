package com.psb.ui.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.psb.R;
import com.psb.ui.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/27.
 */
public class ViewPagerWithPoint extends LinearLayout {

    private Context mContext;
    private LinearLayout layoutPoint;
    private InnerViewPager mInnerViewPager;
    private ViewPagerWithPointAdapter adapter;

    private int padH = DisplayUtil.dip2px(2);
    private int padS = DisplayUtil.dip2px(2);
    private List<ImageView> points = new ArrayList<>();

    private OnSingleTouchListener onSingleTouchListener;

    public ViewPagerWithPoint(Context context) {
        this(context, null);
    }

    public ViewPagerWithPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_viewpager_point, this, true);

        layoutPoint = (LinearLayout) findViewById(R.id.point);
        mInnerViewPager = (InnerViewPager) findViewById(R.id.viewPager);
    }

    public ViewPager getViewPager() {
        return mInnerViewPager;
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        mInnerViewPager.setCurrentItem(item, smoothScroll);
    }

    public int getCurrentItem() {
        return mInnerViewPager.getCurrentItem();
    }

    public void setCurrentItem(int item) {
        mInnerViewPager.setCurrentItem(item);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mInnerViewPager.setOnPageChangeListener(listener);
    }

    public void setPoints(int count) {
        if (count <= 0) {
            return;
        }
        layoutPoint.removeAllViews();
        points.clear();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        for (int i = 0; i < count; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(params);
            iv.setPadding(padH, padS, padH, padS);
            if (i == 0) {
                iv.setImageResource(R.drawable.banner_point_active);
            } else {
                iv.setImageResource(R.drawable.banner_point_inactive);
            }

            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            layoutPoint.addView(iv);
            points.add(iv);
        }

        mInnerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int index) {
                for (int i = 0; i < points.size(); i++) {
                    ImageView iv = points.get(i);
                    if (i == index) {
                        iv.setImageResource(R.drawable.banner_point_active);
                    } else {
                        iv.setImageResource(R.drawable.banner_point_inactive);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public void setPagerViews(List<View> views) {
        if (null == adapter) {
            adapter = new ViewPagerWithPointAdapter(views);
            this.setAdapter(adapter);
        } else {
            adapter.setViews(views);
        }
        setPoints(adapter.getCount());
        adapter.notifyDataSetChanged();
    }

    public ViewPagerWithPointAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PagerAdapter adapter) {
        mInnerViewPager.setAdapter(adapter);
    }

    public void onSingleTouch(View v) {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch(v);
        }
    }

    public void setOnSingleTouchListener(
            OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

    public interface OnSingleTouchListener {
        public void onSingleTouch(View v);
    }

    public class ViewPagerWithPointAdapter extends PagerAdapter {
        private List<View> mListViews;

        public ViewPagerWithPointAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        public void setViews(List<View> views) {
            this.mListViews = views;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}
