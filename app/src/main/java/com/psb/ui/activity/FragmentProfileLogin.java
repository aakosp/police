package com.psb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.psb.R;
import com.psb.ui.base.BaseFragment;

/**
 * Created by zl on 2015/3/13.
 */
public class FragmentProfileLogin extends BaseFragment implements View.OnClickListener {

    private View mView;
    private EditText id, pwd;
    private Button login, register, report;
    private Intent intent;

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
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                return;
            case R.id.register:
                intent.setClass(this.getActivity(), ActivityRegister.class);
                break;
            case R.id.report:
                intent.setClass(this.getActivity(), ActivityOpinionFeedBack.class);
                break;
            default:
                return;
        }
        this.getActivity().startActivity(intent);
    }
}
