package com.psb.protocol;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.psb.entity.Addr;
import com.psb.entity.Article;
import com.psb.entity.ID;
import com.psb.entity.OfficeInfo;
import com.psb.entity.Opinion;
import com.psb.entity.Opinions;
import com.psb.entity.PoliceInfo;
import com.psb.entity.User;
import com.psb.entity.Work;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;

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
    private String id;
    private User user;
    private Map<Integer, Article> articleMap = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private List<OfficeInfo> office;
    private List<Addr> addr;
    private Opinions opinions_ok;
    private Opinions opinions_undo;
    private Map<Integer, Opinion> opinions = new HashMap<>();
//    private Map<Integer, Opinion> opinionsMap = new HashMap<>();
    private ID register, opi, chuli, workid;
    private List<PoliceInfo> policeInfo = new ArrayList<>();
    private List<Work> works = new ArrayList<>();

    private Cache() {
    }

    public synchronized static Cache getInstance() {
        if (null == cache) {
            cache = new Cache();
        }
        return cache;
    }

    public void parse(String responseBody, int event) {
        Log.d("EVENT: " + event, " " + responseBody);
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
                User item = JSON.parseObject(responseBody, User.class);

                if (null != item) {
                    users.put(item.getUser_name(), item);
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
                for(Opinion o :opinions_ok.getData()){
                    opinions.put(o.getId(), o);
                }
                break;

            case Event.GET_OPINION_LIST_UNDO:
                opinions_undo = JSON.parseObject(responseBody, Opinions.class);
                for(Opinion o :opinions_undo.getData()){
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
        }

        EventNotifyCenter.getInstance().doNotify(event);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
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
        Log.d(user.getPolice_name(), user.getUser_name());
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

    public synchronized ID getOpi(){
        return opi;
    }

    public synchronized ID getChuli(){
        return chuli;
    }

    public synchronized Opinion getOpinion(int id){
        return opinions.get(id);
    }

    public String getAddrStr(int id){
        String str = "";
        if(null != addr){
            for(Addr item : addr){
                if(id == item.getId()){
                    str = item.getName();
                    return str;
                }
                List<Addr> cun = item.getChild();
                if(null != cun){
                    for (Addr itemCun : cun){
                        if(id == itemCun.getId()){
                            str = itemCun.getName();
                            return str;
                        }
                    }
                }
            }
        }
        return str;
    }

    public synchronized List<Work> getWorks(){
        return works;
    }

    public Work getWork(int id){
        Work work = null;
        for (Work w : works){
            if(w.getId() == id){
                return w;
            }
        }
        return work;
    }

    public synchronized ID getWorkid(){
        return workid;
    }
}
