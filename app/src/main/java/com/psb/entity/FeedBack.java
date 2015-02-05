package com.psb.entity;

/**
 * Created by zl on 2015/2/5.
 */
public class FeedBack {

    private int id;
    private int opinion_id;
    private String reply_content;
    private int user_id;
    private String reply_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOpinion_id() {
        return opinion_id;
    }

    public void setOpinion_id(int opinion_id) {
        this.opinion_id = opinion_id;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }
}
