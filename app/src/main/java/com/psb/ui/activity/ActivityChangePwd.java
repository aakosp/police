package com.psb.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.psb.R;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ToastUtil;
import com.psb.ui.widget.TopNavigationBar;
import com.util.StringUtils;

/**
 * Created by aako on 2015/2/8.
 */
public class ActivityChangePwd extends BaseActivity {

    private TopNavigationBar topbar;
    private EditText old, newpwd, zaici;
    private Button btn;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        old = (EditText) findViewById(R.id.old);
        newpwd = (EditText) findViewById(R.id.newpwd);
        zaici = (EditText) findViewById(R.id.zaici);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(old.getText().toString())) {
                    ToastUtil.showToast(ActivityChangePwd.this, "请输入原密码", 0);
                    return;
                }
                pwd = newpwd.getText().toString();
                if (StringUtils.isEmpty(pwd)) {
                    ToastUtil.showToast(ActivityChangePwd.this, "请输入新密码", 0);
                    return;
                }
                if (!pwd.equals(zaici.getText().toString())) {
                    ToastUtil.showToast(ActivityChangePwd.this, "两次输入的密码不一致,请重新输入", 0);
                }
                Api.getInstance().changePwd(pwd);
            }
        });
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.CHANGE_PWD);
    }

    @Override
    protected void onDestroy() {
        EventNotifyCenter.getInstance().unregister(this.getHandler(), Event.CHANGE_PWD);
        super.onDestroy();
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.CHANGE_PWD:
                ToastUtil.showToast(this, "密码修改成功", 0);
                break;
        }
    }
}
