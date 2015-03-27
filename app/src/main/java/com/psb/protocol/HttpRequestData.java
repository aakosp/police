package com.psb.protocol;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by zl on 2015/2/3.
 */
public class HttpRequestData implements Runnable {

    private HttpClient.RequestType type = HttpClient.RequestType.GET;
    private String route;
    private String url;
    private int event;
    private List<NameValuePair> params;

    public HttpRequestData(List<NameValuePair> params, String route, String url, int event) {
        this.route = route;
        this.type = HttpClient.RequestType.POST;
        this.url = url;
        this.event = event;
        this.params = params;
    }

    public HttpRequestData(List<NameValuePair> params, String route, String url, int event, HttpClient.RequestType type) {
        this.route = route;
        this.type = type;
        this.url = url;
        this.event = event;
        this.params = params;
    }

    public HttpRequestData(String route, String url, int event) {
        this.route = route;
        this.url = url;
        this.event = event;
    }

    @Override
    public void run() {
//        switch (type) {
//            case GET:
//                HttpClient.get(url, event);
//                break;
//            case POST:
//                HttpClient.post(url, params, event);
//                break;
//            case PUT:
//                HttpClient.put(url, params, event);
//                break;
//        }
        HttpClient.doRequest(route, url, params, event, type);
    }
}
