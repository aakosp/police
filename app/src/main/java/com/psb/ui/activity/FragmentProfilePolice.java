package com.psb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.psb.R;
import com.psb.core.AppContext;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.ItemHorizontal;
import com.psb.ui.widget.RoundImageView;
import com.psb.xg.NotificationService;
import com.psb.xg.NotificationUtil;
import com.psb.xg.XGNotification;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zl on 2015/3/13.
 */
public class FragmentProfilePolice extends BaseFragment implements View.OnClickListener {

    private View mView, profile;
    private ItemHorizontal notice, processing, record, history, sign, pwd, logout;
    //    private ImageView avatar;
    private TextView name, id;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_profile_police, container, false);
        notice = (ItemHorizontal) mView.findViewById(R.id.notice);
        processing = (ItemHorizontal) mView.findViewById(R.id.processing);
        record = (ItemHorizontal) mView.findViewById(R.id.record);
        history = (ItemHorizontal) mView.findViewById(R.id.record_history);
        sign = (ItemHorizontal) mView.findViewById(R.id.sign);
        profile = mView.findViewById(R.id.profile);
//        avatar = (ImageView) mView.findViewById(R.id.avatar);
        name = (TextView) mView.findViewById(R.id.name);
        id = (TextView) mView.findViewById(R.id.id);
        pwd = (ItemHorizontal) mView.findViewById(R.id.pwd);
        logout = (ItemHorizontal) mView.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        notice.setOnClickListener(this);
        processing.setOnClickListener(this);
        record.setOnClickListener(this);
        history.setOnClickListener(this);
        sign.setOnClickListener(this);
        profile.setOnClickListener(this);
        pwd.setOnClickListener(this);
        intent = new Intent();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.NOTICE_SIGN_BEGIN, Event.NOTICE_SIGN_END);
        return mView;
    }

    @Override
    public void init() {
        name.setText(Cache.getInstance().getName());
        id.setText(Cache.getInstance().getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                Cache.getInstance().setLogin(false);
                FragmentProfile.choose();
                return;
            case R.id.profile:
                return;
            case R.id.notice:
                intent.setClass(this.getActivity(), ActivityNotice.class);
                break;
            case R.id.processing:
                intent.setClass(this.getActivity(), ActivityOpinionProcessing.class);
                break;
            case R.id.record:
                intent.setClass(this.getActivity(), ActivityWork.class);
                break;
            case R.id.record_history:
                intent.setClass(this.getActivity(), ActivityWorkList.class);
                break;
            case R.id.sign:
                intent.setClass(this.getActivity(), ActivitySign.class);
                break;
            case R.id.pwd:
                intent.setClass(this.getActivity(), ActivityChangePwd.class);
                break;
            default:
                return;
        }
        this.getActivity().startActivity(intent);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.NOTICE_SIGN_BEGIN:
                sign.setText("签到(正在签到)");
                break;
            case Event.NOTICE_SIGN_END:
                sign.setText("签到");
                break;
        }
    }
}
