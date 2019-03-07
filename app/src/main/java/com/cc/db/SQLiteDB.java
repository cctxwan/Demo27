package com.cc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.cc.model.User_Account_Info;
import com.cc.model.User_Device_Info;
import com.cc.model.User_Information;
import com.cc.model.WxCollectionInfo;
import com.cc.model.WxCommentInfo;
import com.cc.model.setWXJXInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/6/11.
 */

public class SQLiteDB {

    /**  数据库名 */
    public static final String DB_NAME = "SQLite_DB_SUM";

    /** 数据库版本 */
    public static final int VERSION = 1;

    /** 数据库 */
    private static SQLiteDB sqliteDB;

    private SQLiteDatabase db;

    private SQLiteDB(Context context) {
        /** 初始化数据库 */
        OpenHelper dbHelper = new OpenHelper(context, DB_NAME, null, VERSION);
        /** 获取db */
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取SqliteDB实例
     * @param context
     */
    public synchronized static SQLiteDB getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new SQLiteDB(context);
        }
        return sqliteDB;
    }

    /**
     * 将User实例存储到数据库。
     */
    public int saveUser(User_Account_Info user) {
        if (user != null) {
            Cursor cursor = db.rawQuery("select * from User_Account where userid=?", new String[]{user.getUserid()});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
                    db.execSQL("insert into User_Account(userid,accountid,username,password,loginstate,phonenum) values(?,?,?,?,?,?) "
                            , new String[]{
                                    user.getUserid().toString(),
                                    user.getAccountid().toString(),
                                    user.getUsername().toString(),
                                    user.getPassword().toString(),
                                    user.getLoginstate().toString(),
                                    user.getPhonenum().toString(),
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 1;
            }
        }
        else {
            Log.d("错误", "user为空");
            return 0;
        }
    }

    /**
     * 从数据库User_Account表中读取数据
     */
    public List<User_Account_Info> getUsers() {
        List<User_Account_Info> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from User_Account", new String[]{});
        if (cursor.moveToFirst()) {
            do {
                User_Account_Info userInfo = new User_Account_Info();
                userInfo.setUserid(cursor.getString(cursor.getColumnIndex("userid")));
                userInfo.setAccountid(cursor.getString(cursor.getColumnIndex("accountid")));
                userInfo.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                userInfo.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                userInfo.setPhonenum(cursor.getString(cursor.getColumnIndex("phonenum")));
                userInfo.setLoginstate(cursor.getString(cursor.getColumnIndex("loginstate")));
                list.add(userInfo);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 根据当前userid获取对应的user对象
     * @param userId
     */
    public List<User_Account_Info> selectByIdUser(String userId){
        List<User_Account_Info> list = new ArrayList<User_Account_Info>();
        String id = String.valueOf(userId);
        Cursor cursor = db.rawQuery("select * from User_Account where userid=?", new String[]{id});

        if(cursor.moveToFirst()){

            User_Account_Info userInfo = new User_Account_Info();
            userInfo.setUserid(cursor.getString(cursor.getColumnIndex("userid")));
            userInfo.setAccountid(cursor.getString(cursor.getColumnIndex("accountid")));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            userInfo.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            userInfo.setPhonenum(cursor.getString(cursor.getColumnIndex("phonenum")));
            userInfo.setLoginstate(cursor.getString(cursor.getColumnIndex("loginstate")));

            list.add(userInfo);

        }
        return list;
    }



    /**
     * 根据账号名和密码查询对象
     * @param username
     * @param password
     * @return
     */
    public User_Account_Info selectByLogin(String username, String password){
        User_Account_Info info = new User_Account_Info();
        Cursor cursor = db.rawQuery("select * from User_Account where username=? and password=?", new String[]{username, password});
        if(cursor.moveToFirst()){
            return info;
        }
        return null;
    }

    /**
     * 修改收藏属性值
     * @param username
     * @param password
     */
    public void updateUser(String username, String password, String userId) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long ret = -1;
        do {
            ret = db.update("", values, "userid=?",
                    new String[] { userId });
        } while (ret < 0);
    }

    /**
     * 根据当前id删除所对应的的user对象
     * @param userId
     * @return
     */
    public int deleteById(String userId) {
        if (userId.length() < 0) {
            return -1;
        }
        return db.delete("User_Account", "userid=?", new String[]{ userId });
    }

    /**
     * 存储Device信息
     */
    public int saveDevice(User_Device_Info info) {
        if (info != null) {
            Cursor cursor = db.rawQuery("select * from User_Device where deviceid=?", new String[]{info.getDeviceid().toString()});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
                    db.execSQL("insert into User_Device(deviceid,imsi,phonebrands,devicename,systemversion) values(?,?,?,?,?) ", new String[]{info.getDeviceid().toString(), info.getImsi().toString(),info.getPhonebrands().toString(), info.getDevicename().toString(), info.getSystemversion().toString()});
                } catch (Exception e) {
                    Log.d("错误", e.getMessage().toString());
                }
                return 1;
            }
        }
        else {
            Log.d("错误", "user为空");
            return 0;
        }
    }

    /**
     * 存储消息圈信息
     */
    public int saveInformation (User_Information info) {
        if (info != null) {
            Cursor cursor = db.rawQuery("select * from User_Information where Information_id=?", new String[]{info.getInformation_id().toString()});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
                    db.execSQL("insert into User_Information(Information_id,Information_name,Information_state) values(?,?,?) ", new String[]{info.getInformation_id().toString(), info.getInformation_name().toString(),info.getInfomation_state().toString()});
                } catch (Exception e) {
                    Log.d("错误", e.getMessage().toString());
                }
                return 1;
            }
        }
        else {
            Log.d("错误", "user为空");
            return 0;
        }
    }

    /**
     * 根据state遍历所有为1状态的info
     * @param state
     * @return
     */
    public List<User_Information> selectByIdInformation(String state) {
        List<User_Information> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from User_Information where Information_state=?", new String[]{state});

        if(cursor.moveToFirst()){

            User_Information userInfo = new User_Information();
            userInfo.setInformation_id(cursor.getInt(cursor.getColumnIndex("Information_id")));
            userInfo.setInformation_name(cursor.getString(cursor.getColumnIndex("Information_name")));
            userInfo.setInfomation_state(cursor.getString(cursor.getColumnIndex("Information_state")));

            list.add(userInfo);

        }
        return list;
    }


    /**
     * 查询消息圈列表信息
     * @return
     */
    public List<User_Information> selectInformation() {
        List<User_Information> list = new ArrayList<User_Information>();
        Cursor cursor = db.query("User_Information", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                User_Information userInfo = new User_Information();
                userInfo.setInformation_id(cursor.getInt(cursor.getColumnIndex("Information_id")));
                userInfo.setInformation_name(cursor.getString(cursor.getColumnIndex("Information_name")));
                userInfo.setInfomation_state(cursor.getString(cursor.getColumnIndex("Information_state")));
                list.add(userInfo);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 微信精选文章保存
     * @return
     */
    public void saveWXFeature(String account, List<setWXJXInfo> infos, boolean islike, boolean iscollection) {
        if (infos.size() != 0) {
            List<setWXJXInfo> lists = selectWXFeature();
            for(int i = 0; i < lists.size(); i++){
                for (int j = 0; j < infos.size(); j++){
                    if(lists.get(i).getId().equals(infos.get(j).getId())){
                        infos.remove(j);
                    }
                }
            }
            for (int i = 0; i < infos.size(); i++){
                try {
                    db.execSQL("insert into User_WXFeature(feature_id,feature_account,feature_title,feature_source,feature_url,feature_islike,feature_iscollection) values(?,?,?,?,?,?,?) "
                            , new String[]{
                                    infos.get(i).getId(),
                                    account,
                                    infos.get(i).getTitle(),
                                    infos.get(i).getSource(),
                                    infos.get(i).getUrl(),
                                    islike + "",
                                    iscollection + "",
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.d("错误", "info");
        }
    }

    /**
     * 微信精选文章查询
     * @return
     */
    public List<setWXJXInfo> selectWXFeature() {
        List<setWXJXInfo> list = new ArrayList<setWXJXInfo>();
        Cursor cursor = db.query("User_WXFeature", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                setWXJXInfo userInfo = new setWXJXInfo();
                userInfo.setId(cursor.getString(cursor.getColumnIndex("feature_id")));
                userInfo.setSource(cursor.getString(cursor.getColumnIndex("feature_source")));
                userInfo.setTitle(cursor.getString(cursor.getColumnIndex("feature_title")));
                userInfo.setUrl(cursor.getString(cursor.getColumnIndex("feature_url")));
                list.add(userInfo);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 微信精选文章点赞和收藏状态修改
     * @param id
     * @param islike
     * @param iscollection
     * @return
     */
    public boolean updateWXFeaturelike(String id, boolean islike, boolean iscollection) {
        ContentValues values = new ContentValues();
        values.put("feature_islike", islike + "");
        values.put("feature_iscollection", iscollection + "");
        long ret = -1;
        do {
            ret = db.update("User_WXFeature", values, "feature_id=?",
                    new String[] {id});
            System.out.println(ret);
            if(ret == 1){
                return true;
            }
        } while (ret < 0);
        return false;
    }

    /**
     * 根据ID获取微信精选文章点赞和收藏状态
     * @param id
     * @return
     */
    public setWXJXInfo getFeatruelike(String id) {
        setWXJXInfo wxjxInfo = new setWXJXInfo();
        Cursor cursor = db.rawQuery("select * from User_WXFeature where feature_id=?", new String[]{ id });
        if(cursor.moveToFirst()){
            do {
                wxjxInfo.setId(cursor.getString(cursor.getColumnIndex("feature_id")));
                wxjxInfo.setTitle(cursor.getString(cursor.getColumnIndex("feature_title")));
                wxjxInfo.setSource(cursor.getString(cursor.getColumnIndex("feature_source")));
                wxjxInfo.setUrl(cursor.getString(cursor.getColumnIndex("feature_url")));
                wxjxInfo.setAccount(cursor.getString(cursor.getColumnIndex("feature_account")));
                wxjxInfo.setIslike(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("feature_islike"))));
                wxjxInfo.setIscollection(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("feature_iscollection"))));
            } while (cursor.moveToNext());
        }
        return wxjxInfo;
    }


    /**
     * 微信精选文章评论保存
     * @param info
     * @return
     */
    public int saveWXcomment(WxCommentInfo info) {
        if (info != null) {
            try {
                db.execSQL("insert into User_WxComment(comment_string,comment_account,comment_url,comment_islike,comment_iscollection) values(?,?,?,?,?) "
                        , new String[]{
                                info.getComment_string(),
                                info.getComment_account(),
                                info.getComment_url(),
                                info.isComment_islike() + "",
                                info.isComment_iscollection() + "",
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        } else {
            Log.d("错误", "info");
            return 0;
        }
    }

    /**
     * 获取微信文章评论的信息
     * @return
     */
    public List<WxCommentInfo> getWXcommentContent(){
        List<WxCommentInfo> lists = new ArrayList<>();
        Cursor cursor = db.query("User_WxComment", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                WxCommentInfo list = new WxCommentInfo();
                list.setComment_id(cursor.getInt(cursor.getColumnIndex("comment_id")));
                list.setComment_account(cursor.getString(cursor.getColumnIndex("comment_account")));
                list.setComment_string(cursor.getString(cursor.getColumnIndex("comment_string")));
                list.setComment_url(cursor.getString(cursor.getColumnIndex("comment_url")));
                list.setComment_islike(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("comment_islike"))));
                list.setComment_iscollection(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("comment_iscollection"))));
                lists.add(list);
            } while (cursor.moveToNext());
        }
        return lists;
    }

    /**
     * 微信精选文章添加到收藏
     * @param info
     * @return
     */
    public int saveWXCollection(WxCollectionInfo info) {
        if (info != null) {
            try {
                db.execSQL("insert into User_WxCollection(collection_title,collection_url,collection_account) values(?,?,?) "
                        , new String[]{
                                info.getCollection_title(),
                                info.getCollection_url(),
                                info.getCollection_account()
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        } else {
            Log.d("错误", "info");
            return 0;
        }
    }

    /**
     * 修改微信精选文章的参数（本文是否点赞）
     * @param islike
     * @param comment_account
     * @return
     */
//    public boolean updateWxCommentLike(Boolean islike, String comment_account){
//        ContentValues values = new ContentValues();
//        values.put("comment_islike", islike);
//        long ret = -1;
//        do {
//            ret = db.update("User_WxComment", values, "comment_account=?",
//                    new String[] { comment_account + "" });
//            System.out.println(ret);
//            if(ret == 1){
//                return true;
//            }
//        } while (ret < 0);
//        return false;
//    }

    /**
     * 打卡
     * @param accountid
     * @param punchtime
     * @param punchData
     * @return
     */
    public int savePunch(String accountid, String punchtime, String punchData){
        if (accountid != null) {
            try {
                db.execSQL("insert into User_Punch(accountid,punchtime,punchdata) values(?,?,?) "
                        , new String[]{
                                accountid,
                                punchtime,
                                punchData
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        } else {
            Log.d("错误", "info");
            return 0;
        }
    }


    /**
     * 根据日期查询今天是否打卡
     * @param accountid
     * @param punchData
     * @return
     */
    public boolean isPunchByData(String accountid, String punchData){
        ContentValues values = new ContentValues();
        values.put("punchdata", punchData);
        long ret = -1;
        do {
            ret = db.update("User_Punch", values, "accountid=?",
                    new String[] {accountid});
            if(ret == 1){
                return true;
            }
        } while (ret < 0);
        return false;
    }

}
