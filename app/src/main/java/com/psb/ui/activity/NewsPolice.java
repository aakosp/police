package com.psb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.psb.R;
import com.psb.adapter.NewsAdapter;
import com.psb.core.AppContext;
import com.psb.entity.Article;
import com.psb.entity.NewsInfo;
import com.psb.protocol.Api;
import com.psb.ui.util.ImageUtil;
import com.psb.ui.widget.InnerLayout;
import com.psb.ui.widget.ViewPagerWithPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class NewsPolice extends LinearLayout implements PullToRefreshBase.OnRefreshListener2<ScrollView>, AdapterView.OnItemClickListener, View.OnClickListener {

    private PullToRefreshScrollView mPullToRefreshScrollView;
    private ViewPagerWithPoint banner;
    private InnerLayout listView;
    private NewsAdapter adapter;
    private Intent intent;
    private int event = 0;
    private long request_time = 0;
    private int current_page = 1;
    private int last_page = 1;

    public NewsPolice(Context context) {
        this(context, null);
    }

    public NewsPolice(Context context, AttributeSet attrs) {
        super(context, attrs);
        intent = new Intent();
        intent.setClass(context, ActivityNewsDetail.class);
        LayoutInflater.from(context).inflate(R.layout.news_police, this, true);
        mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.refresh_view);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        banner = (ViewPagerWithPoint) findViewById(R.id.banner);
        listView = (InnerLayout) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
    }

    public int getEvent() {
        return this.event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public void setBanner(List<NewsInfo> banners) {
        if (null == banners || banners.size() == 0) {
            banner.setVisibility(View.GONE);
            return;
        }
        banner.setVisibility(View.VISIBLE);
        List<View> vBanners = new ArrayList<>();
        for (NewsInfo info : banners) {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView img = new ImageView(this.getContext());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setLayoutParams(params);
            ImageLoader.getInstance().displayImage(info.getThumb() + ImageUtil.BANNER, img, ImageUtil.options);
            vBanners.add(img);
            img.setTag(info);
            img.setOnClickListener(this);
        }
        banner.setPagerViews(vBanners);
        banner.setCurrentItem(0);
    }

    public void setArticle(Article article) {
        if (null == article) {
            return;
        }
        current_page = article.getCurrent_page();
        last_page = article.getLast_page();
        this.setBanner(article.getBanner());
        if (null == adapter) {
            adapter = new NewsAdapter(article.getData());
            listView.setAdapter(adapter);
        } else {
            adapter.setArticle(article);
        }
        adapter.notifyDataSetChanged();
    }

    public void autoGetArticle() {
        if (System.currentTimeMillis() - request_time > AppContext.auto_request_time_lag) {
            request_time = System.currentTimeMillis();
            mPullToRefreshScrollView.setRefreshing(true);
            Api.getInstance().getArticle(event, 0);
        }
    }

    public void onRefreshComplete() {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsInfo news = (NewsInfo) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("title", news.getTitle());
        bundle.putString("content", news.getContent());
        bundle.putString("url", news.getThumb());
        intent.putExtras(bundle);
        this.getContext().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        NewsInfo news = (NewsInfo) v.getTag();
        if (null == news) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("title", news.getTitle());
        bundle.putString("content", news.getContent());
        bundle.putString("url", news.getThumb());
        intent.putExtras(bundle);
        this.getContext().startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        if (!Api.getInstance().getArticle(event, 0)) {
            this.onRefreshComplete();
            return;
        }
        request_time = System.currentTimeMillis();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        if (current_page == last_page) {
            this.onRefreshComplete();
            return;
        }
        if (!Api.getInstance().getArticle(event, current_page + 1)) {
            this.onRefreshComplete();
            return;
        }
        request_time = System.currentTimeMillis();
    }

}
