package com.psb.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.psb.R;
import com.psb.core.AppContext;
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
public class ActivityOpinionProcessing extends BaseActivity implements ViewPager.OnPageChangeListener {

    private TopNavigationBar topbar;
    private Button unProcess, process;
    List<View> pageViews;
    private ViewPagerWithTitle viewPager;
    private String titles[] = AppContext.getInstance().getResources().getStringArray(R.array.options_columns);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_opinion);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        unProcess = (Button) findViewById(R.id.un_process);
        process = (Button) findViewById(R.id.processed);
        viewPager = (ViewPagerWithTitle) findViewById(R.id.vp);

        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_OPINION_LIST);
        Api.getInstance().getOpinions();
    }

    private void initView() {
        pageViews = new ArrayList<>();
        PullToRefreshListView unList = new PullToRefreshListView(this);
        PullToRefreshListView processList = new PullToRefreshListView(this);
        viewPager.setTabs(titles);
        viewPager.setPagerViews(pageViews);
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(this);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_OPINION_LIST:
                Cache.getInstance().getOpinions();
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
}
