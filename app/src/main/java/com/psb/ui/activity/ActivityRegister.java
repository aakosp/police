package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.psb.R;
import com.psb.entity.Addr;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ToastUtil;
import com.psb.ui.widget.TopNavigationBar;
import com.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by zl on 2015/1/29.
 */
public class ActivityRegister extends BaseActivity implements OnWheelChangedListener {

    private TopNavigationBar topbar;
    private EditText id, pwd, rePwd, name, tel;
    private Button reg;
    private RadioButton male = null;
    private RadioButton female = null;
    private Intent intent;

    private WheelView wvXiang;
    private WheelView wvCun;
    private AddrItem[] mXiangDatas;
    private Map<Integer, AddrItem[]> mCunDatasMap = new HashMap<>();
    private int mCurrentXiangName, mCurrentCunName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        id = (EditText) findViewById(R.id.id);
        pwd = (EditText) findViewById(R.id.pwd);
        rePwd = (EditText) findViewById(R.id.zaici);
        name = (EditText) findViewById(R.id.name);
        tel = (EditText) findViewById(R.id.tel);
        male = (RadioButton) findViewById(R.id.male);
        male.setChecked(true);
        female = (RadioButton) findViewById(R.id.female);
        reg = (Button) findViewById(R.id.register);
        wvXiang = (WheelView) findViewById(R.id.xiang);
        wvCun = (WheelView) findViewById(R.id.cun);

        initDatas();
        wvXiang.setViewAdapter(new ArrayWheelAdapter<AddrItem>(this, mXiangDatas));
        wvXiang.addChangingListener(this);
        wvCun.addChangingListener(this);
        wvXiang.setVisibleItems(5);
        wvCun.setVisibleItems(5);
//        updateCities();
        updateAreas();


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        intent = new Intent();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.REGISTER);

    }

    public void register() {


        String strId = id.getText().toString();
        String strPwd = pwd.getText().toString();
        String strRePwd = rePwd.getText().toString();
        String strName = name.getText().toString();
        String strTel = tel.getText().toString();

        if (StringUtils.isEmpty(strId)) {
            ToastUtil.showToast(this, "账号不能为空", 0);
            return;
        }
        if (StringUtils.isEmpty(strPwd)) {
            ToastUtil.showToast(this, "密码不能为空", 0);
            return;
        }
        if (!strPwd.equals(strRePwd)) {
            ToastUtil.showToast(this, "两次密码输入不一致", 0);
            return;
        }
        if (StringUtils.isEmpty(strName)) {
            ToastUtil.showToast(this, "用户名不能为空", 0);
            return;
        }

        String s = "WOMEN";
        if (male.isChecked()) {
            s = "MEN";
        }
//        Log.d("mCurrentCunName", "======================    "+mCurrentCunName);
        Api.getInstance().register(strId, strPwd, strName, mCurrentCunName, s, strTel);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.REGISTER:
                if (!StringUtils.isEmpty(Cache.getInstance().getReg().getError())) {
                    ToastUtil.showToast(this, Cache.getInstance().getReg().getError(), 0);
                    return;
                }
                intent.setClass(this, ActivityRegisterSuccess.class);
                this.finish();
                break;
            default:
                return;
        }
        this.startActivity(intent);
    }

    private void initDatas() {
        if (null == Cache.getInstance().getAddr()) {
            return;
        }
        mXiangDatas = new AddrItem[Cache.getInstance().getAddr().size()];
        for (int i = 0; i < Cache.getInstance().getAddr().size(); i++) {
            Addr addrxiang = Cache.getInstance().getAddr().get(i);
            AddrItem xiangItem = new AddrItem(addrxiang.getId(), addrxiang.getName());
            mXiangDatas[i] = xiangItem;
            AddrItem mCunDatas[] = new AddrItem[addrxiang.getChild().size()];
            for(int j=0; j<addrxiang.getChild().size(); j++){
                mCunDatas[j] = new AddrItem(addrxiang.getChild().get(j).getId(), addrxiang.getChild().get(j).getName());
            }
            mCunDatasMap.put(addrxiang.getId(), mCunDatas);
        }

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
//        if (wheel == wvXian) {
//            updateCities();
//        } else
        if (wheel == wvXiang) {
            updateAreas();
        } else if (wheel == wvCun) {
            mCurrentCunName = mCunDatasMap.get(mCurrentXiangName)[newValue].id;
        }
    }

//    private void updateCities() {
//        int pCurrent = wvXian.getCurrentItem();
//        mCurrentXianName = mXianDatas[pCurrent].id;
//        Log.d("     updateCities  ", "mCurrentXianName : " + mCurrentXianName);
//        AddrItem[] cities = mXiangDatasMap.get(mCurrentXianName);
//        if (cities == null) {
//            return;
//        }
//        wvXiang.setViewAdapter(new ArrayWheelAdapter<AddrItem>(this, cities));
//        wvXiang.setCurrentItem(0);
//        updateAreas();
//    }

    private void updateAreas() {
        int pCurrent = wvXiang.getCurrentItem();
        mCurrentXiangName = mXiangDatas[pCurrent].id;
        AddrItem[] areas = mCunDatasMap.get(mCurrentXiangName);

        if (areas == null) {
            return;
        }
        wvCun.setViewAdapter(new ArrayWheelAdapter<AddrItem>(this, areas));
        wvCun.setCurrentItem(0);
        mCurrentCunName = mCunDatasMap.get(mCurrentXiangName)[0].id;
    }


    private class AddrItem {

        public AddrItem(int id, String addr) {
            this.id = id;
            this.addr = addr;
        }

        public String addr;
        public int id;

        @Override
        public String toString() {
            return addr;
        }
    }
}
