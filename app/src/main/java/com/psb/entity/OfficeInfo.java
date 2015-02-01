package com.psb.entity;

/**
 * Created by aako on 2015/2/1.
 */
public class OfficeInfo {

    private String id;
    private String name;
    private String addr;
    private String tel;
    private float latitude;
    private float lontitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLontitude() {
        return lontitude;
    }

    public void setLontitude(float lontitude) {
        this.lontitude = lontitude;
    }
}
