package com.psb.protocol;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.psb.entity.Addr;
import com.psb.entity.Article;
import com.psb.entity.ID;
import com.psb.entity.OfficeInfo;
import com.psb.entity.PoliceInfo;
import com.psb.entity.User;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zl on 2015/2/4.
 */
public class Cache {

    private static Cache cache;

    private String id;
    private User user;
    private Map<Integer, Article> articleMap = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private List<OfficeInfo> office;
    private List<Addr> addr;
    private ID register;
    private PoliceInfo policeInfo;

    private Cache() {
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
                Article article = JSON.parseObject(responseBody, Article.class);
                articleMap.put(event, article);
                break;
            case Event.GET_USER:
                User user = JSON.parseObject(responseBody, User.class);
                if (null != user) {
                    users.put(user.getUser_name(), user);
                }
                break;

            case Event.SGIN:
                ID sign = JSON.parseObject(responseBody, ID.class);
                break;

            case Event.GET_ADDRS:
                addr = JSON.parseArray(responseBody, Addr.class);
                break;

            case Event.REGISTER:
                register = JSON.parseObject(responseBody, ID.class);
                break;

            case Event.GET_PLOICE_LIST:
                policeInfo = JSON.parseObject(responseBody, PoliceInfo.class);
                break;

            case Event.GET_OFFICE_LIST:
                office = JSON.parseArray(responseBody, OfficeInfo.class);
                break;

            case Event.CHANGE_PWD:
                break;
        }

        EventNotifyCenter.getInstance().doNotify(event);
    }

    public Article getArticle(int event) {
        return articleMap.get(event);
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public List<Addr> getAddr() {
        return addr;
    }

//    public void disAddr(List<Addr> addrs) {
//        for (Addr addr : addrs) {
////            Log.d("addr", addr.getId() + " " + addr.getName() + " ");
//            if (null != addr.getChild()) {
//                disAddr(addr.getChild());
//            }
//        }
//    }

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
    }

    public List<OfficeInfo> getOffice() {
        return this.office;
    }
}
