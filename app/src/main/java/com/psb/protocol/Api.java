package com.psb.protocol;

import android.util.Log;

import com.psb.ThreadUtil.ThreadPoolExecutorFactory;
import com.psb.core.AppContext;
import com.psb.event.Event;
import com.util.StringUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by zl on 2015/2/3.
 */
public class Api {

    private static Api api;
    private Map<Integer, Long> requestTime = new HashMap<>();
    private Executor executor = ThreadPoolExecutorFactory.createExecutor();
    private static final String ARTICLE = "/article";
    private static final String USER = "/user";
    private static final String SIGN = "/sign_up";
    private static final String OPINION = "/opinion";
    private static final String OPINION_REPLY = "/opinion_reply";
    private static final String ADDRESS = "/address";
    private static final String POLICE_STATION = "/police_station";
    private static final String DAILY_LOG = "/daily_log";
    private static final String VOTE = "/vote";

    private Api() {
    }

    public static synchronized Api getInstance() {
        if (null == api) {
            api = new Api();
        }
        return api;
    }

    public boolean getArticle(int type, int page) {
        if (null != requestTime.get(type)) {
            if (System.currentTimeMillis() - requestTime.get(type) < AppContext.request_time_lag) {
                return false;
            }
        }
        requestTime.put(type, System.currentTimeMillis());
        String url = "";
        if (page > 1) {
            url = "/article?article_cat_id=" + type + "&page=" + page;
        } else {
            url = "/article?article_cat_id=" + type;
        }
        Log.d("getArticle", url);
        HttpRequestData data = new HttpRequestData(ARTICLE, url, type);
        executor.execute(data);
        return true;
    }

    public void getUser(String id) {
        String url = "/user?user_name=" + id;
        HttpRequestData data = new HttpRequestData(USER, url, Event.GET_USER);
        executor.execute(data);
    }

    public void sign_up(String content, double location_x, double location_y) {
        String url = "/sign_up";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair user = new BasicNameValuePair("user_id", "" + Cache.getInstance().getUser().getId());
        NameValuePair c = new BasicNameValuePair("sign_up_content", content);
        NameValuePair x = new BasicNameValuePair("sign_up_location_x", "" + location_x);
        NameValuePair y = new BasicNameValuePair("sign_up_location_y", "" + location_y);
        params.add(user);
        params.add(c);
        params.add(x);
        params.add(y);
        HttpRequestData data = new HttpRequestData(params, SIGN, url, Event.SGIN);
        executor.execute(data);
    }

    public void sign_up_session(String session, double location_x, double location_y) {
        String url = "/sign_up/" + session;
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair s = new BasicNameValuePair("session_id", session);
        NameValuePair x = new BasicNameValuePair("location_x", "" + location_x);
        NameValuePair y = new BasicNameValuePair("location_y", "" + location_y);
        params.add(s);
        params.add(x);
        params.add(y);
        HttpRequestData data = new HttpRequestData(params, url, url, Event.SGIN, HttpClient.RequestType.PUT);
        executor.execute(data);
    }

    public void sign_up_end(String session) {
        String url = "/sign_up/" + session;
        HttpRequestData data = new HttpRequestData(null, url, url, Event.SGIN, HttpClient.RequestType.DELETE);
        executor.execute(data);
    }

    public void commitOpinion(String strTitle, String info, List<String> pic, String strType, String opinion_type) {
        String url = "/opinion";
        List<NameValuePair> params = new ArrayList<>();
        if (null != Cache.getInstance().getUser()) {
            NameValuePair id = new BasicNameValuePair("user_id", "" + Cache.getInstance().getUser().getId());
            params.add(id);
        }
        NameValuePair type = new BasicNameValuePair("type", strType);
        params.add(type);
        NameValuePair opinionType = new BasicNameValuePair("opinion_type", opinion_type);
        params.add(opinionType);
        NameValuePair title = new BasicNameValuePair("title", strTitle);
        params.add(title);
        NameValuePair content = new BasicNameValuePair("content", info);
        params.add(content);
        if (null != pic) {
            for (int i = 0; i < pic.size(); i++) {
                NameValuePair picture = new BasicNameValuePair("picture[" + i + "]", pic.get(i));
                params.add(picture);
            }
        }
        HttpRequestData data = new HttpRequestData(params, OPINION, url, Event.COMMIT_OPINION);
        executor.execute(data);
    }

    public void chuliOpinion(int id, String info) {
        String url = "/opinion_reply";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair pid = new BasicNameValuePair("opinion_id", "" + id);
        params.add(pid);
        NameValuePair pinfo = new BasicNameValuePair("reply_content", info);
        params.add(pinfo);
        NameValuePair uid = new BasicNameValuePair("user_id", "" + Cache.getInstance().getUser().getId());
        params.add(uid);
        HttpRequestData data = new HttpRequestData(params, OPINION_REPLY, url, Event.CHULI);
        executor.execute(data);
    }

    public void getFeedBack(String id) {
        String url = "/opinion_reply/" + id;
        HttpRequestData data = new HttpRequestData(url, url, Event.GET_FEEDBACK);
        executor.execute(data);
    }

    public void opinionFeedBack(String id, String userId, String content) {
        String url = "/opinion_reply";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair nameValuePair1 = new BasicNameValuePair("opinion_id", id);
        NameValuePair nameValuePair2 = new BasicNameValuePair("reply_content", "" + content);
        NameValuePair nameValuePair3 = new BasicNameValuePair("user_id", "" + userId);
        params.add(nameValuePair1);
        params.add(nameValuePair2);
        params.add(nameValuePair3);
        HttpRequestData data = new HttpRequestData(params, OPINION_REPLY, url, Event.OPINION_FEEDBACK);
        executor.execute(data);
    }

