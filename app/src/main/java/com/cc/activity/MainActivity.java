package com.cc.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.R;
import com.cc.db.SQLiteDB;
import com.cc.fragment.FourFragment;
import com.cc.fragment.OneFragment;
import com.cc.fragment.ThreeFragment;
import com.cc.fragment.TwoFragment;
import com.cc.publics.info;

import java.util.HashMap;

/**
 *
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = this;

    /** 底部导航栏 */
    LinearLayout Rel_one, Rel_two, Rel_add, Rel_three, Rel_four;

    //获取底部导航栏的ImageView
    ImageView img_one_bottom, img_two_bottom, img_add_bottom, img_three_bottom, img_four_bottom;

    //未登录
    TextView txt_notlogin;

    /** 第一个fragment */
    public static final int PAGE_COMMON = 0;
    /** 第二个fragment */
    public static final int PAGE_TRANSLUCENT = 1;
    /** 第三个fragment */
    public static final int PAGE_COORDINATOR = 2;
    /** 第四个fragment */
    public static final int PAGE_COLLAPSING_TOOLBAR = 3;

    /** 管理fragment */
    private HashMap<Integer,Fragment> fragments = new HashMap<>();

    //当前activity的fragment控件
    private int fragmentContentId = R.id.fragment_content;

    /** 设置默认的fragment */
    private int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        setTitle(false);
        //判断是否第一次进入app，如果是，弹出隐藏的透明activity进行新人礼包放送
        initFrag();
        // 设置默认的Fragment
        defaultFragment();
        //界面下面的title背景设置
        SelectColor(0);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
