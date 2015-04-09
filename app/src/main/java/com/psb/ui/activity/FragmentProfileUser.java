package com.psb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.psb.R;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.ItemHorizontal;
import com.psb.ui.widget.RoundImageView;

/**
 * Created by zl on 2015/3/13.
 */
public class FragmentProfileUser extends BaseFragment implements View.OnClickListener {

    private View mView, profile;
    private ItemHorizontal minyi, feedback, opinion, pwd, logout;
    //    private RoundImageView avatar;
    private TextView name, id;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_profile_user, container, false);
        profile = mView.findViewById(R.id.profile);
//        avatar = (RoundImageView) mView.findViewById(R.id.avatar);
        name = (TextView) mView.findViewById(R.id.name);
        minyi = (ItemHorizontal) mView.findViewById(R.id.minyi);
        feedback = (ItemHorizontal) mView.findViewById(R.id.feedback);
        opinion = (ItemHorizontal) mView.findViewById(R.id.opinion);
        id = (TextView) mView.findViewById(R.id.id);
        pwd = (ItemHorizontal) mView.findViewById(R.id.pwd);
        logout = (ItemHorizontal) mView.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        minyi.setOnClickListener(this);
        feedback.setOnClickListener(this);
        opinion.setOnClickListener(this);
        profile.setOnClickListener(this);
        pwd.setOnClickListener(this);
        intent = new Intent();

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
            case R.id.minyi:
                intent.setClass(this.getActivity(), ActivityMinyi.class);
                break;
            case R.id.feedback:
                intent.setClass(this.getActivity(), ActivityOpinions.class);
                break;
            case R.id.opinion:
                intent.setClass(this.getActivity(), ActivityOpinionProcessing.class);
                break;
            case R.id.profile:
//                intent.setClass(this.getActivity(), ActivityOpinionInfo.class);
                return;
            case R.id.pwd:
                intent.setClass(this.getActivity(), ActivityChangePwd.class);
                break;
            default:
                return;
        }
        this.getActivity().startActivity(intent);
    }

}
