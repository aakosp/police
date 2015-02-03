package com.psb.protocol;

/**
 * Created by zl on 2015/2/3.
 */
public class HttpRequestData implements Runnable {

    private String url;
    private int event;

    public HttpRequestData(String url, int event){
        this.url = url;
        this.event = event;
    }

    @Override
    public void run() {
        HttpClient.get(url, event);
    }
}
