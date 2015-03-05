package com.psb.entity;

/**
 * Created by aako on 2015/2/8.
 */
public class PoliceInfo {
    private int id;
    private int address_id;
    private int police_id;
    private Addr address;
    private User police;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getPolice_id() {
        return police_id;
    }

    public void setPolice_id(int police_id) {
        this.police_id = police_id;
    }

    public Addr getAddr() {
        return address;
    }

    public void setAddr(Addr addr) {
        this.address = addr;
    }

    public User getPolice() {
        return police;
    }

    public void setPolice(User police) {
        this.police = police;
    }
}