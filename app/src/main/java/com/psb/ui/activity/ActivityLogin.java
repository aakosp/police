package com.psb.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.psb.R;
import com.psb.entity.User;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ToastUtil;
import com.psb.ui.widget.TopNavigationBar;
import com.util.Md5Helper;
import com.util.StringUtils;

/**
 * Created by zl on 2015/1/30.
 */
public class ActivityLogin extends BaseActivity implements View.OnClickListener {

    private TopNavigationBar topbar;
    private EditText id, pwd;
    private Button login, register, report;
    private Intent intent;
    private String strId;
    private String strPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent();
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        id = (EditText) findViewById(R.id.id);
        pwd = (EditText) findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        report = (Button) findViewById(R.id.report);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        report.setOnClickListener(this);

        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_USER);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                strId = id.getText().toString();
                if (StringUtils.isEmpty(strId)) {
                    ToastUtil.showToast(this, "用户名为空", 0);
                    return;
                }
                strPwd = pwd.getText().toString();
                if (StringUtils.isEmpty(strId)) {
                    ToastUtil.showToast(this, "密码为空", 0);
                    return;
                }
                Api.getInstance().getUser(strId);
                return;
            case R.id.register:
                intent.setClass(this, ActivityRegister.class);
                break;
            case R.id.report:
                intent.setClass(this, ActivityOptions.class);
                intent.putExtra("login", true);
                break;
            default:
                return;
        }
        this.startActivity(intent);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_USER:
                User user = Cache.getInstance().getUser(strId);
                if (null == user) {
                    ToastUtil.showToast(this, "用户名或密码错误", 0);
                    return;
                }
                String md5 = Md5Helper.encode(strPwd);
                if (md5.equals(user.getPassword())) {
                    Cache.getInstance().setId(strId);
                    Cache.getInstance().setUser(user);
                    Cache.getInstance().setLogin(true);
//                    Log.d(user.getName(), "" + user.getAddress());
//                    intent.setClass(this, ActivityMain.class);
//                    this.startActivity(intent);
                    this.finish();
                } else {
                    ToastUtil.showToast(this, "用户名或密码错误", 0);
                }
                break;
        }
    }
}
