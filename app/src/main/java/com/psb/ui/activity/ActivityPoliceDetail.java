package com.psb.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import com.psb.R;
import com.psb.adapter.PoliceAdapter;
import com.psb.entity.Addr;
import com.psb.entity.PoliceInfo;
import com.psb.entity.User;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aako on 2015/2/8.
 */
public class ActivityPoliceDetail extends BaseActivity {

    private TopNavigationBar topbar;
    private ListView list;
    private PoliceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_detial);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        list = (ListView) findViewById(R.id.list);
        topbar.setTitleText(this.getIntent().getStringExtra("strAddr"));
        int i = this.getIntent().getIntExtra("addr", 0);
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_POLICE_LIST);
        if (i > 0) {
            Api.getInstance().getPolice(i);
        }
        adapter = new PoliceAdapter(this);
        list.setAdapter(adapter);

    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_POLICE_LIST:
                adapter.setPoliceInfos(Cache.getInstance().getPoliceInfo());
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
