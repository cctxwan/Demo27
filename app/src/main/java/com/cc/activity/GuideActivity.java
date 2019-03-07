package com.cc.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cc.R;
import com.cc.adapter.ViewPagerAdapter;
import com.cc.fragment.GuideFragment;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.cc.utils.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导界面
 * 通过sf判断是否为引导页
 */
public class GuideActivity extends BaseActivity {

    /** 上下文 */
    GuideActivity activity = this;

    /** 指示器 */
    LinearLayout guide_zsq;

    /** viewpaper */
    ViewPager guide_viewpaper;

    // 要申请的权限
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    /** 适配器 */
    PagerAdapter pagerAdapter;

    /** fragments界面 */
    List<Fragment> fragments = new ArrayList<>();

    /**save*/
    SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        setTitle(false);
        //隐藏状态栏
        setState(false);
        StatusBarCompat.translucentStatusBar(this, false);
        try {
            if(isFirstInAPPZoom()){
                initView();
                initFragment();
                //请求打开权限
                checkPermissions();
            }else{
                //跳往主界面
                toMainActivity();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        guide_viewpaper = findViewById(Helper.getResId(activity, "guide_viewpaper"));
        guide_zsq = findViewById(Helper.getResId(activity, "guide_zsq"));
    }

    @Override
    public void initData() {

    }

    /**
     * 跳往主页面
     */
    void toMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //前往主界面的同时结束掉这个act
        ActivityMan.getmInstance().finishActivity(activity);
    }

    /**
     * 是否第一次进入app
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public boolean isFirstInAPPZoom() throws PackageManager.NameNotFoundException {
        //获取包信息
        PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
        //当前app包的版本号（XML配置中的version）
        int currentVersion = info.versionCode;
        //本地存储文件
        sf = PreferenceManager.getDefaultSharedPreferences(activity);
        //将当前获取到的版本号存储起来，因为第一次运行，所以存入0
        int lastVersion = sf.getInt("/data/xml/firstinappzoom.xml", 0);
        //当前版本号大于之前版本号说明该版本号第一次进入，故加载welcome页面启动动画效果
        if(currentVersion > lastVersion){
            //因为第一次进入，所以肯定会执行这段代码，执行之后，下次进入就应该将将0改为当前版本存储
            sf.edit().putInt("/data/xml/firstinappzoom.xml", currentVersion).commit();
            return true;
        }
        return false;
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        //通过循环加载fragment界面并且传入当前的index值
        for (int i = 0; i < 4; i++){
            GuideFragment guideFragment = new GuideFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            guideFragment.setArguments(bundle);
            //添加fragment
            fragments.add(guideFragment);
        }
        //实例化适配器将fragments界面传入适配器
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        //加载适配器
        guide_viewpaper.setAdapter(pagerAdapter);
        //实现监听（页面改变监听）
        guide_viewpaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //通过循环赋值给当前下标下的指示器为选中或未选中
                for (int i = 0; i < fragments.size(); i++){
                    guide_zsq.getChildAt(i).setBackgroundResource(
                            position == i ? Helper.getResDraw(activity, "zsq_selected")
                                    : Helper.getResDraw(activity, "zsq_not_selected"));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /** 设置指示器小圆点 */
        initZSQ();
    }

    /**
     * 小圆点
     */
    private void initZSQ() {
        //计算指示器小圆点的大小
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10f, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
        for (int i = 0; i <fragments.size(); i++){
            View view = new View(this);
            lp.setMargins(5, 5, 5, 5);
            view.setId(i);
            view.setBackgroundResource(
                    i == 0 ? Helper.getResDraw(activity, "zsq_selected")
                            : Helper.getResDraw(activity, "zsq_not_selected"));
            view.setLayoutParams(lp);
            guide_zsq.addView(view, i);
        }
    }

    /**
     * 权限检查
     */
    private void checkPermissions() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                startRequestPermission();
            }
        }
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }


    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        goToAppSetting();
                    } else
                        finish();
                } else {
                    A.C_Log(activity, "权限获取成功");
                }
            }
        }
    }


    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    goToAppSetting();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
