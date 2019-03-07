package com.cc.activity;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;

import com.cc.R;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.ActivityMan;
import com.cc.utils.Helper;

/**
 * 字体转换activity
 *
 */
public class FontChangeActivity extends BaseActivity implements View.OnClickListener {

    //APPID和APPKEY
    private static final String FY_APPID = "20181210000246070";
    private static final String FY_APPKEY = "DBWr7jSS86IRTUBF5Zzn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);

        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_font_change;
    }

    @Override
    public void initView() {
        findViewById(Helper.getResId(activity, "ztzh_img_back")).setOnClickListener(this);
//        et_yuanwen
        findViewById(Helper.getResId(activity, "txt_zdjclanguage"));
        findViewById(Helper.getResId(activity, "lin_languagechange"));
//        et_yiwen
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == Helper.getResId(activity, "ztzh_img_back")){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

}