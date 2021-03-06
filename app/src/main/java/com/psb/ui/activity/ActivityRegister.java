package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by zl on 2015/1/29.
 */
public class ActivityRegister extends BaseActivity implements View.OnClickListener {

    private TopNavigationBar topbar;
    private EditText id, pwd, rePwd, name, tel;
    private Button reg;
    private TextView text_area, text_sex;
    private View area, sex;
    private int areaId = 0;
    private int sexid = -1;

    private Intent intent;

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
        reg = (Button) findViewById(R.id.register);
        text_area = (TextView) findViewById(R.id.text_area);
        text_sex = (TextView) findViewById(R.id.text_sex);
        area = findViewById(R.id.area);
        sex = findViewById(R.id.sex);
        area.setOnClickListener(this);
        sex.setOnClickListener(this);
        reg.setOnClickListener(this);
        intent = new Intent();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.REGISTER);

    }

    public void register() {
        String strId = id.getText().toString();
        String strPwd = pwd.getText().toString();
        String strRePwd = rePwd.getText().toString();
        String strName = name.getText().toString();
        String strTel = tel.getText().toString();

        if (!StringUtils.isId(strId)) {
            ToastUtil.showToast(this, "账号只能包含字母或数字，最少6位", 0);
            return;
        }
        if (!StringUtils.isPwd(strPwd)) {
            ToastUtil.showToast(this, "密码只能包含字母、数字、下划线，最少6位", 0);
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

        if (areaId == 0) {
            ToastUtil.showToast(this, "请选择所在地区", 0);
            return;
        }

        if (sexid == -1) {
            ToastUtil.showToast(this, "请选择性别", 0);
            return;
        }
        String s = "WOMEN";
        if (sexid == 1) {
            s = "MEN";
        }
        Api.getInstance().register(strId, strPwd, strName, areaId, s, strTel);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sex:
                intent.setClass(this, ActivitySex.class);
                this.startActivityForResult(intent, Event.RESULT_SEX);
                break;
            case R.id.area:
                intent.setClass(this, ActivityArea.class);
                this.startActivityForResult(intent, Event.RESULT_AREA);
                break;
            case R.id.register:
                register();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Event.RESULT_SEX:
                sexid = data.getIntExtra("sex", -1);
                if (sexid == 1) {
                    text_sex.setText("男");
                } else if (sexid == 0) {
                    text_sex.setText("女");
                }
                break;
            case Event.RESULT_AREA:
                areaId = data.getIntExtra("areaid", 0);
                if (areaId != 0) {
                    text_area.setText(data.getStringExtra("areastr"));
                }
                break;
        }

    }
}
