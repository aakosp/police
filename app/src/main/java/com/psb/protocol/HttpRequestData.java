package com.psb.protocol;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by zl on 2015/2/3.
 */
public class HttpRequestData implements Runnable {

    private HttpClient.RequestType type = HttpClient.RequestType.GET;
    private String url;
    private int event;
    private List<NameValuePair> params;

    public HttpRequestData(List<NameValuePair> params, String url, int event) {
        this.type = HttpClient.RequestType.POST;
        this.url = url;
        this.event = event;
        this.params = params;
    }

    public HttpRequestData(String url, int event) {
        this.url = url;
        this.event = event;
    }

    @Override
    public void run() {
        if (HttpClient.RequestType.GET == type) {
            HttpClient.get(url, event);
        } else {
            HttpClient.post(url, params, event);
        }
    }
}
