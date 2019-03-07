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
 * QQ号码测吉凶
 */
public class QQHMCJXActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = QQHMCJXActivity.this;
    ImageView qq_img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);

        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_qqhmcjx;
    }

    @Override
    public void initView() {
        qq_img_back = findViewById(R.id.qq_img_back);

        qq_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.qq_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

}
