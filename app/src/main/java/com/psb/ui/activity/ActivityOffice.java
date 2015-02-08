package com.psb.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import com.psb.R;
import com.psb.adapter.OfficeAdapter;
import com.psb.entity.OfficeInfo;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aako on 2015/2/1.
 */
public class ActivityOffice extends BaseActivity {

    private TopNavigationBar topbar;
    private ListView mList;
    private OfficeAdapter officeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);
        this.topbar = (TopNavigationBar) findViewById(R.id.topbar);
        this.mList = (ListView) findViewById(R.id.list);
        this.topbar.setActivity(this);

        officeAdapter = new OfficeAdapter(this);
        officeAdapter.setOfficeInfoList(Cache.getInstance().getOffice());
        mList.setAdapter(officeAdapter);
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_OFFICE_LIST);
        Api.getInstance().getOffice();

    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_OFFICE_LIST:
                officeAdapter.setOfficeInfoList(Cache.getInstance().getOffice());
                officeAdapter.notifyDataSetChanged();
                break;
        }
    }
}
