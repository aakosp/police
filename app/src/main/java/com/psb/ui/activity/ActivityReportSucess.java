package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.Opinion;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by zl on 2015/3/15.
 */
public class ActivityReportSucess extends BaseActivity {
    public Button fanhui;
    public TopNavigationBar top;
    public TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sucess);
        top = (TopNavigationBar) findViewById(R.id.top);
        fanhui = (Button) findViewById(R.id.fanhui);
        top.setActivity(this);
        txt = (TextView) findViewById(R.id.text);
        Intent intent = getIntent();
        if (null != intent) {
            if (!Opinion.ANONYMOUS.equals(intent.getStringExtra("niming"))) {
                txt.setText("已成功实名提交了您的意见");
            }
        }

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
