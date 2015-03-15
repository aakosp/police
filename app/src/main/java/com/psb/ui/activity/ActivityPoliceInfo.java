package com.psb.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.adapter.AddrAdapter;
import com.psb.entity.Addr;
import com.psb.entity.PoliceInfo;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ImageUtil;
import com.psb.ui.widget.TopNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/2/5.
 */
public class ActivityPoliceInfo extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private TopNavigationBar topbar;
    private TextView addr, name, office_addr, tel;
    //    private ImageView img;
    private View layAddr;
    private ListView list;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_list);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        layAddr = findViewById(R.id.lay_addr);
        addr = (TextView) findViewById(R.id.addr);
        name = (TextView) findViewById(R.id.name);
        office_addr = (TextView) findViewById(R.id.office_name);
        office_addr.setOnClickListener(this);
        tel = (TextView) findViewById(R.id.tel);
        tel.setOnClickListener(this);
//        img = (ImageView) findViewById(R.id.img);
        list = (ListView) findViewById(R.id.list);
        intent = new Intent();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_POLICE_LIST);
        if (!Cache.getInstance().isLogin()) {
            layAddr.setVisibility(View.GONE);
        } else {
            layAddr.setVisibility(View.VISIBLE);
            Api.getInstance().getPolice(Cache.getInstance().getUser().getAddress());
        }
        this.init();
    }

    public void init() {
        //取2级乡镇
        List<Addr> addrs = Cache.getInstance().getAddr();
        AddrAdapter addrAdapter = new AddrAdapter();
        addrAdapter.setAddrs(addrs);
        list.setAdapter(addrAdapter);
        list.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AddrAdapter addrAdapter = (AddrAdapter) parent.getAdapter();
        Addr addr = (Addr) addrAdapter.getItem(position);
        intent.setClass(this, ActivityPoliceDetail.class);
        intent.putExtra("addr", addr.getId());
        intent.putExtra("strAddr", addr.getName());
        this.startActivity(intent);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_POLICE_LIST:
                if (Cache.getInstance().getPoliceInfo().size() == 0) {
                    return;
                }
                PoliceInfo info = Cache.getInstance().getPoliceInfo().get(0);
                if (null == info || null == info.getPolice()) {
                    layAddr.setVisibility(View.GONE);
                } else {
                    layAddr.setVisibility(View.VISIBLE);
                    addr.setText(info.getName());
                    name.setText(info.getPolice().getPolice_name());
                    office_addr.setText(info.getPolice().getPolice_station_name());
                    office_addr.setTag(info.getPolice().getPolice_station_id());
                    tel.setText(info.getPolice().getPhone());
                    tel.setTag(info.getPolice().getPhone());
//                    ImageLoader.getInstance().displayImage("", img, ImageUtil.options);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.office_name:
                Intent intent = new Intent();
                intent.putExtra("id", (int) v.getTag());
                intent.setClass(this, ActivityMap.class);
                this.startActivity(intent);
                break;
            case R.id.tel:
                Uri telUri = Uri.parse("tel:" + v.getTag());
                Intent telIntent = new Intent(Intent.ACTION_DIAL, telUri);
                this.startActivity(telIntent);
                break;
        }
    }
}
