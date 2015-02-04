package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psb.R;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.ItemHorizontal;
import com.psb.ui.widget.RoundImageView;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentProfile extends BaseFragment implements View.OnClickListener {

    private View mView, profile;
    private RoundImageView avatar;
    private TextView name, id;
    private ItemHorizontal notice, processing, record, history;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_profile, container, false);
        profile = mView.findViewById(R.id.profile);
        avatar = (RoundImageView) mView.findViewById(R.id.avatar);
        name = (TextView) mView.findViewById(R.id.name);
        id = (TextView) mView.findViewById(R.id.id);
        notice = (ItemHorizontal) mView.findViewById(R.id.notice);
        processing = (ItemHorizontal) mView.findViewById(R.id.processing);
        record = (ItemHorizontal) mView.findViewById(R.id.record);
        history = (ItemHorizontal) mView.findViewById(R.id.record_history);

        profile.setOnClickListener(this);
        notice.setOnClickListener(this);
        processing.setOnClickListener(this);
        record.setOnClickListener(this);
        history.setOnClickListener(this);

        avatar.setImageResource(R.drawable.yuanbao_img);
        name.setText("张三");
        id.setText("aakosp@gmail.com");

        intent = new Intent();
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                break;
            case R.id.notice:
                break;
            case R.id.id:
                break;
            case R.id.processing:
                intent.setClass(this.getActivity(), ActivityOpinionFeedBack.class);
                break;
            case R.id.record:
                break;
            case R.id.record_history:
                break;
            default:
                return;
        }
        this.startActivity(intent);
    }
}
