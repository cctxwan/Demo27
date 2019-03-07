package com.cc.model;

/**
 * nba赛程球队信息
 */
public class NBATeamInfo {

    private int id;
    private String img;
    private String name;

    @Override
    public String toString() {
        return "NBASCInfo{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public NBATeamInfo(int id, String img, String name) {
        this.id = id;
        this.img = img;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
