package com.cc.model;

/**
 * Created by admin on 2018/6/14.
 */

public class User_Device_Info {

    private String deviceid;
    private String imsi;
    private String phonebrands;
    private String devicename;
    private String systemversion;

    @Override
    public String toString() {
        return "User_Device_Info{" +
                "deviceid='" + deviceid + '\'' +
                ", imsi='" + imsi + '\'' +
                ", phonebrands='" + phonebrands + '\'' +
                ", devicename='" + devicename + '\'' +
                ", systemversion='" + systemversion + '\'' +
                '}';
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public void setPhonebrands(String phonebrands) {
        this.phonebrands = phonebrands;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public void setSystemversion(String systemversion) {
        this.systemversion = systemversion;
    }

    public String getDeviceid() {

        return deviceid;
    }

    public String getImsi() {
        return imsi;
    }

    public String getPhonebrands() {
        return phonebrands;
    }

    public String getDevicename() {
        return devicename;
    }

    public String getSystemversion() {
        return systemversion;
    }
}
