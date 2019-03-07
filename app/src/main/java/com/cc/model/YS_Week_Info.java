package com.cc.model;

/**
 * 运势
 * 本周
 */
public class YS_Week_Info {
    /**
     * name : 天蝎座
     * weekth : 36
     * date : 2018年09月02日-2018年09月08日
     * health :
     * job : null
     * love : 恋爱：本周是个表白的好时机，如果有心仪的人赶快去表白吧。
     * money : 财运：本周虽然你有点丢三落四，但是总体来说你的财运机会很好，也能赚到不少钱。
     * work : 工作：本周你在工作上会有个质的爆发，你的工作已经让你变得很成熟了。
     * resultcode : 200
     * error_code : 0
     */

    private String name;
    private int weekth;
    private String date;
    private String health;
    private Object job;
    private String love;
    private String money;
    private String work;
    private String resultcode;
    private int error_code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeekth() {
        return weekth;
    }

    public void setWeekth(int weekth) {
        this.weekth = weekth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public Object getJob() {
        return job;
    }

    public void setJob(Object job) {
        this.job = job;
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
