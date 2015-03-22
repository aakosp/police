package com.psb.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.psb.R;
import com.psb.adapter.WorkAdapter;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.DisplayUtil;
import com.psb.ui.util.ImageUtil;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by aako on 2015/3/15.
 */
public class ActivityWorkList extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    private TopNavigationBar top;
    private PullToRefreshListView list;
    private WorkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        top = (TopNavigationBar) findViewById(R.id.top);
        top.setActivity(this);
        list = (PullToRefreshListView) findViewById(R.id.list);
        adapter = new WorkAdapter(this);
        list.setAdapter(adapter);
        list.getRefreshableView().setDivider(new ColorDrawable(getResources().getColor(R.color.item_office_info_divider)));
        list.getRefreshableView().setDividerHeight(DisplayUtil.dip2px(0.5f));
        list.setOnRefreshListener(this);
        list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        Api.getInstance().getWork();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_WORK);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        Api.getInstance().getWork();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_WORK:
                adapter.setWorks(Cache.getInstance().getWorks());
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
