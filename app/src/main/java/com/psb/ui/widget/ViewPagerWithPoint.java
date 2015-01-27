package com.psb.ui.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.psb.R;
import com.psb.ui.util.DisplayUtil;

import java.util.List;

/**
 * Created by zl on 2015/1/27.
 */
public class ViewPagerWithPoint extends LinearLayout{

    private Context mContext;
    private LinearLayout layoutPoint;
    private ViewPager mCustomViewPager;
    private ViewPagerWithPointAdapter adapter;

    private int padH = DisplayUtil.dip2px(8);
    private int padS = DisplayUtil.dip2px(8);

    private PointF downPoint = new PointF();
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
        mCustomViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    public ViewPager getCustomViewPager() {
        return mCustomViewPager;
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        mCustomViewPager.setCurrentItem(item, smoothScroll);
    }

    public int getCurrentItem(){
        return mCustomViewPager.getCurrentItem();
    }

    public void setCurrentItem(int item) {
        mCustomViewPager.setCurrentItem(item);
    }

    public void setAdapter(PagerAdapter adapter) {
        mCustomViewPager.setAdapter(adapter);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mCustomViewPager.setOnPageChangeListener(listener);
    }

    public void setPoints(int count) {
        if (count <= 0) {
            return;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1);
        for (int i=0; i<count; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(params);
            iv.setPadding(padH, padS, padH, padS);
            iv.setImageResource(R.drawable.banner_point_inactive);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            layoutPoint.addView(iv);
        }

        mCustomViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int index) {
                ImageView iv = (ImageView) layoutPoint.getChildAt(index);
                iv.setImageResource(R.drawable.banner_point_active);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public void setPagerViews(List<View> views){
        if(null == adapter){
            adapter = new ViewPagerWithPointAdapter(views);
            this.setAdapter(adapter);
        }
        else{
            adapter.setViews(views);
        }
        adapter.notifyDataSetChanged();
    }

    public ViewPagerWithPointAdapter getAdapter(){
        return adapter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        switch (evt.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录按下时候的坐标
                downPoint.x = evt.getX();
                downPoint.y = evt.getY();
                if (this.getChildCount() > 1) { //有内容，多于1个时
                    // 通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.getChildCount() > 1) { //有内容，多于1个时
                    // 通知其父控件，现在进行的是本控件的操作，不允许拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 在up时判断是否按下和松手的坐标为一个点
                if (PointF.length(evt.getX() - downPoint.x, evt.getY()
                        - downPoint.y) < (float) 5.0) {
                    onSingleTouch(this);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(evt);
    }

    public void onSingleTouch(View v) {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch(v);
        }
    }

    public interface OnSingleTouchListener {
        public void onSingleTouch(View v);
    }

    public void setOnSingleTouchListener(
            OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            setCurrentItem(index);
        }
    }

    public class ViewPagerWithPointAdapter extends PagerAdapter {
        private List<View> mListViews;

        public ViewPagerWithPointAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        public void setViews(List<View> views){
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
