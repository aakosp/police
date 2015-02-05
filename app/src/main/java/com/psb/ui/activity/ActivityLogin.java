package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.psb.R;
import com.psb.ui.base.BaseActivity;

/**
 * Created by zl on 2015/1/30.
 */
public class ActivityLogin extends BaseActivity implements View.OnClickListener {

    private Button login, register, report;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent();

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        report = (Button) findViewById(R.id.report);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        report.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                intent.setClass(this, ActivityMain.class);
                break;
            case R.id.register:
                intent.setClass(this, ActivityRegister.class);
                break;
            case R.id.report:
                intent.setClass(this, ActivityOpinionFeedBack.class);
                break;
            default:
                return;
        }
        this.startActivity(intent);
    }
}
