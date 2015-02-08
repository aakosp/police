package com.psb.entity;

import java.util.List;

/**
 * Created by zl on 2015/2/4.
 */
public class Article {

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private int from;
    private int to;
    private List<NewsInfo> data;
    private List<NewsInfo> banner;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public List<NewsInfo> getData() {
        return data;
    }

    public void setData(List<NewsInfo> data) {
        this.data = data;
    }

    public List<NewsInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<NewsInfo> banner) {
        this.banner = banner;
    }
}
