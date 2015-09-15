package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.psb.R;
import com.psb.core.AppContext;
import com.psb.entity.NewsInfo;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.ViewPagerWithTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentGuide extends BaseFragment implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener {

    private View mView;
    //    private View query;
    private ViewPagerWithTitle viewPager;

    private String guideColumns[] = AppContext.getInstance().getResources().getStringArray(R.array.guide_columns);
    private List<View> pageViews = new ArrayList<>();

    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_guide, container, false);
//        query = mView.findViewById(R.id.query);
        viewPager = (ViewPagerWithTitle) mView.findViewById(R.id.vp);
        viewPager.setOnPageChangeListener(this);
        this.initView();
        intent = new Intent();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.NEWS_5, Event.NEWS_6, Event.NEWS_7, Event.NEWS_8, Event.NEWS_9);
        return mView;
    }

    private void initView() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < guideColumns.length; i++) {

            NewsGuide list = new NewsGuide(this.getActivity());
            list.setLayoutParams(params);
            list.setEvent(i + 5);
            list.getRefreshableView().setVerticalScrollBarEnabled(false);
            list.setOnItemClickListener(this);
            list.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
            pageViews.add(list);
        }
        viewPager.setTabs(guideColumns);
        viewPager.setPagerViews(pageViews);
        viewPager.setCurrentItem(0);

        NewsGuide news = (NewsGuide) pageViews.get(0);
        news.autoGetArticle();
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.NEWS_5:
            case Event.NEWS_6:
            case Event.NEWS_7:
            case Event.NEWS_8:
            case Event.NEWS_9:
                NewsGuide news = (NewsGuide) pageViews.get(msg.what - 5);
                news.onRefreshComplete();
                news.setArticle(Cache.getInstance().getArticle(msg.what));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsInfo news = (NewsInfo) parent.getAdapter().getItem(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", news.getId());
        bundle.putString("title", news.getTitle());
//        bundle.putString("content", news.getContent());
//        bundle.putString("url", news.getThumb());
        intent.setClass(this.getActivity(), ActivityWebView.class);
        intent.putExtras(bundle);
        this.getActivity().startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        NewsGuide guide = (NewsGuide) pageViews.get(position);
        guide.autoGetArticle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
