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
import android.widget.EditText;

import com.psb.R;
import com.psb.entity.User;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.util.ToastUtil;
import com.util.Md5Helper;
import com.util.StringUtils;

/**
 * Created by zl on 2015/3/13.
 */
public class FragmentProfileLogin extends BaseFragment implements View.OnClickListener {

    private View mView;
    private EditText id, pwd;
    private Button login, register, report;
    private Intent intent;
    private String strId, strPwd;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_login, container, false);
        id = (EditText) mView.findViewById(R.id.userid);
        pwd = (EditText) mView.findViewById(R.id.userpwd);
        login = (Button) mView.findViewById(R.id.login);
        register = (Button) mView.findViewById(R.id.register);
        report = (Button) mView.findViewById(R.id.report);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        report.setOnClickListener(this);
        intent = new Intent();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_USER);
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                strId = id.getText().toString();
                if (StringUtils.isEmpty(strId)) {
                    ToastUtil.showToast(this.getActivity(), "用户名为空", 0);
                    return;
                }
                strPwd = pwd.getText().toString();
                if (StringUtils.isEmpty(strId)) {
                    ToastUtil.showToast(this.getActivity(), "密码为空", 0);
                    return;
                }
                Api.getInstance().getUser(strId);
                return;
            case R.id.register:
                intent.setClass(this.getActivity(), ActivityRegister.class);
                break;
            case R.id.report:
                intent.setClass(this.getActivity(), ActivityOpinions.class);
                intent.putExtra("login", true);
                break;
            default:
                return;
        }
        this.getActivity().startActivity(intent);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_USER:
                if(!Cache.getInstance().isLogin()) {
                    if(StringUtils.isEmpty(strId)){
                        strId = Cache.getInstance().getId();
                    }
                    User user = Cache.getInstance().getUser(strId);
                    Log.d("user", strId);
                    if (null == user) {
                        Log.d("user", "null");
                        ToastUtil.showToast(this.getActivity(), "用户名或密码错误", 0);
                        return;
                    }
                    String md5 = Md5Helper.encode(strPwd);
                    if (md5.equals(user.getPassword())) {
                        Cache.getInstance().setId(strId);
                        Cache.getInstance().setUser(user);
                        Cache.getInstance().setLogin(true);
                        FragmentProfile.choose();
                    } else {
                        ToastUtil.showToast(this.getActivity(), "用户名或密码错误", 0);
                    }
                }
                break;
        }
    }
}
