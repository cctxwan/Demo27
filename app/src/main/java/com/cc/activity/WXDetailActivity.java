package com.cc.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.R;
import com.cc.db.SQLiteDB;
import com.cc.model.User_Account_Info;
import com.cc.model.WxCommentInfo;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class WXDetailActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = WXDetailActivity.this;
    ImageView wxd_img_back, img_wxd_load;
    TextView wxd_txt_share;
    WebView wxd_webview;
    //最下面的评论框
    RelativeLayout rel2;

    //评论框
    EditText wxdetail_et_string;
    TextView wxdetail_txt_comment;
    ImageView wxdetail_img_commentlist;

    //点赞和收藏
    LikeButton wxdetail_likebt, wxdetail_collection;

    //ID
    public static String ID = "";
    //URL
    public static String URL = "";
    //title
    public static String TITLE = "";
    //IMG
//    public static String IMG = "";
    //SOURCE
    public static String SOURCE = "";

    //默认不点赞
    public static boolean ISLIKE = false;

    //默认不收藏
    public static boolean ISCOLLECTION = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_wxdetail;
    }

    @Override
    public void initView() {
        wxd_img_back = findViewById(R.id.wxd_img_back);

        img_wxd_load = findViewById(R.id.img_wxd_load);
        wxd_webview = findViewById(R.id.wxd_webview);
        wxd_txt_share = findViewById(R.id.wxd_txt_share);
        rel2 = findViewById(R.id.rel2);

        //点赞控件
        wxdetail_likebt = findViewById(R.id.wxdetail_likebt);
        //当前id的文章点赞状态
        A.C_Log(activity, SQLiteDB.getInstance(activity).getFeatruelike(ID).isIslike() + "");
        if(SQLiteDB.getInstance(activity).getFeatruelike(ID).isIslike()){
            wxdetail_likebt.setUnlikeDrawableRes(R.mipmap.ic_heart_on);
        }else{
            wxdetail_likebt.setLikeDrawableRes(R.mipmap.ic_heart_off);
        }
        //点赞的点击事件
        wxdetail_likebt.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(SQLiteDB.getInstance(activity).getFeatruelike(ID).isIslike()){
                    ISLIKE = false;
                    if (SQLiteDB.getInstance(activity).updateWXFeaturelike(
                            ID, ISLIKE, ISCOLLECTION)) {
                        wxdetail_likebt.setLikeDrawableRes(R.mipmap.ic_heart_off);
                        A.C_Toast(activity , "已取消", 2);
                    } else {
                        A.C_Toast(activity, "取消失败", 2);
                    }
                }else{
                    ISLIKE = true;
                    if (SQLiteDB.getInstance(activity).updateWXFeaturelike(
                            ID, ISLIKE, ISCOLLECTION)) {
                        wxdetail_likebt.setLikeDrawableRes(R.mipmap.ic_heart_on);
                        A.C_Toast(activity, "已点赞", 2);
                    } else {
                        A.C_Toast(activity, "点赞失败", 2);
                    }
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if(SQLiteDB.getInstance(activity).getFeatruelike(ID).isIslike()){
                    ISLIKE = false;
                    if (SQLiteDB.getInstance(activity).updateWXFeaturelike(
                            ID, ISLIKE, ISCOLLECTION)) {
                        wxdetail_likebt.setUnlikeDrawableRes(R.mipmap.ic_heart_off);
                        A.C_Toast(activity , "已取消", 2);
                    } else {
                        A.C_Toast(activity, "取消失败", 2);
                    }
                }else{
                    ISLIKE = true;
                    if (SQLiteDB.getInstance(activity).updateWXFeaturelike(
                            ID, ISLIKE, ISCOLLECTION)) {
                        wxdetail_likebt.setUnlikeDrawableRes(R.mipmap.ic_heart_on);
                        A.C_Toast(activity, "已点赞", 2);
                    } else {
                        A.C_Toast(activity, "点赞失败", 2);
                    }
                }
            }
        });

        //收藏按钮
        wxdetail_collection = findViewById(R.id.wxdetail_collection);
        //当前id的文章收藏状态
        A.C_Log(activity, SQLiteDB.getInstance(activity).getFeatruelike(ID).isIscollection() + "");
        if(SQLiteDB.getInstance(activity).getFeatruelike(ID).isIscollection()){
            wxdetail_collection.setUnlikeDrawableRes(R.mipmap.collection_on);
        }else{
            wxdetail_collection.setLikeDrawableRes(R.mipmap.collection_off);
        }
        //收藏的点击事件
        wxdetail_collection.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(SQLiteDB.getInstance(activity).getFeatruelike(ID).isIscollection()){
                    ISCOLLECTION = false;
                    if (SQLiteDB.getInstance(activity).updateWXFeaturelike(
                            ID, ISLIKE, ISCOLLECTION)) {
                        wxdetail_collection.setLikeDrawableRes(R.mipmap.collection_off);
                        A.C_Toast(activity , "已取消", 2);
                    } else {
                        A.C_Toast(activity, "取消失败", 2);
                    }
                }else{
                    ISCOLLECTION = true;
                    if (SQLiteDB.getInstance(activity).updateWXFeaturelike(
                            ID, ISLIKE, ISCOLLECTION)) {
                        wxdetail_collection.setLikeDrawableRes(R.mipmap.collection_on);
                        A.C_Toast(activity , "已收藏", 2);
                    } else {
                        A.C_Toast(activity, "收藏失败", 2);
                    }
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                if(SQLiteDB.getInstance(activity).getFeatruelike(ID).isIscollection()){
                    ISCOLLECTION = false;
                    if (SQLiteDB.getInstance(activity).updateWXFeaturelike(
                            ID, ISLIKE, ISCOLLECTION)) {
                        wxdetail_collection.setUnlikeDrawableRes(R.mipmap.collection_off);
                        A.C_Toast(activity , "已取消", 2);
                    } else {
                        A.C_Toast(activity, "取消失败", 2);
                    }
                }else{
                    ISCOLLECTION = true;
                    if (SQLiteDB.getInstance(activity).updateWXFeaturelike(
                            ID, ISLIKE, ISCOLLECTION)) {
                        wxdetail_collection.setUnlikeDrawableRes(R.mipmap.collection_on);
                        A.C_Toast(activity, "已收藏", 2);
                    } else {
                        A.C_Toast(activity, "收藏失败", 2);
                    }
                }
            }
        });

        //评论框
        wxdetail_et_string = findViewById(R.id.wxdetail_et_string);
        wxdetail_txt_comment = findViewById(R.id.wxdetail_txt_comment);
        wxdetail_img_commentlist = findViewById(R.id.wxdetail_img_commentlist);


        wxdetail_txt_comment.setOnClickListener(this);
        wxdetail_img_commentlist.setOnClickListener(this);

        wxd_txt_share.setOnClickListener(this);
        wxd_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        img_wxd_load.setVisibility(View.VISIBLE);
        A.openA(activity, img_wxd_load);

        Intent intent = getIntent();
        if(intent == null) return;
        ID = intent.getStringExtra("id");
        URL = intent.getStringExtra("url");
        TITLE = intent.getStringExtra("title");
