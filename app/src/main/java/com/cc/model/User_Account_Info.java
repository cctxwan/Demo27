package com.cc.model;

/**
 * Created by admin on 2018/6/12.
 */

public class User_Account_Info {

    private String userid ;

    private String accountid;

    private String username;

    private String password;

    private String phonenum;

    private String loginstate;

    public String getLoginstate() {
        return loginstate;
    }

    public void setLoginstate(String loginstate) {
        this.loginstate = loginstate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getAccountid() {
        return accountid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhonenum() {
        return phonenum;
    }

    @Override
    public String toString() {
        return "User_Account_Info{" +
                "userid='" + userid + '\'' +
                ", accountid='" + accountid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", loginstate='" + loginstate + '\'' +
                '}';
    }
}
