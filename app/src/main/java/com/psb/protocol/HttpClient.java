package com.psb.protocol;

import android.net.http.AndroidHttpClient;

import com.util.StringUtils;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zl on 2015/1/23.
 */
public class HttpClient {

    public static final String UTF_8 = "UTF-8";
    public final static String SERVER = "112.126.81.69";
    public final static int PORT = 80;
    private static final String LOG_TAG = "HttpClient";
    private final static int TIMEOUT_CONNECTION = 20000;
    private final static int TIMEOUT_SOCKET = 20000;
    private final static int TIMEOUT_REQUEST = 20000;
    private final static String authorization = "Basic API:03db45e2904663c5c9305a9c6ed62af3";
    private static String appCookie;
    private static String appUserAgent;
    private static HttpHost httpHost;
    private static HttpClientContext context;
    private static AndroidHttpClient client;

    private static String getUserAgent() {
        return appUserAgent;
    }

    private static AndroidHttpClient getHttpClient() {
        if (null == client) {
            client = AndroidHttpClient.newInstance(getUserAgent());
        }
        return client;
    }

    private static HttpHost getHttpHost() {
        if (null == httpHost) {
            httpHost = new HttpHost(SERVER, PORT);
        }
        return httpHost;
    }

    private static HttpContext getHttpContext() {
        if (null == context) {
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.BEST_MATCH)
                    .setExpectContinueEnabled(true)
                    .setStaleConnectionCheckEnabled(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                    .setSocketTimeout(TIMEOUT_SOCKET)
                    .setConnectTimeout(TIMEOUT_CONNECTION)
                    .setConnectionRequestTimeout(TIMEOUT_REQUEST)
                    .build();

            context = HttpClientContext.create();
            context.setRequestConfig(defaultRequestConfig);
        }
        return context;
    }

    private static HttpRequest getHttpRequest(String url, RequestType type) {

        HttpRequest httpRequest = null;
        switch (type) {

            case POST:
                httpRequest = new HttpPost(url);
                break;
            case GET:
                httpRequest = new HttpGet(url);
                break;
            case PUT:
                httpRequest = new HttpPut(url);
                break;
            case DELETE:
                httpRequest = new HttpDelete(url);
                break;
        }
        httpRequest.addHeader("Host", SERVER);
        httpRequest.addHeader("Connection", "Keep-Alive");
        httpRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpRequest.addHeader("authorization", authorization);

//        httpRequest.addHeader("Cookie", "");
//        httpRequest.addHeader("User-Agent", userAgent);
        return httpRequest;
    }

    /**
     * 公用post方法
     *
     * @param url
     * @param params
     */
    public static void post(String url, List<NameValuePair> params, int event) {

        AndroidHttpClient httpClient = null;
        HttpPost httpPost = null;
        //post表单参数处理
        try {
            httpClient = getHttpClient();
            httpPost = (HttpPost) getHttpRequest(url, RequestType.POST);
            if (null != params && params.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(params, UTF_8));
            }
            HttpResponse response = httpClient.execute(getHttpHost(), httpPost, getHttpContext());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                //throw AppException.http(statusCode);
            } else if (statusCode == HttpStatus.SC_OK) {
//                    Cookie[] cookies =
//                    String tmpcookies = "";
//                    for (Cookie ck : cookies) {
//                        tmpcookies += ck.toString()+";";
//                    }
//                    //保存cookie
//                    if(appContext != null && tmpcookies != ""){
//                        AppConfig.getAppConfig(AppContext.getInstance()).set("cookie", tmpcookies);
//                        appCookie = tmpcookies;
//                    }
            }
            InputStream is = response.getEntity().getContent();
            String responseBody = StringUtils.toConvertString(is);
            Cache.getInstance().parse(responseBody, event);
        } catch (Exception e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
//            httpClient.close();
        }
    }

    /**
     * 公用post方法
     *
     * @param url
     * @param event
     */
    public static void get(String url, int event) {

        AndroidHttpClient httpClient = null;
        HttpGet get = null;
        //post表单参数处理
        try {
            httpClient = getHttpClient();
            get = (HttpGet) getHttpRequest(url, RequestType.GET);
            HttpResponse response = httpClient.execute(getHttpHost(), get, getHttpContext());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                //throw AppException.http(statusCode);
            } else if (statusCode == HttpStatus.SC_OK) {
            }
            InputStream is = response.getEntity().getContent();
            String responseBody = StringUtils.toConvertString(is);
            Cache.getInstance().parse(responseBody, event);
        } catch (Exception e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
//            httpClient.close();
//            Log.d("httpClient.close()", "httpClient.close()");
        }
    }

    /**
     * 公用post方法
     *
     * @param url
     * @param event
     */
    public static void put(String url, List<NameValuePair> params, int event) {
        AndroidHttpClient httpClient = null;
        HttpPut put = null;
        //post表单参数处理
        try {
            httpClient = getHttpClient();
            put = (HttpPut) getHttpRequest(url, RequestType.PUT);
            if (null != params && params.size() > 0) {
                put.setEntity(new UrlEncodedFormEntity(params, UTF_8));
            }
            HttpResponse response = httpClient.execute(getHttpHost(), put, getHttpContext());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                //throw AppException.http(statusCode);
            } else if (statusCode == HttpStatus.SC_OK) {
            }
            InputStream is = response.getEntity().getContent();
            String responseBody = StringUtils.toConvertString(is);
            Cache.getInstance().parse(responseBody, event);
        } catch (Exception e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
//            httpClient.close();
//            Log.d("httpClient.close()", "httpClient.close()");
        }
    }

    /**
     * 公用post方法
     *
     * @param url
     * @param event
     */
    public static void doRequest(String url, List<NameValuePair> params, int event, RequestType type) {
        AndroidHttpClient httpClient = null;
        HttpRequest request = null;
        //post表单参数处理
        try {
            httpClient = getHttpClient();
//            switch (type) {
//
//                case POST:
//                    break;
//                case GET:
//                    break;
//                case PUT:
//                    break;
//                case DELETE:
//                    break;
//            }

            request = getHttpRequest(url, type);
            if (null != params && params.size() > 0) {
                ((HttpEntityEnclosingRequestBase) request).setEntity(new UrlEncodedFormEntity(params, UTF_8));
            }
            HttpResponse response = httpClient.execute(getHttpHost(), request, getHttpContext());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                //throw AppException.http(statusCode);
            } else if (statusCode == HttpStatus.SC_OK) {
            }
            InputStream is = response.getEntity().getContent();
            String responseBody = StringUtils.toConvertString(is);
            Cache.getInstance().parse(responseBody, event);
        } catch (Exception e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
//            httpClient.close();
//            Log.d("httpClient.close()", "httpClient.close()");
        }
    }

    private void cleanCookie() {
        appCookie = "";
    }

    enum RequestType {
        POST,
        GET,
        PUT,
        DELETE
    }
}
