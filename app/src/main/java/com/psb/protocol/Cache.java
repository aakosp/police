package com.psb.protocol;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.psb.entity.Article;
import com.psb.event.Event;
import com.psb.event.EventNotifyCenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zl on 2015/2/4.
 */
public class Cache {

    private static Cache cache;

    private Map<Integer, Article> articleMap = new HashMap<>();

    private Cache() {
    }

    public synchronized static Cache getInstance() {
        if (null == cache) {
            cache = new Cache();
        }
        return cache;
    }

    public void parse(String responseBody, int event) {

        switch (event) {
            case Event.NEWS_1:
            case Event.NEWS_2:
            case Event.NEWS_3:
            case Event.NEWS_4:
            case Event.NEWS_5:
            case Event.NEWS_6:
            case Event.NEWS_7:
            case Event.NEWS_8:
            case Event.NEWS_9:
                Article article = JSON.parseObject(responseBody, Article.class);
                articleMap.put(event, article);
                break;

        }

        EventNotifyCenter.getInstance().doNotify(event);
    }

    public Article getArticle(int event) {
        return articleMap.get(event);
    }

}
