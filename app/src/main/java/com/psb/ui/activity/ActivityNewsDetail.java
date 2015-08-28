package com.psb.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ImageUtil;
import com.psb.ui.widget.TopNavigationBar;
import com.util.StringUtils;

/**
 * Created by zl on 2015/1/30.
 */
public class ActivityNewsDetail extends BaseActivity {

    private TopNavigationBar topbar;
    private TextView title, detail;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        title = (TextView) findViewById(R.id.title);
        detail = (TextView) findViewById(R.id.detail);
        img = (ImageView) findViewById(R.id.img);
        topbar.setActivity(this);

        Bundle bundle = this.getIntent().getExtras();
        if (null != bundle) {
            boolean notice = bundle.getBoolean("notice", false);
            if (notice) {
                topbar.setTitleText(this.getResources().getText(R.string.notice));
            }
            title.setText(bundle.getString("title"));
//            detail.setText(bundle.getString("content"));
            detail.setText(Html.fromHtml(bundle.getString("content")));
            if (!StringUtils.isEmpty(bundle.getString("url"))) {
                img.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(bundle.getString("url") + ImageUtil.CONTENT, img, ImageUtil.options);
            }
        }
    }
}
