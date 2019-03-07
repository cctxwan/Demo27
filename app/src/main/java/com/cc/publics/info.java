package com.cc.publics;

import android.app.Activity;

import com.cc.db.SQLiteDB;
import com.cc.model.User_Device_Info;
import com.cc.model.User_Information;
import com.cc.utils.A;
import com.cc.utils.DeviceInfo;

/**
 * 一些共用的数据库方法
 */
public class info {

    /**
     * 保存设备信息
     * 设备号、imsi、手机厂商、设备名称、系统版本
     */
    public static void saveDeviceInfo(Activity activity) {
        User_Device_Info dev_info = new User_Device_Info();
        dev_info.setDeviceid(DeviceInfo.getDeviceId(activity));
        dev_info.setDevicename(DeviceInfo.getDevicename());
        dev_info.setImsi(DeviceInfo.getIMSI(activity));
        dev_info.setPhonebrands(DeviceInfo.getPhoneBrand());
        dev_info.setSystemversion(DeviceInfo.getSystemversion());
        int result = SQLiteDB.getInstance(activity).saveDevice(dev_info);
        if(result == 1){
            A.C_Log(activity, "succ");
        }
    }

    /**
     * 默认添加消息圈消息
     */
    public static void saveInformation(Activity activity) {
        User_Information info = new User_Information();
        info.setInformation_id(1);
        info.setInformation_name("消息圈");
        info.setInfomation_state("1");
        int result = SQLiteDB.getInstance(activity).saveInformation(info);
        if(result == 1){
            A.C_Log(activity, "succ");
        }
    }

}
