package com.psb.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psb.R;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.WidgetViewPager;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentNavigation extends BaseFragment{

    private View mView;
    private WidgetViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_profile, container, false);
        viewPager = (WidgetViewPager) mView.findViewById(R.id.vp);
        return mView;
    }
}