//        IMG = intent.getStringExtra("img");
        SOURCE = intent.getStringExtra("source");
        A.C_Log(activity , "id=" + ID + ",URL=" + URL + ",TITLE=" + TITLE + ",source=" + SOURCE);

        if(URL == null){
            Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
            return;
        }

        wxd_webview.loadUrl(URL);


        //设置可自由缩放网页
        wxd_webview.getSettings().setSupportZoom(true);
        wxd_webview.getSettings().setBuiltInZoomControls(true);
        //支持JavaScript
        wxd_webview.getSettings().setJavaScriptEnabled(true);
        wxd_webview.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }
                return false;
            }

            /**
             * webview 加载完成了
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                img_wxd_load.setVisibility(View.GONE);
                wxd_webview.setVisibility(View.VISIBLE);
                rel2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
                wxd_webview.loadUrl("");
                if(errorCode == -2){
                    //无网
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.wxd_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        } else if (temdId == R.id.wxd_txt_share){
            /**
             * 从底部弹出一个分享的界面
             */
            Intent intent = new Intent(activity, ShareListActivity.class);
            intent.putExtra("title", TITLE);
            intent.putExtra("img", "");
            intent.putExtra("url", URL);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else if (temdId == R.id.wxdetail_txt_comment){

            //发表评论

            //第一步   获取内容和当前账号id
            String et_comment = wxdetail_et_string.getText().toString().trim();
            List<User_Account_Info> users = SQLiteDB.getInstance(activity).getUsers();
            if(users == null){
                A.C_Toast(activity, getResources().getString(R.string.notlogin), 2);
                return;
            }

            //将账号id和评论内容和文章的url作为主要信息保存进数据库
            int isSucc = SQLiteDB.getInstance(activity).saveWXcomment(
                    new WxCommentInfo(
                            URL,
                            users.get(0).getAccountid(),
                            et_comment,
                            ISLIKE,
                            ISCOLLECTION)
            );
            A.C_Log(activity, "isSucc=" + isSucc);


            //成功或失败所处理的逻辑
            if(isSucc == 1){
                wxdetail_et_string.setText("");
                A.C_Toast(activity, getResources().getString(R.string.comment_succ), 2);
            }else {
                A.C_Toast(activity, getResources().getString(R.string.comment_fail), 2);
            }

        } else if (temdId == R.id.wxdetail_img_commentlist){
            //加载评论页面
            List<WxCommentInfo> lists = SQLiteDB.getInstance(activity).getWXcommentContent();
            A.C_Log(activity, "lists=" + lists.toString());
        }
    }

}
