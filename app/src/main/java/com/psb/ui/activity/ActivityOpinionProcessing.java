package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.psb.R;
import com.psb.adapter.OpinionsAdapter;
import com.psb.core.AppContext;
import com.psb.entity.Opinion;
import com.psb.entity.User;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;
import com.psb.ui.widget.ViewPagerWithTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/2/6.
 */
public class ActivityOpinionProcessing extends BaseActivity implements ViewPager.OnPageChangeListener, PullToRefreshBase.OnRefreshListener2<ListView> {

    private TopNavigationBar topbar;
    List<View> pageViews;
    private ViewPagerWithTitle viewPager;
    private String titles[];
    private boolean isPolice = false;
    private int ok_page = -1;
    private int un_page = -1;
    private int ok_lastpage = -1;
    private int un_lastpage = -1;
    private PullToRefreshListView unList, okList;
    private OpinionsAdapter ok, undo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_opinion);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        viewPager = (ViewPagerWithTitle) findViewById(R.id.vp);
        isPolice = User.POLICE.equals(Cache.getInstance().getUser().getRole());
        if (isPolice) {
            titles = AppContext.getInstance().getResources().getStringArray(R.array.options_columns);
        } else {
            titles = AppContext.getInstance().getResources().getStringArray(R.array.user_options_columns);
        }

        this.initView();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_OPINION_LIST_UNDO, Event.GET_OPINION_LIST_OK, Event.REFRESH_OPINION);
        if (isPolice) {
            Api.getInstance().getOpinionsUndo(-1, un_page);
            Api.getInstance().getOpinionsOK(-1, ok_page);
        } else {
            Api.getInstance().getOpinionsUndo(Cache.getInstance().getUser().getId(), un_page);
            Api.getInstance().getOpinionsOK(Cache.getInstance().getUser().getId(), ok_page);
        }
        intent = new Intent();
    }

    private void initView() {
        pageViews = new ArrayList<>();
        unList = new PullToRefreshListView(this);
        okList = new PullToRefreshListView(this);
        unList.setOnRefreshListener(this);
        okList.setOnRefreshListener(this);
        ok = new OpinionsAdapter(this);
        undo = new OpinionsAdapter(this);
        okList.setAdapter(ok);
        unList.setAdapter(undo);
        pageViews.add(unList);
        pageViews.add(okList);
        viewPager.setTabs(titles);
        viewPager.setPagerViews(pageViews);
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(this);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_OPINION_LIST_OK:
                okList.onRefreshComplete();
                ok_page = Cache.getInstance().getOpinions_ok().getCurrent_page();
                ok_lastpage = Cache.getInstance().getOpinions_ok().getLast_page();
                ok.setOpinions(Cache.getInstance().getOpinions_ok());
                break;
            case Event.GET_OPINION_LIST_UNDO:
                unList.onRefreshComplete();
                un_page = Cache.getInstance().getOpinions_undo().getCurrent_page();
                un_lastpage = Cache.getInstance().getOpinions_undo().getLast_page();
                undo.setOpinions(Cache.getInstance().getOpinions_undo());
                break;
            case Event.REFRESH_OPINION:
                if (isPolice) {
                    Api.getInstance().getOpinionsUndo(-1, un_page);
                    Api.getInstance().getOpinionsOK(-1, ok_page);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        int currnt = viewPager.getCurrentItem();
        if (currnt == 0) {
            if (isPolice) {
                Api.getInstance().getOpinionsUndo(-1, un_page);
            } else {
                Api.getInstance().getOpinionsUndo(Cache.getInstance().getUser().getId(), un_page);
            }
        } else {
            if (isPolice) {
                Api.getInstance().getOpinionsOK(-1, ok_page);
            } else {
                Api.getInstance().getOpinionsOK(Cache.getInstance().getUser().getId(), ok_page);
            }
        }
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        int currnt = viewPager.getCurrentItem();
        if (currnt == 0) {
            if (un_page == un_lastpage) {
                unList.onRefreshComplete();
                return;
            }
            if (isPolice) {
                Api.getInstance().getOpinionsUndo(-1, un_page);
            } else {
                Api.getInstance().getOpinionsUndo(Cache.getInstance().getUser().getId(), un_page);
            }
        } else {
            if (ok_page == ok_lastpage) {
                okList.onRefreshComplete();
                return;
            }
            if (isPolice) {
                Api.getInstance().getOpinionsOK(-1, ok_page);
            } else {
                Api.getInstance().getOpinionsOK(Cache.getInstance().getUser().getId(), ok_page);
            }
        }
    }
}
