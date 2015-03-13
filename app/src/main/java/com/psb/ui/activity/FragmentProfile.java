package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.entity.User;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.util.TipsLogin;
import com.psb.ui.widget.ItemHorizontal;
import com.psb.ui.widget.RoundImageView;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentProfile extends BaseFragment {

    private View mView;
    private Fragment login, user, police;

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
        this.choose();
        return mView;
    }

    public void choose() {
        if (!Cache.getInstance().isLogin()) {
            this.getActivity().getSupportFragmentManager().beginTransaction().hide(user).hide(police).show(login).commit();
        } else {
            if (User.POLICE.equals(Cache.getInstance().getUser().getRole()))
                this.getActivity().getSupportFragmentManager().beginTransaction().hide(login).hide(user).show(police).commit();
            else
                this.getActivity().getSupportFragmentManager().beginTransaction().hide(login).hide(police).show(user).commit();
        }
    }

}
