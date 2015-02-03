package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psb.R;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.ItemHorizontal;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentNavigation extends BaseFragment implements View.OnClickListener{

    private View mView;
    private ItemHorizontal info ,map ,officelist;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_navigation, container, false);
        info = (ItemHorizontal) mView.findViewById(R.id.info);
        map = (ItemHorizontal) mView.findViewById(R.id.office_map);
        officelist = (ItemHorizontal) mView.findViewById(R.id.office_info);
        info.setOnClickListener(this);
        map.setOnClickListener(this);
        officelist.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        if(null == intent){
            intent = new Intent();
        }
        switch (v.getId()){
            case R.id.info:
//                intent.setClass(this.getActivity(), );
                break;
            case R.id.office_map:
                intent.setClass(this.getActivity(), ActivityMap.class);
                break;
            case R.id.office_info:
                intent.setClass(this.getActivity(), ActivityOffice.class);
                break;
            default:
                return;
        }
        this.startActivity(intent);
    }
}
