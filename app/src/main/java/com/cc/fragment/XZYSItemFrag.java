package com.cc.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.R;
import com.cc.adapter.XZYSPagerAdapter;
import com.cc.utils.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 星座运势的具体界面
 */
public class XZYSItemFrag extends Fragment {

    public static String title = "";
    View view;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    TabLayout xzys_tabLayout;

    ViewPager xzys_viewpager;

    List<String> titles = Arrays.asList("今天", "明天", "本周", "本月", "今年");
    static List<Fragment> fragments = new ArrayList<>();
    static XZYSPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.xzys_item, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();

        A.C_Log(getActivity(), title);
        handler.post(getDatas);
    }

    private void initData() {
        fragments.add(new XZYS_JT_Fragment());
        fragments.add(new XZYS_MT_Fragment());
        fragments.add(new XZYS_BZ_Fragment());
        fragments.add(new XZYS_BY_Fragment());
        fragments.add(new XZYS_JN_Fragment());

        adapter = new XZYSPagerAdapter(getChildFragmentManager(), getActivity(), fragments, titles);
        xzys_viewpager.setAdapter(adapter);
        xzys_tabLayout.setupWithViewPager(xzys_viewpager);//此方法就是让tablayout和ViewPager联动
    }

    private void initView() {
        xzys_tabLayout = view.findViewById(R.id.xzys_tabLayout);
        xzys_viewpager = view.findViewById(R.id.xzys_viewpager);
    }

    Runnable getDatas = new Runnable() {
        @Override
        public void run() {

        }
    };

    /**
     * 传值
     * @param item_title
     * @return
     */
    public static XZYSItemFrag getInstance(String item_title){
        title = item_title;
        XZYSItemFrag frag = new XZYSItemFrag();
        return frag;
    }

}
