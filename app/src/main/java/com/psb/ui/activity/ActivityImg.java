package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ImageUtil;

/**
 * Created by aako on 2015/7/28.
 */
public class ActivityImg extends BaseActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        img = (ImageView) findViewById(R.id.img);
        if(null != img){
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityImg.this.finish();
                }
            });
            Intent intent = getIntent();
            if(null != intent){
                String uri = intent.getStringExtra("uri");
                ImageLoader.getInstance().displayImage(uri, img, ImageUtil.options);
            }
        }
    }


}
