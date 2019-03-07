package com.cc.activity;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cc.R;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.ActivityMan;

/**
 * 足球联赛
 */
public class FootBallActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = FootBallActivity.this;
    ImageView fb_img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_foot_ball;
    }

    @Override
    public void initView() {
        fb_img_back = findViewById(R.id.fb_img_back);

        fb_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.fb_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

}
