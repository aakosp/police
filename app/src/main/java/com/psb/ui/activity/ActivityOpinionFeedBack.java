package com.psb.ui.activity;

import android.os.Bundle;

import com.psb.R;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by zl on 2015/2/3.
 */
public class ActivityOpinionFeedBack extends BaseActivity {

    private TopNavigationBar topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion_feedback);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
    }
}