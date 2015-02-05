package com.psb.protocol;

import com.psb.ThreadUtil.ThreadPoolExecutorFactory;
import com.psb.core.AppContext;
import com.psb.event.Event;

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

    private String base_url = "http://" + HttpClient.SERVER + ":" + HttpClient.PORT;
    private List<NameValuePair> params = new ArrayList<>();
    private Map<Integer, Long> requestTime = new HashMap<>();
    private Executor executor = ThreadPoolExecutorFactory.createExecutor();
    private static Api api;

    private Api() {
    }

    public static synchronized Api getInstance() {
        if (null == api) {
            api = new Api();
        }
        return api;
    }

    public void getArticle(int type) {
        if (null != requestTime.get(type)) {
            if (System.currentTimeMillis() - requestTime.get(type) < AppContext.request_time_lag) {
                return;
            }
        }
        requestTime.put(type, System.currentTimeMillis());
        String url = base_url + "/article/?article_cat_id=" + type;
        HttpRequestData data = new HttpRequestData(url, type);
        executor.execute(data);
    }

    public void getUser(String id) {
        String url = base_url + "/user/" + id;
        HttpRequestData data = new HttpRequestData(url, Event.GET_USER);
        executor.execute(data);
    }

    public void sgin(String id, float location_x, float location_y) {
        String url = base_url + "/sign_up";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair user = new BasicNameValuePair("user_id", id);
        NameValuePair x = new BasicNameValuePair("sign_up_location_x", "" + location_x);
        NameValuePair y = new BasicNameValuePair("sign_up_location_y", "" + location_y);
        params.add(user);
        params.add(x);
        params.add(y);
        HttpRequestData data = new HttpRequestData(params, url, Event.SGIN);
        executor.execute(data);
    }

    public void getFeedBack(String id) {
        String url = base_url + "/opinion_reply/" + id;
        HttpRequestData data = new HttpRequestData(url, Event.GET_FEEDBACK);
        executor.execute(data);
    }

    public void opinionFeedBack(String id, String userId, String content) {
        String url = base_url + "/opinion_reply";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair nameValuePair1 = new BasicNameValuePair("opinion_id", id);
        NameValuePair nameValuePair2 = new BasicNameValuePair("reply_content", "" + content);
        NameValuePair nameValuePair3 = new BasicNameValuePair("user_id", "" + userId);
        params.add(nameValuePair1);
        params.add(nameValuePair2);
        params.add(nameValuePair3);
        HttpRequestData data = new HttpRequestData(params, url, Event.OPINION_FEEDBACK);
        executor.execute(data);
    }

    public void getOpinion(String id) {
        String url = base_url + "/opinion/" + id;
        HttpRequestData data = new HttpRequestData(url, Event.GET_OPINION);
        executor.execute(data);
    }

    public void getOpinions() {
        String url = base_url + "/opinion";
        HttpRequestData data = new HttpRequestData(url, Event.GET_OPINION_LIST);
        executor.execute(data);
    }

    public void getAddrs() {
        String url = base_url + "/address";
        HttpRequestData data = new HttpRequestData(url, Event.GET_ADDRS);
        executor.execute(data);
    }

    public void getAddrByParent(String id) {
        String url = base_url + "/address&parentid=" + id;
        HttpRequestData data = new HttpRequestData(url, Event.GET_ADDRS);
        executor.execute(data);
    }

    public void getOffice() {
        String url = base_url + "/police_station";
        HttpRequestData data = new HttpRequestData(url, Event.GET_ADDRS);
        executor.execute(data);
    }
}