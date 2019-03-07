package com.cc.model;

public class YS_Month_Info {
    /**
     * date : 2018年09月
     * name : 天秤座
     * month : 9
     * all : 本月的天秤有种能力施展不开的感觉，可以试着和亲密的朋友出去玩玩，找回良好的心态。
     * health :
     * love : 本月有伴的天秤和另一半相处比较稳定平和，而单身的天秤感情观还是一片模糊。
     * money : 本月的天秤财运并不好，特别是要注意的是小心合作伙伴坑你。
     * work : 本月天秤人在工作上会有个阶段性的总结而得到不错的回报。本月的天秤会在暗地里下功夫，但是如果有好的方法还是要求助的，否则很有可能白忙活。
     * happyMagic :
     * resultcode : 200
     * error_code : 0
     */

    private String date;
    private String name;
    private int month;
    private String all;
    private String health;
    private String love;
    private String money;
    private String work;
    private String happyMagic;
    private String resultcode;
    private int error_code;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getHappyMagic() {
        return happyMagic;
    }

    public void setHappyMagic(String happyMagic) {
        this.happyMagic = happyMagic;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
