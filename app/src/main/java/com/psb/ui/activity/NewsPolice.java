package com.psb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.psb.R;
import com.psb.adapter.NewsAdapter;
import com.psb.entity.NewsTitle;
import com.psb.ui.widget.ViewPagerWithPoint;
import com.psb.ui.widget.ViewPagerWithTitle;

import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class NewsPolice extends LinearLayout implements PullToRefreshBase.OnRefreshListener<PullToRefreshScrollView>, AdapterView.OnItemClickListener {

    private PullToRefreshScrollView mPullToRefreshScrollView;
    private ViewPagerWithPoint banner;
    private ListView listView;
    private NewsAdapter adapter;
    private Intent intent;

    public NewsPolice(Context context) {
        this(context, null);
    }

    public NewsPolice(Context context, AttributeSet attrs) {
        super(context, attrs);
        intent = new Intent();
        intent.setClass(context, ActivityNewsDetail.class);
        LayoutInflater.from(context).inflate(R.layout.news_police, this, true);
        mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.refresh_view);
        banner = (ViewPagerWithPoint) findViewById(R.id.banner);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
    }

    public void setBanner(List<View> banners) {
        banner.setPagerViews(banners);
        banner.setCurrentItem(0);
    }

    public void addNews(List<NewsTitle> news) {
        if (null == adapter) {
            adapter = new NewsAdapter(news);
            listView.setAdapter(adapter);
        } else {
            adapter.addNews(news);
            adapter.notifyDataSetChanged();
        }
    }

    public void scrollTop() {
        if (null != mPullToRefreshScrollView) {
            mPullToRefreshScrollView.getRefreshableView().smoothScrollTo(0, 0);
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase<PullToRefreshScrollView> refreshView) {
        //TODO: Post Request
        mPullToRefreshScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshScrollView.onRefreshComplete();
            }
        }, 5000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsTitle news = (NewsTitle) parent.getAdapter().getItem(position);
        this.getContext().startActivity(intent);
    }
}
