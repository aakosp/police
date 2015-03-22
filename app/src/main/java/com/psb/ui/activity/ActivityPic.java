package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ImageUtil;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by aako on 2015/3/22.
 */
public class ActivityPic extends BaseActivity {

    private TopNavigationBar top;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        top = (TopNavigationBar) findViewById(R.id.top);
        top.setActivity(this);
        img = (ImageView) findViewById(R.id.pic);
        Intent intent = getIntent();
        if (null != intent) {
            Bundle b = intent.getExtras();
            if (null != b) {
                ImageLoader.getInstance().displayImage(b.getString("url", ""), img, ImageUtil.options);
            }
        }
    }
}
