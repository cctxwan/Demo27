package com.cc.activity;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cc.R;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.ActivityMan;

public class OneFragBannerOneActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = OneFragBannerOneActivity.this;
    ImageView nba_img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(false);

        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_one_frag_banner_one;
    }

    @Override
    public void initView() {
        nba_img_back = findViewById(R.id.nba_img_back);

        nba_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.nba_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

}
