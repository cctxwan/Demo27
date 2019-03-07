package com.cc.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by admin on 2018/6/14.
 */

public class DeviceInfo {

    /**
     * 获取设备的唯一标识，deviceId
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取手机品牌
     * @return
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMSI号
     */
    public static String getIMSI(Context context){
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getSubscriberId();
    }

    /**
     * 获取手机设备名称
     */
    public static String getDevicename(){
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机系统版本
     */
    public static String getSystemversion(){
        return android.os.Build.VERSION.RELEASE;
    }

}
