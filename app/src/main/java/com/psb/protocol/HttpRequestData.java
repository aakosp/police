package com.psb.protocol;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by zl on 2015/2/3.
 */
public class HttpRequestData implements Runnable {

    private String url;
    private int event;
//    private List<NameValuePair> params;

    public HttpRequestData(String url, int event){
        this.url = url;
        this.event = event;
    }

    @Override
    public void run() {
        HttpClient.get(url, event);
    }
}