    public void getOpinion(int id) {
        String url = "/opinion/" + id;
        HttpRequestData data = new HttpRequestData(url, url, Event.GET_OPINION);
        executor.execute(data);
    }

    public void getOpinionsOK(int id, int page) {
        String url = "/opinion?reply=REPLYED";
        if (id != -1) {
            url += "&user_id=" + id;
        }

        if (page != -1) {
            url += "&page=" + page;
        }
        HttpRequestData data = new HttpRequestData(OPINION, url, Event.GET_OPINION_LIST_OK);
        executor.execute(data);
    }

    public void getOpinionsUndo(int id, int page) {
        String url = "/opinion?reply=NOT_REPLY";
        if (id != -1) {
            url += "&user_id=" + id;
        }
        if (page != -1) {
            url += "&page=" + page;
        }
        HttpRequestData data = new HttpRequestData(OPINION, url, Event.GET_OPINION_LIST_UNDO);
        executor.execute(data);
    }

    public void getAddrs() {
        String url = "/address";
        HttpRequestData data = new HttpRequestData(ADDRESS, url, Event.GET_ADDRS);
        executor.execute(data);
    }

    public void getAddrByParent(String id) {
        String url = "/address&parentid=" + id;
        HttpRequestData data = new HttpRequestData(url, url, Event.GET_ADDRS);
        executor.execute(data);
    }

    public void getOffice() {
        String url = "/police_station";
        HttpRequestData data = new HttpRequestData(POLICE_STATION, url, Event.GET_OFFICE_LIST);
        executor.execute(data);
    }

    public void getPolice(int addr) {
        String url = "/address/" + addr;
        HttpRequestData data = new HttpRequestData(url, url, Event.GET_POLICE_LIST);
        executor.execute(data);
    }

    public void changePwd(String pwd) {
        String url = "/user/" + Cache.getInstance().getUser().getId();
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair nameValuePair1 = new BasicNameValuePair("password", pwd);
        params.add(nameValuePair1);
        HttpRequestData data = new HttpRequestData(params, url, url, Event.CHANGE_PWD, HttpClient.RequestType.PUT);
        executor.execute(data);
    }

    public void commitWork(String strTitle, String strType, String info, List<String> pic) {
        String url = "/daily_log";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair id = new BasicNameValuePair("police_id", "" + Cache.getInstance().getUser().getId());
        params.add(id);
        NameValuePair title = new BasicNameValuePair("title", strTitle);
        params.add(title);
        NameValuePair type = new BasicNameValuePair("type", strType);
        params.add(type);
        NameValuePair content = new BasicNameValuePair("content", info);
        params.add(content);
        if (null != pic) {
            for (int i = 0; i < pic.size(); i++) {
                NameValuePair picture = new BasicNameValuePair("picture[" + i + "]", pic.get(i));
                params.add(picture);
            }
        }
        HttpRequestData data = new HttpRequestData(params, DAILY_LOG, url, Event.COMMIT_WORK);
        executor.execute(data);
    }

    public void getWork() {
        String url = "/daily_log?police_id=" + Cache.getInstance().getUser().getId();
        HttpRequestData data = new HttpRequestData(DAILY_LOG, url, Event.GET_WORK);
        executor.execute(data);
    }

    public void register(String id, String pwd, String name, int addr, String sex, String phone) {
        String url = "/user";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair nameValuePair1 = new BasicNameValuePair("user_name", id);
        NameValuePair nameValuePair2 = new BasicNameValuePair("password", pwd);
        NameValuePair nameValuePair3 = new BasicNameValuePair("name", name);
        if (addr != 0) {
            NameValuePair nameValuePair4 = new BasicNameValuePair("address", "" + addr);
            params.add(nameValuePair4);
        }
        if (!StringUtils.isEmpty(sex)) {
            NameValuePair nameValuePair5 = new BasicNameValuePair("sex", sex);
            params.add(nameValuePair5);
        }
        if (!StringUtils.isEmpty(phone)) {
            NameValuePair nameValuePair6 = new BasicNameValuePair("phone", phone);
            params.add(nameValuePair6);
        }
        params.add(nameValuePair1);
        params.add(nameValuePair2);
        params.add(nameValuePair3);
        HttpRequestData data = new HttpRequestData(params, USER, url, Event.REGISTER);
        executor.execute(data);
    }

    public void getVote() {
        String url = "/vote";
        HttpRequestData data = new HttpRequestData(VOTE, url, Event.GET_VOTE);
        executor.execute(data);
    }

    public void checkVote() {
        String url = "/vote/" + Cache.getInstance().getUser().getId();
        HttpRequestData data = new HttpRequestData(url, url, Event.CHECK_VOTE);
        executor.execute(data);
    }

    public void vote(int policeId, List<String> vote) {
        String url = "/vote";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair id = new BasicNameValuePair("user_id", "" + Cache.getInstance().getUser().getId());
        params.add(id);
        NameValuePair pid = new BasicNameValuePair("police_id", "" + policeId);
        params.add(pid);
        int i = 1;
        for (String a : vote) {
            if (!StringUtils.isEmpty(a)) {
                NameValuePair p = new BasicNameValuePair("answer_" + i, a);
                params.add(p);
            }
            i++;
        }
        HttpRequestData data = new HttpRequestData(params, VOTE, url, Event.SET_VOTE);
        executor.execute(data);
    }
}