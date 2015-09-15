package com.psb.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.psb.R;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.widget.TopNavigationBar;

/**
 * Created by aako on 2015/9/14.
 */
public class ActivityWebView extends BaseActivity {

    private TopNavigationBar topbar;
    private WebView mWebView;
    private String strWews;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        mWebView = (WebView) findViewById(R.id.web);

        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_NEWS);

        Bundle bundle = this.getIntent().getExtras();
        if (null != bundle) {
            boolean notice = bundle.getBoolean("notice", false);
            if (notice) {
                topbar.setTitleText(this.getResources().getText(R.string.notice));
            }
//            title.setText(bundle.getString("title"));
//            detail.setText(bundle.getString("content"));
//            detail.setText(Html.fromHtml(bundle.getString("content")));
//            if (!StringUtils.isEmpty(bundle.getString("url"))) {
//                img.setVisibility(View.VISIBLE);
//                ImageLoader.getInstance().displayImage(bundle.getString("url") + ImageUtil.CONTENT, img, ImageUtil.options);
//            }
//            strWews = bundle.getString("content");
            id = bundle.getInt("id", 0);
            if (id > 0) {
                if (Cache.getInstance().existNews(id)) {
                    strWews = Cache.getInstance().getNewsInfo(id).getContent();
                } else {
                    Api.getInstance().getNewsById(id);
                }
            }
        }
        WebSettings wSet = mWebView.getSettings();
        wSet.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl("file:///android_asset/index.html");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);// 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebView.loadUrl("javascript:updateHtml('" + strWews + "')");
        }
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_NEWS:
                strWews = Cache.getInstance().getNewsInfo(id).getContent();
                mWebView.loadUrl("javascript:updateHtml('" + strWews + "')");
                break;
        }
    }
}
