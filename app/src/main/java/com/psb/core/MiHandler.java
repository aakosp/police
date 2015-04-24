package com.psb.core;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.psb.entity.Article;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Cache;

/**
 * Created by zl on 2015/4/24.
 */
public class MiHandler extends Handler {

    private Context context;

    public MiHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        String s = (String) msg.obj;
        Cache.getInstance().parse(s, Event.NOTICE);

//            if (MainActivity.sMainActivity != null) {
//                MainActivity.sMainActivity.refreshLogInfo();
//
//                if (msg.what==1) {
//                    MainActivity.sMainActivity.refreshPushState((msg.arg1==1)?true:false);
//                }
//            }

        if (!TextUtils.isEmpty(s)) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }
}
