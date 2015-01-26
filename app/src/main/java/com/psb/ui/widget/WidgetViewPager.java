package com.psb.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.psb.R;
import com.psb.ui.util.DisplayUtil;


/**
 * Created by zl on 2014/11/27.
 */
public class WidgetViewPager extends LinearLayout{

    private Context mContext;
    private LinearLayout layoutTitle, layoutTabs;
    private ImageView mCursor;
    private ViewPager mCustomViewPager;
    private int cursorColor = R.color.topbar;
    private int defaultTabColor = R.color.topbar;
    private int selectedTabColor = R.color.default_light_grey_color;
    private int textSize = 18;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号

    public WidgetViewPager(Context context) {
        this(context, null);
    }

    public WidgetViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_custom_viewpager, this, true);

        layoutTitle = (LinearLayout) findViewById(R.id.title);
        layoutTabs = (LinearLayout) findViewById(R.id.tabs);
        mCursor = (ImageView) findViewById(R.id.cursor);
        mCursor.setBackgroundColor(getResources().getColor(cursorColor));
        mCustomViewPager = (ViewPager) findViewById(R.id.custviewPager);
    }

    public ViewPager getCustomViewPager() {
        return mCustomViewPager;
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        mCustomViewPager.setCurrentItem(item, smoothScroll);
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
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1);
        for (String tab : tabs) {
            TextView tv = new TextView(mContext);
            tv.setText(tab);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(params);
            layoutTabs.addView(tv);
        }

        if(layoutTabs.getChildCount() == 2){
            ((TextView) layoutTabs.getChildAt(0)).setTextColor(getResources().getColor(defaultTabColor));
            ((TextView) layoutTabs.getChildAt(1)).setTextColor(getResources().getColor(selectedTabColor));
            layoutTabs.getChildAt(0).setOnClickListener(new MyOnClickListener(0));
            layoutTabs.getChildAt(1).setOnClickListener(new MyOnClickListener(1));
        }

        //计算偏移量
        offset = DisplayUtil.getDisplayMetrics().widthPixels / tabs.length;
        LayoutParams cursorParams = new LayoutParams(offset,
                DisplayUtil.dip2px(2));
        mCursor.setLayoutParams(cursorParams);
        mCustomViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int index) {
                ObjectAnimator animation = ObjectAnimator.ofFloat(mCursor, "x",
                        offset * currIndex, offset * index);
                currIndex = index;
                animation.setDuration(200);
                animation.start();
                for (int i = 0; i < layoutTabs.getChildCount(); i++) {
                    TextView tv = (TextView) layoutTabs.getChildAt(i);
                    if (i == index) {
                        tv.setTextColor(mContext.getResources().getColor(defaultTabColor));
                    } else {
                        tv.setTextColor(mContext.getResources().getColor(selectedTabColor));
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });


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
}
