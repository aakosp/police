package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.psb.R;
import com.psb.adapter.GuideAdapter;
import com.psb.adapter.NewsAdapter;
import com.psb.entity.Article;
import com.psb.entity.NewsInfo;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by aako on 2015/3/21.
 */
public class ActivityNotice extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView>, AdapterView.OnItemClickListener{

    private TopNavigationBar top;
    private PullToRefreshListView list;
    private int current_page = 1;
    private int lastPage = 1;
//    private long request_time = 0;
    private NewsAdapter adapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        top = (TopNavigationBar) findViewById(R.id.top);
        top.setActivity(this);
        list = (PullToRefreshListView) findViewById(R.id.list);
        list.setOnRefreshListener(this);
        list.setOnItemClickListener(this);
        adapter = new NewsAdapter();
        list.setAdapter(adapter);
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.NOTICE);
        Api.getInstance().getArticle(Event.NOTICE, 0);
        intent = new Intent();
        intent.setClass(this, ActivityNewsDetail.class);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//        request_time = System.currentTimeMillis();
        Api.getInstance().getArticle(Event.NOTICE, 0);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (current_page == lastPage) {
            list.onRefreshComplete();
            return;
        }
        if (!Api.getInstance().getArticle(Event.NOTICE, current_page + 1)) {
            list.onRefreshComplete();
            return;
        }
//        request_time = System.currentTimeMillis();
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.NOTICE:
                list.onRefreshComplete();
                Article news = Cache.getInstance().getArticle(msg.what);
                current_page = news.getCurrent_page();
                lastPage = news.getLast_page();
                adapter.setArticle(news);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsInfo news = (NewsInfo) parent.getAdapter().getItem(position);
        Bundle bundle = new Bundle();
        bundle.putBoolean("notice", true);
        bundle.putString("title", news.getTitle());
        bundle.putString("content", news.getContent());
        bundle.putString("url", news.getThumb());
        intent.putExtras(bundle);
        this.startActivity(intent);
    }
}
