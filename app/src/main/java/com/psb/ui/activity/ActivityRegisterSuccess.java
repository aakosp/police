package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.psb.R;
import com.psb.ui.base.BaseActivity;

/**
 * Created by aako on 2015/2/8.
 */
public class ActivityRegisterSuccess extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        Button b = (Button) findViewById(R.id.login);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ActivityRegisterSuccess.this, ActivityLogin.class);
                ActivityRegisterSuccess.this.startActivity(intent);
                ActivityRegisterSuccess.this.finish();
            }
        });
    }
}
