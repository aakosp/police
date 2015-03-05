package com.psb.entity;

/**
 * Created by zl on 2015/2/5.
 */
public class User {

    public static final String POLICE = "POLICE";

    private int id;
    private String user_name;
    private String role;
    private String name;
    private String password;
    private int police_station_id;
    private int address;
    private String phone;

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPolice_station_id() {
        return police_station_id;
    }

    public void setPolice_station_id(int police_station_id) {
        this.police_station_id = police_station_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
