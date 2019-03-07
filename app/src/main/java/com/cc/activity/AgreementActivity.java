package com.cc.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.cc.R;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.ActivityMan;
import com.cc.utils.Helper;

public class AgreementActivity extends BaseActivity {

    Activity activity = AgreementActivity.this;

    ProgressBar pb;

    WebView web_yhxy;

    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_agreement;
    }

    @Override
    public void initView() {
        img_back = findViewById(Helper.getResId(activity, "img_back"));

        pb = findViewById(Helper.getResId(activity, "pb"));
        web_yhxy = findViewById(Helper.getResId(activity, "web_yhxy"));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    public void initData() {
        web_yhxy.loadUrl("https://www.csdn.net/nav/mobile");

        web_yhxy.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if(newProgress==100){
                    pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        web_yhxy.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * back
     */
    private void back() {
        Intent intent = new Intent(activity, RegisterActivity.class);
        startActivity(intent);
        ActivityMan.getmInstance().finishActivity(activity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            back();
        }
        return false;
    }

}
