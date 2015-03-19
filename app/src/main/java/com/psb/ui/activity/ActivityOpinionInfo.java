package com.psb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.psb.R;
import com.psb.entity.ID;
import com.psb.entity.Opinion;
import com.psb.entity.User;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.protocol.Api;
import com.psb.protocol.Cache;
import com.psb.ui.base.BaseActivity;
import com.psb.ui.util.ToastUtil;
import com.psb.ui.widget.TopNavigationBar;
import com.util.StringUtils;

/**
 * Created by aako on 2015/3/15.
 */
public class ActivityOpinionInfo extends BaseActivity implements View.OnClickListener {

    private int id;
    private TopNavigationBar top;
    private TextView title, jieguo, time, type, info;
    private ImageView img;
    private TextView df_name, df_time, df_info;
    private View df, chuli;
    private TextView chuli_name, chuli_info;
    private Button commit;
    private boolean isPolice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion_info);
        top = (TopNavigationBar) findViewById(R.id.top);
        top.setActivity(this);
        df = findViewById(R.id.df);
        chuli = findViewById(R.id.chuli);

        img = (ImageView) findViewById(R.id.img);
        title = (TextView) findViewById(R.id.title);
        jieguo = (TextView) findViewById(R.id.jieguo);
        time = (TextView) findViewById(R.id.time);
        type = (TextView) findViewById(R.id.type);
        info = (TextView) findViewById(R.id.info);
        df_name = (TextView) findViewById(R.id.df_name);
        df_time = (TextView) findViewById(R.id.df_time);
        df_info = (TextView) findViewById(R.id.df_info);
        chuli_name = (TextView) findViewById(R.id.chuli_name);
        chuli_info = (TextView) findViewById(R.id.chuli_info);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);
        isPolice = User.POLICE.equals(Cache.getInstance().getUser().getRole());

//        Api.getInstance().getOpinion(id);
        EventNotifyCenter.getInstance().register(this.getHandler(), Event.CHULI);

        id = getIntent().getIntExtra("id", -1);
        if (id == -1) {
            ToastUtil.showToast(this, "获取失败", 0);
        } else {
            Opinion opinion = Cache.getInstance().getOpinion(id);

//            opinion = new Opinion();
//            opinion.setTitle("231231");
//            opinion.setTime("3123123123");
//            opinion.setContent("asdlsajdjsladjsajdlsaldlsaas加大三季度拉丝机达拉斯阿里斯顿接撒考虑到家啦手机登陆");
//            opinion.setReply(Opinion.NOT_REPLY);
//            opinion.setId(1);
//            opinion.setType(Opinion.ANONYMOUS);
//            User u = new User();
//            u.setPhone("1383838383");
//            u.setName("123");
//            u.setId(1);
//            u.setAddress(287);
//            u.setUser_name("阿斯顿");
//            opinion.setUser(u);
//            Opinion.Reply r = opinion.new Reply();
//            r.setId(1);
//            r.setReply_police_name("323");
//            r.setReply_time("213213123");
//            r.setReply_content("sadlksaldjasljd撒娇的撒娇的旅客撒娇了的就撒了快捷电路卡死机了的卡死机了肯德基里开始");
//
//            opinion.setReply_result(r);
            this.init(opinion);
        }

//        EventNotifyCenter.getInstance().register(this.getHandler(), Event.CHULI);
    }

    public void init(Opinion opinion) {
        if (null != opinion) {
            title.setText(opinion.getTitle());
            if (!Opinion.NOT_REPLY.equals(opinion.getReply())) {
                if (isPolice)
                    jieguo.setText("（已处理）");
                else
                    jieguo.setText("（已答复）");

                df_name.setText("答复人：" + opinion.getReply_result().getReply_police_name() + "，" + opinion.getReply_result().getReply_police_phone());
                df_time.setText(opinion.getReply_result().getReply_time());
                df_info.setText(opinion.getReply_result().getReply_content());
            } else {
                if (isPolice) {
                    jieguo.setText("（未处理）");
                    chuli.setVisibility(View.VISIBLE);
                    String chuliname = "答复人：" + Cache.getInstance().getUser().getPolice_name() + "，" + Cache.getInstance().getUser().getPhone();
                    chuli_name.setText(chuliname);
                } else {
                    jieguo.setText("（未答复）");
                }
                df.setVisibility(View.GONE);
            }
            time.setText(opinion.getTime());
            if (Opinion.ANONYMOUS.equals(opinion.getType())) {
                type.setText("匿名");
            } else {
                String name = opinion.getUser().getName();
                name += "，" + Cache.getInstance().getAddrStr(opinion.getUser().getAddress());
                name += "，" + opinion.getUser().getPhone();
                type.setText(name);
            }
            info.setText(opinion.getContent());

        }
    }

    @Override
    public void onClick(View v) {
        String strChuli = chuli_info.getText().toString();
        Api.getInstance().chuliOpinion(id, strChuli);
    }

    @Override
    protected void handlerPacketMsg(Message msg) {
        switch (msg.what) {
            case Event.CHULI:
                ID res = Cache.getInstance().getChuli();
                if (!StringUtils.isEmpty(res.getError())) {
                    ToastUtil.showLongToast(this, res.getError(), 0);
                } else if (res.getId() > -1) {
                    EventNotifyCenter.getInstance().doNotify(Event.REFRESH_OPINION);
                    Intent intent = new Intent();
                    intent.setClass(this, ActivityChuliSuccess.class);
                    this.startActivity(intent);
                    this.finish();
                }
                break;
//            case Event.GET_OPINION:
//                if(id == Cache.getInstance().getOpinion().getId()){
//                    this.init(Cache.getInstance().getOpinion());
//                }
//                break;
        }
    }


}
