package com.psb.ui.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.psb.adapter.GuideAdapter;
import com.psb.core.AppContext;
import com.psb.entity.Article;
import com.psb.protocol.Api;

/**
 * Created by zl on 2015/2/4.
 */
public class NewsGuide extends PullToRefreshListView implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private int event = 0;
    private long request_time = 0;
    private int current_page = 1;
    private int lastPage = 1;
    private GuideAdapter adapter;

    public NewsGuide(Context context) {
        super(context);
    }

    public NewsGuide(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setMode(Mode.BOTH);
        this.setOnRefreshListener(this);
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public void autoGetArticle() {
        if (System.currentTimeMillis() - request_time > AppContext.auto_request_time_lag) {
            request_time = System.currentTimeMillis();
            this.setRefreshing(true);
            Api.getInstance().getArticle(event, 0);
        }
    }

    public void setArticle(Article article) {
        if (null == article) {
            return;
        }
        current_page = article.getCurrent_page();
        lastPage = article.getLast_page();
        if (null == adapter) {
            adapter = new GuideAdapter(this.getContext());
            this.setAdapter(adapter);
        }
        adapter.setArticle(article);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        Log.d("onRefresh", "onRefresh");
        request_time = System.currentTimeMillis();
        Api.getInstance().getArticle(event, 0);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (current_page == lastPage) {
            this.onRefreshComplete();
            return;
        }
        if (!Api.getInstance().getArticle(event, lastPage)) {
            this.onRefreshComplete();
            return;
        }
        request_time = System.currentTimeMillis();
    }
}
