package com.psb.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.psb.R;
import com.psb.adapter.AlbumPhotosAdapter;
import com.psb.entity.ID;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ToastUtil;
import com.psb.ui.widget.TopNavigationBar;
import com.upyun.api.ImageUploadTask;
import com.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/3/15.
 */
public class ActivityOpinions extends BaseActivity implements View.OnClickListener {

    private TopNavigationBar top;
    private EditText title, info;
    private GridView imgs;
    private AlbumPhotosAdapter adapter;
    private View def, root;
    private Button shiming, niming, login_niming;
    private String type = "ANONYMOUS";
    private List<String> urls = new ArrayList<>();
    private List<Uri> uris;
    private boolean shibai = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        root = findViewById(R.id.root);
        top = (TopNavigationBar) findViewById(R.id.topbar);
        top.setActivity(this);
        title = (EditText) findViewById(R.id.title);
        info = (EditText) findViewById(R.id.info);
        imgs = (GridView) findViewById(R.id.imgs);
        shiming = (Button) findViewById(R.id.shiming);
        niming = (Button) findViewById(R.id.niming);
        login_niming = (Button) findViewById(R.id.login_niming);
        shiming.setOnClickListener(this);
        niming.setOnClickListener(this);
        login_niming.setOnClickListener(this);
        def = findViewById(R.id.def);
        boolean login = this.getIntent().getBooleanExtra("login", false);
        if (login) {
            def.setVisibility(View.GONE);
            login_niming.setVisibility(View.VISIBLE);
        }
        adapter = new AlbumPhotosAdapter(this, root);
        imgs.setAdapter(adapter);

        EventNotifyCenter.getInstance().register(this.getHandler(), Event.COMMIT_OPINION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            adapter.addImage(uri);
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = null;
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            if (data.getData() != null) {
                uri = data.getData();
            } else {
                uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            }
            if (null != uri) {
                adapter.addImage(uri);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shiming:
                type = "ONYMOUS";
            case R.id.niming:
            case R.id.login_niming:
                this.commit();
                break;
        }
    }

    public void commit() {
        shibai = false;
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
        uris = adapter.getImgs();
        if (uris.size() == 0) {
            Api.getInstance().commitOpinion(strTitle, strInfo, null, type);
            return;
        }
        for (int i = 0; i < uris.size(); i++) {
            ImageUploadTask task = new ImageUploadTask(i);
            task.setOnUploadListener(new ImageUploadTask.OnImageUploadListener() {
                @Override
                public void onUploadComplete(int id, String path) {
                    urls.add(path);
                    if (urls.size() == uris.size()) {
                        Api.getInstance().commitOpinion(strTitle, strInfo, urls, type);
                    }
                }

                @Override
                public void onUploadFailed(int id) {
                    ToastUtil.showLongToast(ActivityOpinions.this, "上传图片失败", 0);
                    shibai = true;
                }
            });
            if (shibai) {
                return;
            }
            task.execute(uris.get(i).toString());
        }
        if (shibai) {
            return;
        }
//        Api.getInstance().commitOpinion(strTitle, strInfo, null, type);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.COMMIT_OPINION:
                ID res = Cache.getInstance().getOpi();
                if (!StringUtils.isEmpty(res.getError())) {
                    ToastUtil.showToast(this, res.getError(), 0);
                } else if (res.getId() > -1) {
                    Intent intent = new Intent();
                    intent.setClass(this, ActivityReportSucess.class);
                    intent.putExtra("niming", type);
                    this.startActivity(intent);
                    this.finish();
                }
                break;
        }
    }
}
