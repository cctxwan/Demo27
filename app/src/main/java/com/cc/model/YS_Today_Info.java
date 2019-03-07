package com.cc.model;

/**
 * 运势
 * 今日
 */
public class YS_Today_Info {
    /**
     * date : 20180906
     * name : 天秤座
     * datetime : 2018年09月06日
     * all : 20%
     * color : 绿色
     * health : 60%
     * love : 20%
     * money : 20%
     * number : 3
     * QFriend : 双鱼座
     * summary : 今天会和领导有比较多的互动和交流，自己容易不敢表达自己的真实想法；家里会有比较多让自己操心的事情或者家人给予的关心远超出自己的实际需要，让自己感到有些心累。
     * work : 20%
     * resultcode : 200
     * error_code : 0
     */

    private int date;
    private String name;
    private String datetime;
    private String all;
    private String color;
    private String health;
    private String love;
    private String money;
    private int number;
    private String QFriend;
    private String summary;
    private String work;
    private String resultcode;
    private int error_code;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQFriend() {
        return QFriend;
    }

    public void setQFriend(String QFriend) {
        this.QFriend = QFriend;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
