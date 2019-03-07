package com.cc.controller;

import android.app.PendingIntent;
import android.telephony.SmsManager;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by admin on 2018/7/23.
 */

public class SMSController {

    public int sendMsg(String number, String msg, PendingIntent spi, PendingIntent dpi){
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> contents = new ArrayList<>();
        for (String content  : contents){
            smsManager.sendTextMessage(number, null, content, spi, dpi);
        }
        return contents.size();
    }

    public int sendMsg(Set<String> numbers, String msg, PendingIntent spi, PendingIntent dpi){
        int result = 0;
        for (String number  : numbers){
            int count = sendMsg(number, msg, spi, dpi);
            result += count;
        }
        return result;
    }

}
