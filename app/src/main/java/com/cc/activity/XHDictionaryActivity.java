package com.cc.activity;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cc.R;
import com.cc.adapter.XHItemPagerAdapter;
import com.cc.fragment.XHItemBSFragment;
import com.cc.fragment.XHItemPYFragment;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.ActivityMan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 新华字典
 */
public class XHDictionaryActivity extends BaseActivity implements View.OnClickListener {

    public static String XH_APPKEY = "de15ecbc5d203846aff49d71ea8dc31e";
    Activity activity = XHDictionaryActivity.this;
    ImageView xh_img_back;

    List<String> titles = Arrays.asList("部首", "拼音");
    static List<Fragment> fragments = new ArrayList<>();
    static XHItemPagerAdapter adapter;

    ViewPager xh_viewpager;
    TabLayout xh_tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);

        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_xhdictionary;
    }

    @Override
    public void initView() {
        xh_img_back = findViewById(R.id.xh_img_back);
        xh_tabLayout = findViewById(R.id.xh_tabLayout);
        xh_viewpager = findViewById(R.id.xh_viewpager);

        xh_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        fragments.add(new XHItemBSFragment());
        fragments.add(new XHItemPYFragment());
        adapter = new XHItemPagerAdapter(getSupportFragmentManager(), activity, fragments, titles);
        xh_viewpager.setAdapter(adapter);
        xh_tabLayout.setupWithViewPager(xh_viewpager);//此方法就是让tablayout和ViewPager联动
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.xh_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }
}
