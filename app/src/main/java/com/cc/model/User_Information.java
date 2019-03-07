package com.cc.model;

/**
 * 信息实体类
 * @version 2018年7月20日10:10:37
 */
public class User_Information {

    /** 信息id */
    public Integer Information_id;

    /** 信息名 */
    public String Information_name;

    /** 信息状态（0代表正常，1代表正在审核，2代表审核失败，3代表已删除） */
    public String Infomation_state;

    @Override
    public String toString() {
        return "User_Information{" +
                "Information_id=" + Information_id +
                ", Information_name='" + Information_name + '\'' +
                ", Infomation_state='" + Infomation_state + '\'' +
                '}';
    }

    public void setInformation_name(String information_name) {
        Information_name = information_name;
    }

    public void setInfomation_state(String infomation_state) {
        Infomation_state = infomation_state;
    }

    public Integer getInformation_id() {

        return Information_id;
    }

    public String getInformation_name() {
        return Information_name;
    }

    public String getInfomation_state() {
        return Infomation_state;
    }

    public void setInformation_id(Integer information_id) {

        Information_id = information_id;
    }
}
