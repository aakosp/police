package com.psb.protocol;

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
    private String base_url = "http://" + HttpClient.SERVER;
    private List<NameValuePair> params = new ArrayList<>();
    private Map<Integer, Long> requestTime = new HashMap<>();
    private Executor executor = ThreadPoolExecutorFactory.createExecutor();

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
            url = base_url + "//article?article_cat_id=" + type + "?page=" + page;
        } else {
            url = base_url + "//article?article_cat_id=" + type;
        }
        HttpRequestData data = new HttpRequestData(url, type);
        executor.execute(data);
        return true;
    }

    public void getUser(String id) {
        String url = base_url + "/user?user_name=" + id;
        HttpRequestData data = new HttpRequestData(url, Event.GET_USER);
        executor.execute(data);
    }

    public void sgin(String content, double location_x, double location_y) {
        String url = base_url + "/sign_up";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair user = new BasicNameValuePair("user_id", "" + Cache.getInstance().getUser().getId());
        NameValuePair c = new BasicNameValuePair("sign_up_content", content);
        NameValuePair x = new BasicNameValuePair("sign_up_location_x", "" + location_x);
        NameValuePair y = new BasicNameValuePair("sign_up_location_y", "" + location_y);
        params.add(user);
        params.add(c);
        params.add(x);
        params.add(y);
        HttpRequestData data = new HttpRequestData(params, url, Event.SGIN);
        executor.execute(data);
    }

    public void commitOpinion(String strTitle, String info, String pic, String strType){
        String url = base_url + "/opinion";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair id = new BasicNameValuePair("user_id", ""+Cache.getInstance().getUser().getId());
        params.add(id);
        NameValuePair type = new BasicNameValuePair("title", strType);
        params.add(type);
        NameValuePair title = new BasicNameValuePair("title", strTitle);
        params.add(title);
        NameValuePair content = new BasicNameValuePair("content", info);
        params.add(content);
        if(!StringUtils.isEmpty(pic)){
            NameValuePair picture = new BasicNameValuePair("picture", pic);
            params.add(picture);
        }
        HttpRequestData data = new HttpRequestData(params, url, Event.COMMIT_WORK);
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
        HttpRequestData data = new HttpRequestData(url, Event.GET_OFFICE_LIST);
        executor.execute(data);
    }

    public void getPolice(int addr) {
        String url = base_url + "/address/" + addr;
        HttpRequestData data = new HttpRequestData(url, Event.GET_POLICE_LIST);
        executor.execute(data);
    }

    public void changePwd(String pwd) {
        String url = base_url + "/user/" + Cache.getInstance().getUser().getId();
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair nameValuePair1 = new BasicNameValuePair("password", pwd);
        params.add(nameValuePair1);
        HttpRequestData data = new HttpRequestData(params, url, Event.CHANGE_PWD, HttpClient.RequestType.PUT);
        executor.execute(data);
    }

    public void commitWork(String strTitle, String info, String pic){
        String url = base_url + "/daily_log";
        List<NameValuePair> params = new ArrayList<>();
        NameValuePair id = new BasicNameValuePair("user_id", ""+Cache.getInstance().getUser().getId());
        params.add(id);
        NameValuePair title = new BasicNameValuePair("title", strTitle);
        params.add(title);
        NameValuePair content = new BasicNameValuePair("content", info);
        params.add(content);
        if(!StringUtils.isEmpty(pic)){
            NameValuePair picture = new BasicNameValuePair("picture", pic);
            params.add(picture);
        }
        HttpRequestData data = new HttpRequestData(params, url, Event.COMMIT_WORK);
        executor.execute(data);
    }

    public void register(String id, String pwd, String name, int addr, String sex, String phone) {
        String url = base_url + "/user";
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
        HttpRequestData data = new HttpRequestData(params, url, Event.REGISTER);
        executor.execute(data);
    }
}