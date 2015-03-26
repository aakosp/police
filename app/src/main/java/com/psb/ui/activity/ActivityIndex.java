package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.psb.R;
import com.psb.ui.base.BaseActivity;

/**
 * Created by zl on 2015/3/26.
 */
public class ActivityIndex extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        this.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(ActivityIndex.this, ActivityMain.class);
                ActivityIndex.this.startActivity(intent);
                ActivityIndex.this.finish();
            }
        }, 1500);
    }
}