//        if(!A.isUserLogin(activity)){
//            //未登录
//            txt_notlogin = findViewById(R.id.txt_notlogin);
//            txt_notlogin.setOnClickListener(this);
//            return;
//        }
        //底部导航栏的父控件
        Rel_one = findViewById(R.id.Rel_one);
        Rel_two = findViewById(R.id.Rel_two);
        Rel_add = findViewById(R.id.Rel_add);
        Rel_three = findViewById(R.id.Rel_three);
        Rel_four = findViewById(R.id.Rel_four);

        img_one_bottom = findViewById(R.id.img_one_bottom);
        img_two_bottom = findViewById(R.id.img_two_bottom);
        img_add_bottom = findViewById(R.id.img_add_bottom);
        img_three_bottom = findViewById(R.id.img_three_bottom);
        img_four_bottom = findViewById(R.id.img_four_bottom);


        Rel_one.setOnClickListener(this);
        Rel_two.setOnClickListener(this);
        Rel_add.setOnClickListener(this);
        Rel_three.setOnClickListener(this);
        Rel_four.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //执行一个子线程去执行保存操作
        MyThread myThread = new MyThread();
        new Thread(myThread).start();
    }

    /**
     * 保存
     */
    class MyThread implements Runnable{
        @Override
        public void run() {
            try {
                if(isFirstInAPPMain()){
                    //新人共享页面
                    ToGuidanceActivity();
                    //创建数据库
                    SQLiteDB.getInstance(activity);
                    info.saveDeviceInfo(activity);
                    info.saveInformation(activity);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化fragment
     */
    private void initFrag() {
        fragments.put(PAGE_COMMON, new OneFragment());
        fragments.put(PAGE_TRANSLUCENT, new TwoFragment());
        fragments.put(PAGE_COORDINATOR, new ThreeFragment());
        fragments.put(PAGE_COLLAPSING_TOOLBAR, new FourFragment());
    }

    private void defaultFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId,fragments.get(PAGE_COMMON));
        currentTab = PAGE_COMMON;
        ft.commit();
    }

    /**
     * 当页面选中时改变当前的导航栏蓝色和图片的状态
     * @param position 当前页面
     */
    public void SelectColor(int position) {
        if(position == 0){
            //给底部到导航栏的image更换图片
            img_one_bottom.setImageResource(R.mipmap.xiao);
            img_two_bottom.setImageResource(R.mipmap.ws);
            img_three_bottom.setImageResource(R.mipmap.ws);
            img_four_bottom.setImageResource(R.mipmap.nu);

            //给底部导航栏更换背景色
            Rel_one.setBackgroundResource(R.color.blue);
            Rel_two.setBackgroundResource(R.color.white);
            Rel_three.setBackgroundResource(R.color.white);
            Rel_four.setBackgroundResource(R.color.white);
        } else if (position == 1){
            //给底部到导航栏的image更换图片
            img_one_bottom.setImageResource(R.mipmap.ws);
            img_two_bottom.setImageResource(R.mipmap.xiao);
            img_three_bottom.setImageResource(R.mipmap.ws);
            img_four_bottom.setImageResource(R.mipmap.nu);

            //给底部导航栏更换背景色
            Rel_one.setBackgroundResource(R.color.white);
            Rel_two.setBackgroundResource(R.color.blue);
            Rel_three.setBackgroundResource(R.color.white);
            Rel_four.setBackgroundResource(R.color.white);
        } else if (position == 2){
            //给底部到导航栏的image更换图片
            img_one_bottom.setImageResource(R.mipmap.nu);
            img_two_bottom.setImageResource(R.mipmap.ws);
            img_three_bottom.setImageResource(R.mipmap.xiao);
            img_four_bottom.setImageResource(R.mipmap.ws);

            //给底部导航栏更换背景色
            Rel_one.setBackgroundResource(R.color.white);
            Rel_two.setBackgroundResource(R.color.white);
            Rel_three.setBackgroundResource(R.color.blue);
            Rel_four.setBackgroundResource(R.color.white);
        } else if (position == 3){
            //给底部到导航栏的image更换图片
            img_one_bottom.setImageResource(R.mipmap.nu);
            img_two_bottom.setImageResource(R.mipmap.ws);
            img_three_bottom.setImageResource(R.mipmap.ws);
            img_four_bottom.setImageResource(R.mipmap.xiao);

            //给底部导航栏更换背景色
            Rel_one.setBackgroundResource(R.color.white);
            Rel_two.setBackgroundResource(R.color.white);
            Rel_three.setBackgroundResource(R.color.white);
            Rel_four.setBackgroundResource(R.color.blue);
        }
    }

    /**
     * 点击切换下部按钮
     * @param page
     */
    private void changeTab(int page) {
        //默认的currentTab == 当前的页码，不做任何处理
        if (currentTab == page) {
            return;
        }

        //获取fragment的页码
        Fragment fragment = fragments.get(page);
        //fragment事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //如果该Fragment对象被添加到了它的Activity中，那么它返回true，否则返回false。
        //当前activity中添加的不是这个fragment
        if(!fragment.isAdded()){
            //所以将他加进去
            ft.add(fragmentContentId,fragment);
        }
        //隐藏当前currentTab的
        ft.hide(fragments.get(currentTab));
        //显示现在page的
        ft.show(fragments.get(page));
        //设置当前currentTab底部的状态
        SelectColor(currentTab);
        //当前显示的赋值给currentTab
        currentTab = page;
        //设置当前currentTab底部的状态
        SelectColor(currentTab);
        //activity被销毁？  ！否
        if (!this.isFinishing()) {
            //允许状态丢失
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 所有的控件在这里进行点击（单击）事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.Rel_one){
            changeTab(PAGE_COMMON);
        } else if (temdId == R.id.Rel_two){
            changeTab(PAGE_TRANSLUCENT);
        } else if (temdId == R.id.Rel_add){
            startActivity(new Intent(activity, SMSblessActivity.class));
        }else if (temdId == R.id.Rel_three){
            changeTab(PAGE_COORDINATOR);
        } else if (temdId == R.id.Rel_four){
            changeTab(PAGE_COLLAPSING_TOOLBAR);
        } else if (temdId == R.id.txt_notlogin){
            //登录去

        }
    }



    /**
     * 判断是否第一次进入app的Main界面
     * @return
     */
    public boolean isFirstInAPPMain() throws PackageManager.NameNotFoundException {
        //获取包信息
        PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
        //当前app包的版本号（XML配置中的version）
        int currentVersion = info.versionCode;
        //本地存储文件
        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
        //将当前获取到的版本号存储起来，因为第一次运行，所以存入0
        int lastVersion = sf.getInt("/data/xml/firstinappmain.xml", 0);
        //当前版本号大于之前版本号说明该版本号第一次进入，故加载welcome页面启动动画效果
        if(currentVersion > lastVersion){
            //因为第一次进入，所以肯定会执行这段代码，执行之后，下次进入就应该将将0改为当前版本存储
            sf.edit().putInt("/data/xml/firstinappmain.xml", currentVersion).commit();
            return true;
        }
        return false;
    }

    /**
     * 第一次进入引导透明页
     */
    private void ToGuidanceActivity() {
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        startActivity(intent);
    }

}
