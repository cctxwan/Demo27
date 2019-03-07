package com.cc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2018/6/11.
 */
public class OpenHelper extends SQLiteOpenHelper {

    //建表语句（创建用户表）
    public static final String CREATE_USER_Account = "create table User_Account ("
            + "userid text, "
            + "accountid text, "
            + "username text, "
            + "password text, "
            + "loginstate text, "
            + "phonenum text )";

    //建表语句（创建打卡表）
    public static final String CREATE_USER_Punch = "create table User_Punch ("
            + "accountid text, "
            + "punchtime text, "
            + "punchdata text )";

    //建表语句（创建设备表：用于存储手机厂商、型号、IMEI、系统版本号等）
    public static final String CREATE_USER_Device = "create table User_Device ("
            + "deviceid text, "
            + "imsi text, "
            + "phonebrands text, "
            + "devicename text, "
            + "systemversion text)";

    //建表语句（创建消息圈列表：用于three的frag列表界面）
    public static final String CREATE_USER_INFORMATION = "create table User_Information ("
            + "Information_id integer, "
            + "Information_name text, "
            + "Information_state text)";

    //建表语句（创建微信精选文章：用于four的微信精选文章列表界面）
    public static final String CREATE_USER_WXFeature = "create table User_WXFeature ("
            + "feature_id text, "
            + "feature_account text, "
            + "feature_title text, "
            + "feature_source text, "
            + "feature_url text, "
            + "feature_islike boolean, "
            + "feature_iscollection boolean)";

    //建表语句（创建微信文章评论：用于four的微信精选文章列表界面）
    public static final String CREATE_USER_WXCOMMENT = "create table User_WxComment ("
            + "comment_id integer primary key autoincrement, "
            + "comment_string text, "
            + "comment_account text, "
            + "comment_url text, "
            + "comment_islike boolean, "
            + "comment_iscollection boolean)";

    //建表语句（创建微信文章收藏：用于four的收藏列表界面）
    public static final String CREATE_USER_WXCOLLECTION = "create table User_WxCollection ("
            + "collection_id integer primary key autoincrement, "
            + "collection_title text, "
            + "collection_url text, "
            + "collection_account text)";

    /**
     * 构造方法
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }

    /**
     * 初次创建
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_USER_Account);//创建用户表
        db.execSQL(CREATE_USER_Punch);//创建用户表
        db.execSQL(CREATE_USER_Device);//创建设备表
        db.execSQL(CREATE_USER_INFORMATION);//创建消息圈列表
        db.execSQL(CREATE_USER_WXFeature);//创建微信精选文章
        db.execSQL(CREATE_USER_WXCOMMENT);//创建微信文章评论
        db.execSQL(CREATE_USER_WXCOLLECTION);//创建微信文章收藏
    }

    /**
     * 当数据库版本出现改变时
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
