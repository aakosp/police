package com.psb.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psb.R;
import com.psb.entity.User;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseFragment;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentProfile extends BaseFragment {

    private View mView;
    private static Fragment user, police, login;
    private static FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_profile, container, false);
        login = this.getActivity().getSupportFragmentManager().findFragmentById(R.id.login);
        user = this.getActivity().getSupportFragmentManager().findFragmentById(R.id.user);
        police = this.getActivity().getSupportFragmentManager().findFragmentById(R.id.police);
        fm = this.getFragmentManager();
        this.choose();
        return mView;
    }

    public static void choose() {
        if (!Cache.getInstance().isLogin()) {
            fm.beginTransaction().hide(user).hide(police).show(login).commit();
        } else {
            if (User.POLICE.equals(Cache.getInstance().getRole())) {
                fm.beginTransaction().hide(login).hide(user).show(police).commit();
                BaseFragment bf = (BaseFragment) police;
                bf.init();
            } else {
                fm.beginTransaction().hide(login).hide(police).show(user).commit();
                BaseFragment bf = (BaseFragment) user;
                bf.init();
            }
        }

    }

}
