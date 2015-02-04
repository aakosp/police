package com.psb.protocol;

import com.psb.ThreadUtil.ThreadPoolExecutorFactory;
import com.psb.core.AppContext;

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

    private String url = "http://"+ HttpClient.SERVER+":"+HttpClient.PORT;
    private List<NameValuePair> params = new ArrayList<>();
    private Map<Integer, Long> requestTime = new HashMap<>();
    private Executor executor = ThreadPoolExecutorFactory.createExecutor();
    private static Api api;

    private Api(){}

    public static synchronized Api getInstance(){
        if(null == api){
            api = new Api();
        }
        return api;
    }

    public void getArticle(int type) {
        if(null != requestTime.get(type)){
            if(System.currentTimeMillis() - requestTime.get(type) < AppContext.request_time_lag){
                return;
            }
        }
        requestTime.put(type, System.currentTimeMillis());
        url += "/article/?article_cat_id="+type;
        HttpRequestData data = new HttpRequestData(url, type);
        executor.execute(data);
    }

}