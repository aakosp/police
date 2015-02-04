package com.psb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.adapter.NewsAdapter;
import com.psb.core.AppContext;
import com.psb.entity.Article;
import com.psb.entity.NewsInfo;
import com.psb.protocol.Api;
import com.psb.ui.util.ImageUtil;
import com.psb.ui.widget.ViewPagerWithPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/1/26.
 */
public class NewsPolice extends LinearLayout implements PullToRefreshBase.OnRefreshListener<ScrollView>, AdapterView.OnItemClickListener, View.OnClickListener {

    private PullToRefreshScrollView mPullToRefreshScrollView;
    private ViewPagerWithPoint banner;
    private ListView listView;
    private NewsAdapter adapter;
    private Intent intent;
    private int event = 0;
    private long request_time = 0;

    public NewsPolice(Context context) {
        this(context, null);
    }

    public NewsPolice(Context context, AttributeSet attrs) {
        super(context, attrs);
        intent = new Intent();
        intent.setClass(context, ActivityNewsDetail.class);
        LayoutInflater.from(context).inflate(R.layout.news_police, this, true);
        mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.refresh_view);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        banner = (ViewPagerWithPoint) findViewById(R.id.banner);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getEvent() {
        return this.event;
    }

    public void setBanner(List<String> urls) {
        if (urls.size() > 0) {
            banner.setVisibility(View.VISIBLE);
        } else {
            banner.setVisibility(View.GONE);
            return;
        }
        List<View> banners = new ArrayList<>();
        for (String url : urls) {
            ImageView img = new ImageView(this.getContext());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage(url, img, ImageUtil.options);
            banners.add(img);
            img.setOnClickListener(this);
        }
        banner.setPagerViews(banners);
        banner.setCurrentItem(0);
    }

    public void setArticle(Article article) {
        if (null == adapter) {
            adapter = new NewsAdapter(article.getData());
            listView.setAdapter(adapter);
        } else {
            adapter.setArticle(article);
        }
        if (article.getPer_page() == 1) {
            this.scrollTop();
        }
        adapter.notifyDataSetChanged();
    }

    public void scrollTop() {
        if (null != mPullToRefreshScrollView) {
            mPullToRefreshScrollView.getRefreshableView().smoothScrollTo(0, 0);
        }
    }

    public void autoGetArticle() {
        if (System.currentTimeMillis() - request_time > AppContext.auto_request_time_lag) {
            request_time = System.currentTimeMillis();
            mPullToRefreshScrollView.setRefreshing(true);
            Api.getInstance().getArticle(event);
        }
    }

    public void onRefreshComplete() {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsInfo news = (NewsInfo) parent.getAdapter().getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("title", news.getTitle());
        bundle.putString("content", news.getContent());
        bundle.putString("url", news.getThumb());
        intent.putExtras(bundle);
        this.getContext().startActivity(intent);
    }

    @Override
    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        request_time = System.currentTimeMillis();
        Api.getInstance().getArticle(event);
        //TODO: Post Request
//        mPullToRefreshScrollView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPullToRefreshScrollView.onRefreshComplete();
//            }
//        }, 5000);
    }

    @Override
    public void onClick(View v) {
        Intent t = new Intent();
        t.setClass(this.getContext(), ActivityRegister.class);
        this.getContext().startActivity(t);
    }
}
