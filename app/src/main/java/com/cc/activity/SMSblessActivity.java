package com.cc.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.cc.R;
import com.cc.fragment.SMSblessFragment;

/**
 * 发送接入短信的activity
 * cc
 */
public class SMSblessActivity extends AppCompatActivity {

    TabLayout sms_tabLayout;

    ViewPager sms_viewPager;

    /** tablelayout的title填充的数据 */
    String  titles[] = new String[]{"发送短信", " 节日记录"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsbless);
        initView();
        initData();
    }

    /**
     * 实例化数据
     */
    private void initData() {
        sms_viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                //节日短信frag
                return new SMSblessFragment();
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        //tableyout+viewpager绑定
        sms_tabLayout.setupWithViewPager(sms_viewPager);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        sms_tabLayout = (TabLayout) findViewById(R.id.sms_tabLayout);
        sms_viewPager = (ViewPager) findViewById(R.id.sms_viewpager);
    }
}
