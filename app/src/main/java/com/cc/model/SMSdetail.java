package com.cc.model;

/**
 * 节日短信内容对象
 */
public class SMSdetail {

    private int id;

    private int festivalid;

    private String smsdetail;

    public SMSdetail(int id, int festivalid, String smsdetail) {
        this.id = id;
        this.festivalid = festivalid;
        this.smsdetail = smsdetail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFestivalid(int festivalid) {
        this.festivalid = festivalid;
    }

    public void setSmsdetail(String smsdetail) {
        this.smsdetail = smsdetail;
    }

    public int getId() {
        return id;
    }

    public int getFestivalid() {
        return festivalid;
    }

    public String getSmsdetail() {
        return smsdetail;
    }
}
