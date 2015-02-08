package com.psb.entity;

import java.util.List;

/**
 * Created by aako on 2015/2/8.
 */
public class AddrList {

    private int id;
    private int parentid;
    private String name;
    private int order;
    private List<Addr> child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Addr> getChild() {
        return child;
    }

    public void setChild(List<Addr> child) {
        this.child = child;
    }

}
