package com.cc.activity;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.R;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.cc.utils.Helper;

public class ChangePSDActivity extends BaseActivity implements View.OnClickListener {

    Handler handler = new Handler();

    Activity activity = ChangePSDActivity.this;

    ImageView changepsd_img_back, img_old_getpass, img_new_getpass, img_com_getpass;

    EditText et_old_psd, et_new_psd, et_com_psd;

    TextView psd_txt_com, txt_forget_psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_change_psd;
    }

    @Override
    public void initView() {
        changepsd_img_back = findViewById(R.id.changepsd_img_back);

        et_old_psd = findViewById(R.id.et_old_psd);
        img_old_getpass = findViewById(R.id.img_old_getpass);

        et_new_psd = findViewById(R.id.et_new_psd);
        img_new_getpass = findViewById(R.id.img_new_getpass);

        et_com_psd = findViewById(R.id.et_com_psd);
        img_com_getpass = findViewById(R.id.img_com_getpass);

        psd_txt_com = findViewById(R.id.psd_txt_com);
        txt_forget_psd = findViewById(R.id.txt_forget_psd);


        changepsd_img_back.setOnClickListener(this);
        img_old_getpass.setOnClickListener(this);
        img_new_getpass.setOnClickListener(this);
        img_com_getpass.setOnClickListener(this);
        psd_txt_com.setOnClickListener(this);
        txt_forget_psd.setOnClickListener(this);
    }

    @Override
    public void initData() {
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.changepsd_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }else if(temdId == R.id.psd_txt_com){
            String old_psd = et_old_psd.getText().toString().trim();
            String new_psd = et_new_psd.getText().toString().trim();
            String com_psd = et_com_psd.getText().toString().trim();
            A.C_Log(activity, "old_psd=" + old_psd + ",new_psd=" + new_psd + ",com_psd=" + com_psd);
        }else if(temdId == R.id.txt_forget_psd){
            A.C_Log(activity, activity.getResources().getString(Helper.getResStr(activity, "forget_psd")));

        }
    }
}
