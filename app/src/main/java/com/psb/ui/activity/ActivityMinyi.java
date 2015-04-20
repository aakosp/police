package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.ID;
import com.psb.entity.PoliceInfo;
import com.psb.entity.Vote;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ToastUtil;
import com.psb.ui.widget.ItemVote;
import com.psb.ui.widget.TopNavigationBar;
import com.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2015/4/9.
 */
public class ActivityMinyi extends BaseActivity implements View.OnClickListener {
    //    private TextView minyi1, minyi2, minyi3;
//    private RadioButton minyi1_1, minyi1_2, minyi1_3, minyi2_1, minyi2_2, minyi2_3, minyi3_1, minyi3_2, minyi3_3;
    private TopNavigationBar top;
    private LinearLayout vote;
    private Button commit;
    private List<ItemVote> listVote = new ArrayList<>();
    private TextView police;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minyi);
        top = (TopNavigationBar) findViewById(R.id.top);
        top.setActivity(this);
        vote = (LinearLayout) findViewById(R.id.vote);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);
        police = (TextView) findViewById(R.id.police);
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_POLICE_LIST, Event.GET_VOTE, Event.SET_VOTE, Event.CHECK_VOTE);
        Log.d("addr", Cache.getInstance().getUser().getAddress()+"");
        Api.getInstance().getPolice(Cache.getInstance().getUser().getAddress());
        Api.getInstance().checkVote();

    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.GET_POLICE_LIST:
                if (Cache.getInstance().getPoliceInfo().size() == 0) {
                    return;
                }
                PoliceInfo info = Cache.getInstance().getPoliceInfo().get(0);
                if (null == info || null == info.getPolice()) {
                    Intent intent = new Intent();
                    intent.setClass(this, ActivityChuliSuccess.class);
                    intent.putExtra("minyi_un", true);
                    this.startActivity(intent);
                    this.finish();
                } else {
                    police.setText("负责"+info.getName()+"的民警是：" + info.getPolice().getPolice_name() + "，电话为：" + info.getPolice().getPhone());
                    id = info.getPolice().getId();
                }
                break;
            case Event.GET_VOTE:
                init(Cache.getInstance().getVote());
                break;
            case Event.SET_VOTE:
                ID res = Cache.getInstance().getVoteR();
                if (!StringUtils.isEmpty(res.getError())) {
                    ToastUtil.showToast(this, res.getError(), 0);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(this, ActivityChuliSuccess.class);
                    intent.putExtra("minyi", true);
                    this.startActivity(intent);
                    this.finish();
                }
                break;
            case Event.CHECK_VOTE:
                if (null != Cache.getInstance().getVoted() && Vote.NO.equals(Cache.getInstance().getVoted())) {
                    Api.getInstance().getVote();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(this, ActivityChuliSuccess.class);
                    intent.putExtra("minyi_s", true);
                    this.startActivity(intent);
                    this.finish();
                }
                break;
        }
    }

    private void init(List<Vote> votes) {
        if (null == votes) {
            return;
        }
        listVote.clear();
        for (Vote v : votes) {
            ItemVote item = new ItemVote(this);
            item.setVote(v);
            vote.addView(item);
            listVote.add(item);
        }
        commit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                commitVote();
                break;
        }
    }

    private void commitVote() {
        if(id == 0){
            return;
        }
        List<String> v = new ArrayList<>();
        for (ItemVote item : listVote) {
            v.add(item.getChecked());
        }
        Api.getInstance().vote(id, v);
    }
}
