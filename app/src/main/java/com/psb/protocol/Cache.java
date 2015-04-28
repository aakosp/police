package com.psb.protocol;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.psb.core.AppContext;
import com.psb.entity.Addr;
import com.psb.entity.Article;
import com.psb.entity.ID;
import com.psb.entity.OfficeInfo;
import com.psb.entity.Opinion;
import com.psb.entity.Opinions;
import com.psb.entity.PoliceInfo;
import com.psb.entity.User;
import com.psb.entity.Vote;
import com.psb.entity.Work;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;
import com.psb.ui.activity.ActivityMain;
import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zl on 2015/2/4.
 */
public class Cache {

    private static Cache cache;

    private boolean isLogin = false;
    private String id, pwd, role, name;
    private User user;
    private Map<Integer, Article> articleMap = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private List<OfficeInfo> office;
    private List<Addr> addr;
    private Opinions opinions_ok;
    private Opinions opinions_undo;
    private Map<Integer, Opinion> opinions = new HashMap<>();
    //    private Map<Integer, Opinion> opinionsMap = new HashMap<>();
    private ID register, opi, chuli, workid, sign, setVote;
    private List<PoliceInfo> policeInfo = new ArrayList<>();
    private List<Work> works = new ArrayList<>();
    private List<Vote> votes;
    private String voted;
    private String token;


    private Cache() {
        Context ctx = AppContext.getInstance();
        SharedPreferences sp = ctx.getSharedPreferences("police", Context.MODE_PRIVATE);
        isLogin = sp.getBoolean("login", false);
        id = sp.getString("id", "");
        pwd = sp.getString("pwd", "");
        role = sp.getString("role", "");
        name = sp.getString("name", "");
    }

    public synchronized static Cache getInstance() {
        if (null == cache) {
            cache = new Cache();
        }
        return cache;
    }

