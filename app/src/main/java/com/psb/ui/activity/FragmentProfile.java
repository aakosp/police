package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.psb.R;
import com.psb.ui.base.BaseFragment;
import com.psb.ui.widget.ItemHorizontal;
import com.psb.ui.widget.RoundImageView;

/**
 * Created by zl on 2015/1/26.
 */
public class FragmentProfile extends BaseFragment implements View.OnClickListener {

    private boolean isPolice = true;
    private View mView, profile;
    private RoundImageView avatar;
    private TextView name, id;
    //police
    private ItemHorizontal notice, processing, record, history, sign;
    //Villager
    private ItemHorizontal minyi, feedback, opinion;

    private ItemHorizontal pwd;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            return mView;
        }
        if(isPolice){
            this.initPolice(container);
        }
        else{
            this.initVillager(container);
        }
        profile = mView.findViewById(R.id.profile);
        avatar = (RoundImageView) mView.findViewById(R.id.avatar);
        name = (TextView) mView.findViewById(R.id.name);
        id = (TextView) mView.findViewById(R.id.id);
        pwd = (ItemHorizontal) mView.findViewById(R.id.pwd);
        profile.setOnClickListener(this);
        pwd.setOnClickListener(this);
        intent = new Intent();

        avatar.setImageResource(R.drawable.default_img);
        name.setText("张三");
        id.setText("aakosp@gmail.com");
        return mView;
    }

    private void initPolice(ViewGroup container){
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_profile_police, container, false);
        notice = (ItemHorizontal) mView.findViewById(R.id.notice);
        processing = (ItemHorizontal) mView.findViewById(R.id.processing);
        record = (ItemHorizontal) mView.findViewById(R.id.record);
        history = (ItemHorizontal) mView.findViewById(R.id.record_history);
        sign = (ItemHorizontal) mView.findViewById(R.id.sign);

        notice.setOnClickListener(this);
        processing.setOnClickListener(this);
        record.setOnClickListener(this);
        history.setOnClickListener(this);
        sign.setOnClickListener(this);
    }

    private void initVillager(ViewGroup container){
        mView = this.getActivity().getLayoutInflater().inflate(R.layout.activity_profile, container, false);
        minyi = (ItemHorizontal) mView.findViewById(R.id.minyi);
        feedback = (ItemHorizontal) mView.findViewById(R.id.feedback);
        opinion = (ItemHorizontal) mView.findViewById(R.id.opinion);

        minyi.setOnClickListener(this);
        feedback.setOnClickListener(this);
        opinion.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                break;
            case R.id.notice:
                break;
            case R.id.processing:
                intent.setClass(this.getActivity(), ActivityOpinionProcessing.class);
                break;
            case R.id.sign:
                intent.setClass(this.getActivity(), ActivitySign.class);
                break;
            case R.id.record:
                intent.setClass(this.getActivity(), ActivityWork.class);
                break;
            case R.id.record_history:
                break;
            case R.id.pwd:
                break;
            case R.id.minyi:
                break;
            case R.id.feedback:
                intent.setClass(this.getActivity(), ActivityOpinionFeedBack.class);
                break;
            case R.id.opinion:
                break;
            default:
                return;
        }
        this.startActivity(intent);
    }
}
