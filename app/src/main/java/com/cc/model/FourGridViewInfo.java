package com.cc.model;


/**
 * FourFragment里面的gridview内容
 */
public class FourGridViewInfo {

    public String id;

    public int img_url;

    public String name;

    //1打开0关闭
    public String state;

    //1有新数据 0没有
    public int add_data;

    public FourGridViewInfo(String id, int img_url, String name, String state, int add_data) {
        this.id = id;
        this.img_url = img_url;
        this.name = name;
        this.state = state;
        this.add_data = add_data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImg_url() {
        return img_url;
    }

    public void setImg_url(int img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAdd_data() {
        return add_data;
    }

    public void setAdd_data(int add_data) {
        this.add_data = add_data;
    }
}