    public void parse(String responseBody, int event) {
//        Log.d("EVENT: " + event, " " + responseBody);
        switch (event) {
            case Event.NEWS_1:
            case Event.NEWS_2:
            case Event.NEWS_3:
            case Event.NEWS_4:
            case Event.NEWS_5:
            case Event.NEWS_6:
            case Event.NEWS_7:
            case Event.NEWS_8:
            case Event.NEWS_9:
            case Event.NOTICE:
                Article article = JSON.parseObject(responseBody, Article.class);
                articleMap.put(event, article);
                break;

            case Event.GET_USER:
                User item = JSON.parseObject(responseBody, User.class);
                if (isLogin && item.getUser_name().equals(id)) {
                    if (null == user) {
                        user = item;
                    }
                    setLogin(item.getPassword().equals(pwd));
                }
                if (null != item) {
                    users.put(item.getUser_name(), item);
                }
                break;

            case Event.SGIN:
                sign = JSON.parseObject(responseBody, ID.class);
                break;

            case Event.GET_ADDRS:
                addr = JSON.parseArray(responseBody, Addr.class);
                break;

            case Event.REGISTER:
                register = JSON.parseObject(responseBody, ID.class);
                break;

            case Event.GET_POLICE_LIST:
                policeInfo = JSON.parseArray(responseBody, PoliceInfo.class);
                break;

            case Event.GET_OFFICE_LIST:
                office = JSON.parseArray(responseBody, OfficeInfo.class);
                break;

            case Event.CHANGE_PWD:
                break;

            case Event.GET_OPINION_LIST_OK:
                opinions_ok = JSON.parseObject(responseBody, Opinions.class);
                for (Opinion o : opinions_ok.getData()) {
                    opinions.put(o.getId(), o);
                }
                break;

            case Event.GET_OPINION_LIST_UNDO:
                opinions_undo = JSON.parseObject(responseBody, Opinions.class);
                for (Opinion o : opinions_undo.getData()) {
                    opinions.put(o.getId(), o);
                }
                break;

            case Event.COMMIT_OPINION:
                opi = JSON.parseObject(responseBody, ID.class);
                break;

            case Event.GET_WORK:
                works = JSON.parseArray(responseBody, Work.class);
                break;

            case Event.CHULI:
                chuli = JSON.parseObject(responseBody, ID.class);
                break;

            case Event.GET_OPINION:
//                opinion = JSON.parseObject(responseBody, Opinion.class);
                break;

            case Event.COMMIT_WORK:
                workid = JSON.parseObject(responseBody, ID.class);
                break;

            case Event.GET_VOTE:
                votes = JSON.parseArray(responseBody, Vote.class);
                break;

            case Event.SET_VOTE:
                setVote = JSON.parseObject(responseBody, ID.class);
                break;

            case Event.CHECK_VOTE:
                voted = (String) JSON.parseObject(responseBody).get("voted");
                break;
        }

        EventNotifyCenter.getInstance().doNotify(event);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
        Context ctx = AppContext.getInstance();
        SharedPreferences sp = ctx.getSharedPreferences("police", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("login", isLogin);
        editor.commit();

        if (isLogin && User.POLICE.equals(user.getRole())) {
            // 注册接口
//            XGPushManager.registerPush(this);
            XGPushManager.registerPush(AppContext.getInstance(), "" + user.getId(),
                    new XGIOperateCallback() {
                        @Override
                        public void onSuccess(Object data, int flag) {
//                            Log.w("onSuccess",
//                                    "+++ register push sucess. token:" + data);
                        }

                        @Override
                        public void onFail(Object data, int errCode, String msg) {
//                            Log.w("onFail",
//                                    "+++ register push fail. token:" + data
//                                            + ", errCode:" + errCode + ",msg:"
//                                            + msg);
                        }
                    });
        }

    }

    public synchronized Article getArticle(int event) {
        return articleMap.get(event);
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public synchronized List<Addr> getAddr() {
        return addr;
    }

    public ID getReg() {
        return register;
    }

    public User getInfo() {
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        Context ctx = AppContext.getInstance();
        SharedPreferences sp = ctx.getSharedPreferences("police", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        role = user.getRole();
        id = user.getUser_name();
        pwd = user.getPassword();
        editor.putString("id", user.getUser_name());
        editor.putString("pwd", user.getPassword());
        editor.putString("role", user.getRole());
        if (user.getRole().equals(User.POLICE)) {
            name = user.getPolice_name();
            editor.putString("name", user.getPolice_name());
        } else {
            name = user.getName();
            editor.putString("name", user.getName());
        }

        editor.commit();
    }


    public synchronized List<OfficeInfo> getOffice() {
        return this.office;
    }

    public synchronized List<PoliceInfo> getPoliceInfo() {
        return policeInfo;
    }

    public synchronized Opinions getOpinions_ok() {
        return opinions_ok;
    }

    public synchronized Opinions getOpinions_undo() {
        return opinions_undo;
    }

    public synchronized ID getOpi() {
        return opi;
    }

    public synchronized ID getChuli() {
        return chuli;
    }

    public synchronized Opinion getOpinion(int id) {
        return opinions.get(id);
    }

    public String getAddrStr(int id) {
        String str = "";
        if (null != addr) {
            for (Addr item : addr) {
                if (id == item.getId()) {
                    str = item.getName();
                    return str;
                }
                List<Addr> cun = item.getChild();
                if (null != cun) {
                    for (Addr itemCun : cun) {
                        if (id == itemCun.getId()) {
                            str = itemCun.getName();
                            return str;
                        }
                    }
                }
            }
        }
        return str;
    }

    public synchronized List<Work> getWorks() {
        return works;
    }

    public Work getWork(int id) {
        Work work = null;
        for (Work w : works) {
            if (w.getId() == id) {
                return w;
            }
        }
        return work;
    }

    public synchronized ID getWorkid() {
        return workid;
    }

    public synchronized ID getSign() {
        return sign;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public List<Vote> getVote() {
        return votes;
    }

    public String getVoted() {
        return voted;
    }

    public ID getVoteR() {
        return setVote;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
