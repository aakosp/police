package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.Opinion;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by aako on 2015/3/15.
 */
public class ActivityChuliSuccess extends BaseActivity {

    public Button fanhui;
    public TopNavigationBar top;
    public TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuli_sucess);
        top = (TopNavigationBar) findViewById(R.id.top);
        fanhui = (Button) findViewById(R.id.fanhui);
        txt = (TextView) findViewById(R.id.text);
        Intent intent = getIntent();
        if (null != intent) {
            Log.d("sign", intent.getBooleanExtra("sign", false) + "");
            if (intent.getBooleanExtra("sign", false)) {
                top.setTitleText("签到");
                txt.setText("签到成功");
                fanhui.setText("返回个人中心");
            }
        }
        top.setActivity(this);

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
