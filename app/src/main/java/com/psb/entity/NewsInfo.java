package com.psb.entity;

/**
 * Created by zl on 2015/1/27.
 */
public class NewsInfo {

    private int id;
    private int article_cat_id;
    private int is_hot;
    private String title;
    private String content;
    private String thumb;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticle_cat_id() {
        return article_cat_id;
    }

    public void setArticle_cat_id(int article_cat_id) {
        this.article_cat_id = article_cat_id;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
