package com.psb.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.adapter.AddrAdapter;
import com.psb.entity.Addr;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ImageUtil;
import com.psb.ui.widget.TopNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/2/5.
 */
public class ActivityPoliceInfo extends BaseActivity {

    private TopNavigationBar topbar;
    private TextView addr, name, office_addr, tel;
    private ImageView img;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_list);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        addr = (TextView) findViewById(R.id.addr);
        name = (TextView) findViewById(R.id.name);
        office_addr = (TextView) findViewById(R.id.office_name);
        tel = (TextView) findViewById(R.id.tel);
        img = (ImageView) findViewById(R.id.img);
        list = (ListView) findViewById(R.id.list);
        this.init();
    }

    public void init() {
        addr.setText("白营镇张官屯");
        name.setText("张三");
        office_addr.setText("大付庄警务室");
        tel.setText("18503888888");
        ImageLoader.getInstance().displayImage("", img, ImageUtil.options);
        List<Addr> addrs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Addr addr = new Addr();
            addr.setId(i);
            addr.setName("地址：" + i);
            addrs.add(addr);
        }
        AddrAdapter addrAdapter = new AddrAdapter();
        addrAdapter.setAddrs(addrs);
        list.setAdapter(addrAdapter);

    }


}
