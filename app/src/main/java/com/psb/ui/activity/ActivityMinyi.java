package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.psb.R;
import com.psb.entity.ID;
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
public class ActivityMinyi extends BaseActivity implements View.OnClickListener{
//    private TextView minyi1, minyi2, minyi3;
//    private RadioButton minyi1_1, minyi1_2, minyi1_3, minyi2_1, minyi2_2, minyi2_3, minyi3_1, minyi3_2, minyi3_3;
    private TopNavigationBar top;
    private LinearLayout vote;
    private Button commit;
    private List<ItemVote> listVote = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minyi);
        top = (TopNavigationBar) findViewById(R.id.top);
        vote = (LinearLayout) findViewById(R.id.vote);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);
        Api.getInstance().checkVote();
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.GET_VOTE, Event.SET_VOTE, Event.CHECK_VOTE);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what){
            case Event.GET_VOTE:
                init(Cache.getInstance().getVote());
                break;
            case Event.SET_VOTE:
                ID res = Cache.getInstance().getVoteR();
                if (!StringUtils.isEmpty(res.getError())) {
                    ToastUtil.showToast(this, res.getError(), 0);
                }
                else {
                    Intent intent = new Intent();
                    intent.setClass(this, ActivityChuliSuccess.class);
                    intent.putExtra("minyi", true);
                    this.startActivity(intent);
                    this.finish();
                }
                break;
            case Event.CHECK_VOTE:
                if(null!=Cache.getInstance().getVoted() && Vote.NO.equals(Cache.getInstance().getVoted())){
                    Api.getInstance().getVote();
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(this, ActivityChuliSuccess.class);
                    intent.putExtra("minyi_s", true);
                    this.startActivity(intent);
                    this.finish();
                }
                break;
        }
    }

    private void init(List<Vote> votes){
        if(null == votes){
            return;
        }
        listVote.clear();
        for(Vote v : votes){
            ItemVote item = new ItemVote(this);
            item.setVote(v);
            vote.addView(item);
            listVote.add(item);
        }
        commit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commit:
                commitVote();
                break;
        }
    }

    private void commitVote(){
        List<String> v = new ArrayList<>();
        for(ItemVote item : listVote){
            v.add(item.getChecked());
        }
        Api.getInstance().vote(v);
    }
}
