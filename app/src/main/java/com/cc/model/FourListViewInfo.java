package com.cc.model;

/**
 * Created by admin on 2018/7/25.
 */

public class FourListViewInfo {

    public String id;

    public int img_url;

    public String name;

    public String state;

    public FourListViewInfo(String id, int img_url, String name, String state) {
        this.id = id;
        this.img_url = img_url;
        this.name = name;
        this.state = state;
    }

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

    public int getImg_url() {
        return img_url;
    }

    public void setImg_url(int img_url) {
        this.img_url = img_url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
