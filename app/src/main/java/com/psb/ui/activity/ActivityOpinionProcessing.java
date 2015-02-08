package com.psb.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.psb.R;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by zl on 2015/2/6.
 */
public class ActivityOpinionProcessing extends BaseActivity {

    private TopNavigationBar topbar;
    private Button unProcess, process;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_opinion);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        unProcess = (Button) findViewById(R.id.un_process);
        process = (Button) findViewById(R.id.processed);
        list = (ListView) findViewById(R.id.list);
    }
}
