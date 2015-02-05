package com.psb.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.psb.R;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by zl on 2015/1/29.
 */
public class ActivityRegister extends BaseActivity {

    private TopNavigationBar topbar;
    private EditText id, pwd, rePwd, name, tel;
    private Spinner sex, addr;

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
        sex = (Spinner) findViewById(R.id.sex);
        addr = (Spinner) findViewById(R.id.addr);
    }

    public void register() {
        String strId = id.getText().toString();
        String strPwd = pwd.getText().toString();
        String strRePwd = rePwd.getText().toString();
        String strName = name.getText().toString();
        String strTel = tel.getText().toString();

        String strSex = sex.getTransitionName();
        String strAddr = addr.getTransitionName();
    }
}
