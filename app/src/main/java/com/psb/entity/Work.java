package com.psb.entity;

import java.util.List;

/**
 * Created by aako on 2015/3/15.
 */
public class Work {

    public static final String OPINION = "OPINION";
    public static final String DISPUTE = "DISPUTE";
    public static final String SECURITY = "SECURITY";
    public static final String EDUCATION = "EDUCATION";
    public static final String SERVING = "SERVING";
    public static final String OTHER = "OTHER";

    private int id;
    private int police_id;
    private String title;
    private String type;
    private String time;
    private String content;
    private List<String> picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPolice_id() {
        return police_id;
    }

    public void setPolice_id(int police_id) {
        this.police_id = police_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }
}
