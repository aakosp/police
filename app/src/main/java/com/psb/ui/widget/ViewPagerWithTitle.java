package com.psb.ui.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psb.R;
import com.psb.ui.util.DisplayUtil;

import java.util.List;


/**
 * Created by zl on 2014/11/27.
 */
public class ViewPagerWithTitle extends LinearLayout {

    private Context mContext;
    private LinearLayout layoutTabs;//layoutTitle,
    //    private ImageView mCursor;
    private ViewPager mCustomViewPager;
    private int cursorColor = R.color.topbar;
    //    private int defaultTabColor = R.color.topbar;
//    private int selectedTabColor = R.color.default_light_grey_color;
    private int textSize = 18;
    //    private int offset = 0;// 动画图片偏移量
//    private int currIndex = 0;// 当前页卡编号
    private ViewPagerWithTitleAdapter adapter;

    private int padH = DisplayUtil.dip2px(8);
    private int padS = DisplayUtil.dip2px(12);

    public ViewPagerWithTitle(Context context) {
        this(context, null);
    }

    public ViewPagerWithTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_viewpager_title, this, true);

//        layoutTitle = (LinearLayout) findViewById(R.id.title);
        layoutTabs = (LinearLayout) findViewById(R.id.tabs);
//        mCursor = (ImageView) findViewById(R.id.cursor);
//        mCursor.setBackgroundColor(getResources().getColor(cursorColor));
        mCustomViewPager = (ViewPager) findViewById(R.id.custviewPager);
    }

    public ViewPager getCustomViewPager() {
        return mCustomViewPager;
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        mCustomViewPager.setCurrentItem(item, smoothScroll);
    }

    public int getCurrentItem() {
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

    public void setTabs(String tabs[]) {
        if (null == tabs || tabs.length == 0) {
            return;
        }
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.weight = 1;
        for (int i = 0; i < tabs.length; i++) {
            TextView tv = new TextView(mContext);
            tv.setText(tabs[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(params);
            tv.setTextSize(textSize);
            tv.setTextColor(mContext.getResources().getColor(R.color.viewpager_title_normal));
            tv.setPadding(padH, padS, padH, padS);
            tv.setLines(1);
            tv.setTag(i);
            if (i == 0) {
                tv.setTextColor(mContext.getResources().getColor(R.color.viewpager_title_selected));
            }
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = (int) v.getTag();
                    mCustomViewPager.setCurrentItem(i);
                }
            });
            layoutTabs.addView(tv);
        }
        mCustomViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int index) {
                for (int i = 0; i < layoutTabs.getChildCount(); i++) {
                    TextView tv = (TextView) layoutTabs.getChildAt(i);
                    if (i == index) {
                        tv.setTextColor(mContext.getResources().getColor(R.color.viewpager_title_selected));
                    } else {
                        tv.setTextColor(mContext.getResources().getColor((R.color.viewpager_title_normal)));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
//        layoutTitle.setVisibility(View.VISIBLE);
    }

    public void setPagerViews(List<View> views) {
        if (null == adapter) {
            adapter = new ViewPagerWithTitleAdapter(views);
            this.setAdapter(adapter);
        } else {
            adapter.setViews(views);
        }
        adapter.notifyDataSetChanged();
    }

    public ViewPagerWithTitleAdapter getAdapter() {
        return adapter;
    }

    private class MyOnClickListener implements OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            setCurrentItem(index);
        }
    }

    public class ViewPagerWithTitleAdapter extends PagerAdapter {
        private List<View> mListViews;

        public ViewPagerWithTitleAdapter(List<View> mListViews) {
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
