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

    private WheelView wvXian;
    private WheelView wvXiang;
    private WheelView wvCun;
    private AddrItem[] mProvinceDatas;
    private Map<Integer, AddrItem[]> mCitisDatasMap = new HashMap<>();
    private Map<Integer, AddrItem[]> mAreaDatasMap = new HashMap<>();
    private int mCurrentProviceName, mCurrentCityName, mCurrentAreaName;


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
        wvXian = (WheelView) findViewById(R.id.xian);
        wvXiang = (WheelView) findViewById(R.id.xiang);
        wvCun = (WheelView) findViewById(R.id.cun);

        initDatas();
        wvXian.setViewAdapter(new ArrayWheelAdapter<AddrItem>(this, mProvinceDatas));
        wvXian.addChangingListener(this);
        wvXiang.addChangingListener(this);
        wvCun.addChangingListener(this);
        wvXian.setVisibleItems(5);
        wvXiang.setVisibleItems(5);
        wvCun.setVisibleItems(5);
        updateCities();
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
//        Log.d("mCurrentAreaName", "======================    "+mCurrentAreaName);
        Api.getInstance().register(strId, strPwd, strName, mCurrentAreaName, s, strTel);
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
        mProvinceDatas = new AddrItem[Cache.getInstance().getAddr().size()];
        for (int i = 0; i < Cache.getInstance().getAddr().size(); i++) {
            Addr addrxian = Cache.getInstance().getAddr().get(i);
            AddrItem xianItem = new AddrItem(addrxian.getId(), addrxian.getName());
            mProvinceDatas[i] = xianItem;
            AddrItem mCitiesDatas[] = new AddrItem[addrxian.getChild().size()];
            for (int j = 0; j < addrxian.getChild().size(); j++) {
                Addr xiang = addrxian.getChild().get(j);
                AddrItem xiangItem = new AddrItem(xiang.getId(), xiang.getName());
                mCitiesDatas[j] = xiangItem;
                AddrItem mAreasDatas[] = new AddrItem[xiang.getChild().size()];
                for (int k = 0; k < xiang.getChild().size(); k++) {
                    Addr cun = xiang.getChild().get(k);
                    mAreasDatas[k] = new AddrItem(cun.getId(), cun.getName());
//                    Log.d("mAreasDatas", xiang.getChild().get(k).getName());
                }
                mAreaDatasMap.put(xiang.getId(), mAreasDatas);
            }

            mCitisDatasMap.put(addrxian.getId(), mCitiesDatas);
        }

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == wvXian) {
            updateCities();
        } else if (wheel == wvXiang) {
            updateAreas();
        } else if (wheel == wvCun) {
            mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue].id;
            Log.d("     onChanged  ", "mCurrentAreaName : "+mCurrentAreaName);
        }
    }

    private void updateCities() {
        int pCurrent = wvXian.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent].id;
        Log.d("     updateCities  ", "mCurrentProviceName : "+mCurrentProviceName);
        AddrItem[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            return;
        }
        wvXiang.setViewAdapter(new ArrayWheelAdapter<AddrItem>(this, cities));
        wvXiang.setCurrentItem(0);
        updateAreas();
    }

    private void updateAreas() {
        int pCurrent = wvXiang.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent].id;
        Log.d("     mCurrentCityName  ", "mCurrentCityName : "+mCurrentCityName);
        AddrItem[] areas = mAreaDatasMap.get(mCurrentCityName);

        if (areas == null) {
            return;
        }
        wvCun.setViewAdapter(new ArrayWheelAdapter<AddrItem>(this, areas));
        wvCun.setCurrentItem(0);
        mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[0].id;
        Log.d("mCurrentAreaName", "mCurrentAreaName : "+mCurrentAreaName);
    }


    private class AddrItem {

        public AddrItem(int id, String addr){
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
