package com.cc.utils;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.R;
import com.cc.db.SQLiteDB;
import com.cc.model.User_Account_Info;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 工具类
 */
public class A {

    private static Animation rotateAnimation;

    //log日志标识  1代表打印日志，反之
    private static int C_LOG_CODE = 1;

    private static Toast toast;

    //Toast弹出位置
    private static int TOP = 1;
    private static int BOTTOM = 2;
    private static int LEFT = 3;
    private static int RIGHT = 4;


    /**
     * 开启一个动画
     * @param img
     */
    public static void openA(Activity activity, ImageView img){
        //加载loading动画
        rotateAnimation = AnimationUtils.loadAnimation(activity, R.anim.loading);
        LinearInterpolator interpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(interpolator);
        img.startAnimation(rotateAnimation);
    }

    /**
     * 输出日志
     * @param activity
     * @param msg
     */
    public static void C_Log(Activity activity, String msg){
        if(C_LOG_CODE == 0){
            Log.i(activity.getPackageName(), "不执行日志输出，请在A.class里修改日志标识");
        }else if(C_LOG_CODE == 1){
            Log.i(activity.getPackageName(), "输出日志=" + msg);
        }
    }

    /**
     * Toast
     * @param activity
     * @param msg
     * @param flag
     */
    public static void C_Toast(Activity activity, String msg, int flag){
        if(activity == null) return;
        if(msg == null) return;
        if(toast == null){
            toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        //上下左右
        if(flag == TOP){
            toast.setGravity(Gravity.TOP, 0, 0);
        }else if(flag == BOTTOM){
            toast.setGravity(Gravity.BOTTOM, 0, 100);
        }else if(flag == LEFT){
            toast.setGravity(Gravity.LEFT, 0, 0);
        }else if(flag == RIGHT){
            toast.setGravity(Gravity.RIGHT, 0, 0);
        }
        toast.show();
    }

    /**
     * 判断当前是否登录
     * @param activity
     * @return
     */
    public static boolean isUserLogin(Activity activity){
        List<User_Account_Info> users = SQLiteDB.getInstance(activity).getUsers();
        if(users.size() == 0){
            A.C_Toast(activity, activity.getResources().getString(R.string.notlogin), 2);
        }else{
            return true;
        }
        return false;
    }

    /**
     * 获取当前时间
     * @param activity
     */
    public static String getTime(Activity activity){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Str = sdf.format(date);
        C_Log(activity, "格式化后的时间：" + Str);
        return Str;
    }

    /**
     * 获取当前日期
     * @param activity
     */
    public static String getData(Activity activity){
        //这是一种方式，这里我们用另一种截取的方式
//        Calendar now = Calendar.getInstance();
//        System.out.println("年: " + now.get(Calendar.YEAR));
//        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
//        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
//        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));
//        System.out.println("分: " + now.get(Calendar.MINUTE));
//        System.out.println("秒: " + now.get(Calendar.SECOND));

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Str = sdf.format(date);
        String data = Str.substring(0, 9);
        C_Log(activity, "格式化后的日期：" + data);
        return data;
    }

}
