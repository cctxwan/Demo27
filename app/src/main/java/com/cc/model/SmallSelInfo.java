package com.cc.model;

/**
 * 用于设置里面的
 */
public class SmallSelInfo {

    private String id;

    private int imgurl;

    public SmallSelInfo(String id, int imgurl, String name, String state) {
        this.id = id;
        this.imgurl = imgurl;
        this.name = name;
        this.state = state;
    }

    public int getImgurl() {
        return imgurl;
    }

    public void setImgurl(int imgurl) {
        this.imgurl = imgurl;
    }

    private String name;

    private String state;

    @Override
    public String toString() {
        return "SmallSelInfo{" +
                "id='" + id + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public SmallSelInfo() {
        super();
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
