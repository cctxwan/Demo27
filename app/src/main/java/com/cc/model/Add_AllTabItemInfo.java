package com.cc.model;

public class Add_AllTabItemInfo {

    public int id;
    public String name;
    //0代表显示在界面上， 1代表未显示在界面上
    public int state;

    public Add_AllTabItemInfo(int id, String name, int state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Add_AllTabItemInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state=" + state +
                '}';
    }

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
