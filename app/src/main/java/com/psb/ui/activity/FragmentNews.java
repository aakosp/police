package com.psb.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.psb.R;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.WidgetViewPager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentNews extends BaseFragment {

    private View mView;
    private WidgetViewPager viewPager;
    private List<View> pageViews = new ArrayList<>();
    private String newsColumns[] = this.getResources().getStringArray(R.array.news_columns);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_news, container, false);
        viewPager = (WidgetViewPager) mView.findViewById(R.id.vp);
        return mView;
    }

    private void initView(){
        pageViews.add(new NewsPolice(this.getActivity()));
        pageViews.add(new NewsPresence(this.getActivity()));
        pageViews.add(new NewsWarning(this.getActivity()));
        pageViews.add(new NewsSecurity(this.getActivity()));
        viewPager.setTabs(newsColumns);
        viewPager.setAdapter(new MyViewPagerAdapter(pageViews));
        viewPager.setCurrentItem(0);
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
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
