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

    private WheelView xian;
    private WheelView xiang;
    private WheelView cun;
    private String[] mProvinceDatas;
    private Map<String, String[]> mCitisDatasMap = new HashMap<>();
    private Map<String, String[]> mAreaDatasMap = new HashMap<>();
    private String mCurrentProviceName;
    private String mCurrentCityName;
    private String mCurrentAreaName = "";


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
        xian = (WheelView) findViewById(R.id.xian);
        xiang = (WheelView) findViewById(R.id.xiang);
        cun = (WheelView) findViewById(R.id.cun);

        initDatas();
        xian.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        xian.addChangingListener(this);
        xiang.addChangingListener(this);
        cun.addChangingListener(this);
        xian.setVisibleItems(5);
        xiang.setVisibleItems(5);
        cun.setVisibleItems(5);
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
        Api.getInstance().register(strId, strPwd, strName, 2, s, strTel);
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

        mProvinceDatas = new String[Cache.getInstance().getAddr().size()];
        for (int i = 0; i < Cache.getInstance().getAddr().size(); i++) {
            Addr addrxian = Cache.getInstance().getAddr().get(i);
            String strXian = addrxian.getName();
            mProvinceDatas[i] = strXian;
            String mCitiesDatas[] = new String[addrxian.getChild().size()];
            for (int j = 0; j < addrxian.getChild().size(); j++) {
                Addr xiang = addrxian.getChild().get(j);
                String strXiang = xiang.getName();
//                Log.d("strXiang", strXiang);
                mCitiesDatas[j] = strXiang;
                String mAreasDatas[] = new String[xiang.getChild().size()];
                for (int k = 0; k < xiang.getChild().size(); k++) {
                    mAreasDatas[k] = xiang.getChild().get(k).getName();
//                    Log.d("mAreasDatas", xiang.getChild().get(k).getName());
                }
                mAreaDatasMap.put(strXiang, mAreasDatas);
            }

            mCitisDatasMap.put(strXian, mCitiesDatas);
        }

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == xian) {
            updateCities();
        } else if (wheel == xiang) {
            updateAreas();
        } else if (wheel == cun) {
            mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
        }
    }

    private void updateCities() {
        int pCurrent = xian.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        xiang.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        xiang.setCurrentItem(0);
        updateAreas();
    }

    private void updateAreas() {
        int pCurrent = xiang.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mAreaDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        cun.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        cun.setCurrentItem(0);
    }
}
