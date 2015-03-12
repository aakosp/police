package com.psb.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.psb.R;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ImageUtil;
import com.psb.ui.util.ToastUtil;
import com.psb.ui.widget.TopNavigationBar;
import com.upyun.api.ImageUploadTask;
import com.upyun.api.Uploader;
import com.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zl on 2015/2/6.
 */
public class ActivityWork extends BaseActivity implements View.OnClickListener {

    private TopNavigationBar topbar;
    private EditText title, info;
    private Button commit;
    private ImageView img1, img2, img3, img4, img5, img6;
    private Map<Integer, Uri> uris;
    private Map<Integer, String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        title = (EditText) findViewById(R.id.title);
        info = (EditText) findViewById(R.id.info);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        img6 = (ImageView) findViewById(R.id.img6);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);

        EventNotifyCenter.getInstance().register(this.getHandler(), Event.COMMIT_WORK);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                this.commit();
                break;
            case R.id.img1:
            case R.id.img2:
            case R.id.img3:
            case R.id.img4:
            case R.id.img5:
            case R.id.img6:
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, v.getId());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            Bitmap bitmap = ImageUtil.getBitmapFromUri(uri);
            ImageView img = (ImageView) findViewById(requestCode);
            img.setImageBitmap(bitmap);
            if (null == uris) {
                uris = new HashMap<>();
            }
            uris.put(resultCode, uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void commit() {
        final String strTitle = title.getText().toString();
        if (StringUtils.isEmpty(strTitle)) {
            ToastUtil.showToast(this, "请填写标题", 0);
            return;
        }
        final String strInfo = info.getText().toString();
        if (StringUtils.isEmpty(strInfo)) {
            ToastUtil.showToast(this, "请填写内容", 0);
            return;
        }
        for (int key : uris.keySet()) {
            final Uri uri = uris.get(key);
            ImageUploadTask task = new ImageUploadTask(key);
            task.setOnUploadListener(new ImageUploadTask.OnImageUploadListener() {
                @Override
                public void onUploadComplete(int id, String path) {
                    if (null == urls) {
                        urls = new HashMap<Integer, String>();
                    }
                    urls.put(id, path);
                    if (urls.size() == uris.size()) {
                        String pic = JSON.toJSONString(urls.values());
                        Log.d("pic", pic);
                        Api.getInstance().commitWork(strTitle, strInfo, pic);
                    }
                }

                @Override
                public void onUploadFailed(int id) {
                    if (null == urls) {
                        urls = new HashMap<Integer, String>();
                    }
                    urls.remove(id);
                }
            });
            task.execute(uri.toString());
        }
        Api.getInstance().commitWork(strTitle, strInfo, null);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.COMMIT_WORK:
                ToastUtil.showToast(this, "提交成功", 0);
                break;
        }
    }
}
