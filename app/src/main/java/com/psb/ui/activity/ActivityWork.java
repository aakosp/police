package com.psb.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.psb.R;
import com.psb.adapter.AlbumPhotosAdapter;
import com.psb.adapter.PhotosAdapter;
import com.psb.entity.ID;
import com.psb.entity.Work;
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
 * Created by zl on 2015/2/6.
 */
public class ActivityWork extends BaseActivity implements View.OnClickListener {

    private TopNavigationBar topbar;
    private EditText title, info, type;
    private Button commit;
    private GridView imgs;
    private AlbumPhotosAdapter adapter;
    private List<String> urls = new ArrayList<>();
    private List<Uri> uris;
    private boolean shibai = false;
    private String strType = "";
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        topbar = (TopNavigationBar) findViewById(R.id.topbar);
        topbar.setActivity(this);
        title = (EditText) findViewById(R.id.title);
        info = (EditText) findViewById(R.id.info);
        type = (EditText) findViewById(R.id.type);
        type.setOnClickListener(this);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);

        imgs = (GridView) findViewById(R.id.imgs);
        adapter = new AlbumPhotosAdapter(this);
//        imgs.setAdapter(adapter);

        Intent intent = getIntent();
        if (null != intent) {
            id = intent.getIntExtra("id", -1);
        }
        if (id != -1) {
            topbar.setTitleText("查看工作记录");
            commit.setVisibility(View.GONE);
            Work work = Cache.getInstance().getWork(id);
            title.setText(work.getTitle());
            title.setEnabled(false);
            switch (work.getType()) {
                case Work.DISPUTE:
                    type.setText("矛盾纠纷");
                    break;
                case Work.EDUCATION:
                    type.setText("宣传教育");
                    break;
                case Work.OPINION:
                    type.setText("社情民意");
                    break;
                case Work.SECURITY:
                    type.setText("治安防范");
                    break;
                case Work.SERVING:
                    type.setText("服务群众");
                    break;
            }
            info.setText(work.getContent());
            info.setEnabled(false);
            imgs.setAdapter(new PhotosAdapter(this));
            adapter.notifyDataSetChanged();
        } else {
            imgs.setAdapter(adapter);
        }

        EventNotifyCenter.getInstance().register(this.getHandler(), Event.COMMIT_WORK);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                this.commit();
                break;
            case R.id.type:
                Intent intent = new Intent();
                intent.setClass(this, PopWorkType.class);
                this.startActivityForResult(intent, Event.RESULT_WORK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            adapter.addImage(uri);
        } else if (requestCode == Event.RESULT_WORK) {
            strType = data.getStringExtra("id");
            type.setText(data.getStringExtra("type"));
        }
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
        if (StringUtils.isEmpty(strType)) {
            ToastUtil.showToast(this, "请填选择类型", 0);
            return;
        }
        uris = adapter.getImgs();
        if (uris.size() == 0) {
            Api.getInstance().commitWork(strTitle, strType, strInfo, null);
            return;
        }
        for (int i = 0; i < uris.size(); i++) {
            ImageUploadTask task = new ImageUploadTask(i);
            task.setOnUploadListener(new ImageUploadTask.OnImageUploadListener() {
                @Override
                public void onUploadComplete(int id, String path) {
                    urls.add(path);
                    if (urls.size() == uris.size()) {
                        String pic = JSON.toJSONString(urls);
                        Api.getInstance().commitWork(strTitle, strType, strInfo, pic);
                    }
                }

                @Override
                public void onUploadFailed(int id) {
                    ToastUtil.showLongToast(ActivityWork.this, "上传图片失败", 0);
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
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.COMMIT_WORK:
                ID res = Cache.getInstance().getWorkid();
                if (!StringUtils.isEmpty(res.getError())) {
                    ToastUtil.showToast(this, res.getError(), 0);
                } else if (res.getId() > -1) {
                    Intent intent = new Intent();
                    intent.setClass(this, ActivityWorkSuccess.class);
                    this.startActivity(intent);
                    this.finish();
                }
                break;
        }
    }
}
