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
 * 标准电码查询
 */
public class StandardCodeActivity extends BaseActivity implements View.OnClickListener  {

    static final String WX_APPKEY = "f0bd63aaba2a63f7cb438647af815744";

    Activity activity = StandardCodeActivity.this;
    ImageView sc_img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_standard_code;
    }

    @Override
    public void initView() {
        sc_img_back = findViewById(R.id.sc_img_back);

        sc_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.sc_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }
}
