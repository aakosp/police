package com.psb.entity;

/**
 * Created by zl on 2015/2/5.
 */
public class OfficeInfo {

    private int id;
    private String name;
    private String phone;
    private String address;
    private float location_x;
    private float location_y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLocation_x() {
        return location_x;
    }

    public void setLocation_x(float location_x) {
        this.location_x = location_x;
    }

    public float getLocation_y() {
        return location_y;
    }

    public void setLocation_y(float location_y) {
        this.location_y = location_y;
    }
}
