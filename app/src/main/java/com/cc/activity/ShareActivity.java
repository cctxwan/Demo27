package com.cc.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cc.R;
import com.cc.utils.ActivityMan;

public class ShareActivity extends BaseActivity implements View.OnClickListener {

    ImageView img_guidance_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        setTitle(false);
        //隐藏状态栏
        setState(false);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_share;
    }

    @Override
    public void initView() {
        img_guidance_close = (ImageView) findViewById(R.id.img_guidance_close);

        img_guidance_close.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    /**
     * 获取到控件的点击事件在这里执行
     * @param v
     */
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_guidance_close){
            ActivityMan.getmInstance().finishActivity(ShareActivity.class);
        }
    }

}
