package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.adapter.AddrAdapter;
import com.psb.entity.Addr;
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
public class ActivityPoliceInfo extends BaseActivity implements AdapterView.OnItemClickListener {

    private TopNavigationBar topbar;
    private TextView addr, name, office_addr, tel;
    private ImageView img;
    private ListView list;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_list);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        addr = (TextView) findViewById(R.id.addr);
        name = (TextView) findViewById(R.id.name);
        office_addr = (TextView) findViewById(R.id.office_name);
        tel = (TextView) findViewById(R.id.tel);
        img = (ImageView) findViewById(R.id.img);
        list = (ListView) findViewById(R.id.list);
        intent = new Intent();
//        Api.getInstance().getPolice(Cache.getInstance().getUser().getAddress_id());
        this.init();
    }

    public void init() {
        addr.setText("白营镇张官屯");
        name.setText("张三");
        office_addr.setText("大付庄警务室");
        tel.setText("18503888888");
        ImageLoader.getInstance().displayImage("", img, ImageUtil.options);
        //取2级乡镇
        List<Addr> addrs = Cache.getInstance().getAddr().get(0).getChild();
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
}
