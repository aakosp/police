package com.psb.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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

    private LinearLayout layoutTabs, layoutTitle;
    private ImageView mCursor;
    private View divider;
    private ViewPager mCustomViewPager;
    private int cursorColor = R.color.selected_red;
    private int textSize = 16;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private ViewPagerWithTitleAdapter adapter;

    private int padH = DisplayUtil.dip2px(8);
    private int padS = DisplayUtil.dip2px(12);

    private boolean mCursorVisible;

    private ViewPager.OnPageChangeListener onPageChangeListener;

    public ViewPagerWithTitle(Context context) {
        this(context, null);
    }

    public ViewPagerWithTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_viewpager_title, this, true);

        layoutTitle = (LinearLayout) findViewById(R.id.title);
        layoutTabs = (LinearLayout) findViewById(R.id.tabs);
        mCursor = (ImageView) findViewById(R.id.cursor);
        mCursor.setBackgroundColor(getResources().getColor(cursorColor));
        mCustomViewPager = (ViewPager) findViewById(R.id.custviewPager);
        divider = findViewById(R.id.divider);
        this.initializeAttributes(context, attrs);
    }

    private void initializeAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.widgetviewpager);
        if (null != mCursor) {
            mCursorVisible = typedArray.getBoolean(R.styleable.widgetviewpager_tab_cursor_visible, true);
            this.setCursorVisible(mCursorVisible);
        }
        if (null != divider) {
            boolean dividerVisible = typedArray.getBoolean(R.styleable.widgetviewpager_tab_divider_visible, false);
            this.setDividerVisible(dividerVisible);
        }
    }

    public void setCursorVisible(boolean visibility) {
        if (visibility) {
            mCursor.setVisibility(View.VISIBLE);
        } else {
            mCursor.setVisibility(View.GONE);
        }
    }

    public void setDividerVisible(boolean visibility) {
        if (visibility) {
            divider.setVisibility(View.VISIBLE);
        } else {
            divider.setVisibility(View.GONE);
        }
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

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        onPageChangeListener = listener;
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
            tv.setTextColor(mContext.getResources().getColor(R.color.unselected_text));
            tv.setPadding(padH, padS, padH, padS);
            tv.setLines(1);
            tv.setTag(i);
            if (i == 0) {
                tv.setTextColor(mContext.getResources().getColor(R.color.selected_red));
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

        //计算偏移量
        offset = DisplayUtil.getDisplayMetrics().widthPixels / tabs.length;
        LayoutParams cursorParams = new LayoutParams(offset,
                DisplayUtil.dip2px(1));
        mCursor.setLayoutParams(cursorParams);

        mCustomViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (null != onPageChangeListener) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int index) {
                if (mCursorVisible) {
                    ObjectAnimator animation = ObjectAnimator.ofFloat(mCursor, "x",
                            offset * currIndex, offset * index);
                    currIndex = index;
                    animation.setDuration(200);
                    animation.start();
                }
                for (int i = 0; i < layoutTabs.getChildCount(); i++) {
                    TextView tv = (TextView) layoutTabs.getChildAt(i);
                    if (i == index) {
                        tv.setTextColor(mContext.getResources().getColor(R.color.selected_red));
                    } else {
                        tv.setTextColor(mContext.getResources().getColor((R.color.unselected_text)));
                    }
                }
                if (null != onPageChangeListener) {
                    onPageChangeListener.onPageSelected(index);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (null != onPageChangeListener) {
                    onPageChangeListener.onPageScrollStateChanged(i);
                }
            }
        });
        layoutTitle.setVisibility(View.VISIBLE);
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

    public void setAdapter(PagerAdapter adapter) {
        mCustomViewPager.setAdapter(adapter);
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
