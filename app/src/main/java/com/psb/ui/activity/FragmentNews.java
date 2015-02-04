package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.psb.R;
import com.psb.adapter.GuideAdapter;
import com.psb.core.AppContext;
import com.psb.entity.NewsInfo;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.ViewPagerWithTitle;
import com.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentNews extends BaseFragment implements ViewPager.OnPageChangeListener {

    private View mView;
    private ViewPagerWithTitle viewPager;
    private List<View> pageViews = new ArrayList<>();
    private String newsColumns[] = AppContext.getInstance().getResources().getStringArray(R.array.news_columns);

//    private NewsPolice mNewsPolice;
//    private NewsPresence mNewsPresence;
//    private NewsWarning mNewsWarning;
//    private NewsSecurity mNewsSecurity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_news, container, false);
        viewPager = (ViewPagerWithTitle) mView.findViewById(R.id.vp);
        this.initView();

        EventNotifyCenter.getInstance().register(this.getHandler(), Event.NEWS_1, Event.NEWS_2, Event.NEWS_3, Event.NEWS_4);
        Api.getInstance().getArticle(1);
        return mView;
    }

    private void initView() {
        for (int i = 0; i < newsColumns.length; i++) {
            NewsPolice newsPolice = new NewsPolice(this.getActivity());
            newsPolice.setEvent(i + 1);
            pageViews.add(newsPolice);
        }
        viewPager.setTabs(newsColumns);
        viewPager.setPagerViews(pageViews);
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(this);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.NEWS_1:
            case Event.NEWS_2:
            case Event.NEWS_3:
            case Event.NEWS_4:
                NewsPolice news = (NewsPolice) pageViews.get(msg.what - 1);
                news.onRefreshComplete();
                news.setArticle(Cache.getInstance().getArticle(msg.what));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        NewsPolice news = (NewsPolice) pageViews.get(position);
        news.autoGetArticle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
