package com.cc.model;

/**
 * AddTabItem
 * GridView的modle
 */
public class Add_MyTabItemInfo {

    public int id;
    public String name;
    //0代表当前的tabitem， 1代表未选中的item
    public int state;

    @Override
    public String toString() {
        return "AddTabItemInfo{" +
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

    public Add_MyTabItemInfo(int id, String name, int state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }
}
