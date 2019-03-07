package com.cc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.cc.utils.A;
import com.cc.utils.ActivityMan;

/**
 * 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    Activity activity = BaseActivity.this;

    /***是否显示标题栏*/
    private  boolean isshowtitle = true;

    /***是否显示标题栏*/
    private  boolean isshowstate = true;

    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMan.getmInstance().addActivity(this);
        if(!isshowtitle){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if(!isshowstate){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                    WindowManager.LayoutParams. FLAG_FULLSCREEN);
        }
        //设置布局
        setContentView(intiLayout());
        //初始化控件
        initView();
        //设置数据
        initData();
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();

    /**
     * 是否设置标题栏
     *
     * @return
     */
    public void setTitle(boolean ishow) {
        isshowtitle=ishow;
    }

    /**
     * 设置是否显示状态栏
     * @param ishow
     */
    public void setState(boolean ishow) {
        isshowstate=ishow;
    }


    @Override
    protected void onPause() {
        super.onPause();
        A.C_Log(activity, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        A.C_Log(activity, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        A.C_Log(activity, "onResume");
    }

    /**
     * 销魂activity的时候调用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        A.C_Log(activity, "onDestroy");
        ActivityMan.getmInstance().finishActivity(this);
    }
}
