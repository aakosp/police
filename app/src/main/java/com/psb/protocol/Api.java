package com.psb.protocol;

import com.psb.ThreadUtil.ThreadPoolExecutorFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by zl on 2015/2/3.
 */
public class Api {

    private String url = "http://"+ HttpClient.SERVER+":"+HttpClient.PORT;
    private List<NameValuePair> params = new ArrayList<>();
    private int event;

    private Executor executor = ThreadPoolExecutorFactory.createExecutor();
    private static Api api;

    private Api(){

    }

    public static synchronized Api getInstance(){
        if(null == api){
            api = new Api();
        }
        return api;
    }

    public void getArticle(int type) {
        url += "/article/?article_cat_id="+type;
        HttpRequestData data = new HttpRequestData(url, type);
        executor.execute(data);
    }

}