package com.psb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.psb.R;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by aako on 2015/3/15.
 */
public class ActivityWorkSuccess extends BaseActivity {

    public Button fanhui;
    public TopNavigationBar top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuli_sucess);
        top = (TopNavigationBar) findViewById(R.id.top);
        fanhui = (Button) findViewById(R.id.fanhui);
        top.setActivity(this);

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
