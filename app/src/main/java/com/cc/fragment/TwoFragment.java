package com.cc.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cc.R;
import com.cc.activity.AddTabItemActivity;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2018/4/10.
 */

public class TwoFragment extends Fragment implements View.OnClickListener {

    //当前所在的fragment的值，默认为0
    public static int DEFAULTFRAGMENT = 0;

//    private int mOffset = 0;
//    private int mScrollY = 0;

    static ViewPager viewPager;
    TabLayout frag_two_tabLayout;
    PopupWindow popupWindow;
    ImageView frag_two_img_add;

    //添加头部item布局信息
    List<String> titles = Arrays.asList("头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚");

    //每一个头部item所对应一个item
    static List<Frag_Two_Frag_Item> frag_two_frag_items = new ArrayList<>();
    //适配器
    static FragmentPagerAdapter fragmentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //获取标题栏上的信息
        //title
//        final Toolbar toolbar = view.findViewById(R.id.frag_two_toolbar);
//        //标题栏上的按钮
//        final View fragment_two_bt_BarLayout = view.findViewById(R.id.fragment_two_bt_BarLayout);
        //refresh
        final RefreshLayout frag_two_refreshLayout = view.findViewById(R.id.frag_two_refreshLayout);


        //越界回弹
        frag_two_refreshLayout.setEnableOverScrollBounce(false);

        //在刷新或者加载的时候不允许操作视图
        frag_two_refreshLayout.setDisableContentWhenRefresh(true);
        frag_two_refreshLayout.setDisableContentWhenLoading(true);

        //监听列表在滚动到底部时触发加载事件（默认true）
        frag_two_refreshLayout.setEnableAutoLoadmore(false);

        //设置自定义Header
        frag_two_refreshLayout.setHeaderHeight(50);
        frag_two_refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        //设置自定义Footer
        frag_two_refreshLayout.setFooterHeight(50);
        frag_two_refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));

        /**
         * 正在下拉刷新数据中
         */
        frag_two_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                A.C_Log(getActivity(), "555");
                frag_two_refreshLayout.finishRefresh(2000);
                Frag_Two_Frag_Item.adapter.notifyDataSetChanged();
            }
        });

        /**
         * 正在上拉加载数据中
         */
        frag_two_refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                A.C_Log(getActivity(), "444");
                frag_two_refreshLayout.finishLoadmore(2000);
                Frag_Two_Frag_Item.adapter.notifyDataSetChanged();
            }
        });

        /**
         * sf的事件监听
         */
        frag_two_refreshLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {
            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
            }

            @Override
            public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {
            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {
            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {
            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }

            @Override
            public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
                A.C_Log(getActivity(), "" + oldState + "-------" + newState);
                if(newState == RefreshState.RefreshFinish){
                    A.C_Log(getActivity(), "刷新完成");
                    showPopup(frag_two_tabLayout);

                }
                if(oldState == RefreshState.RefreshFinish){
                    popupWindow.dismiss();
                }
            }
        });


        StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(getContext(), R.color.cyan));
        
        initView(view);
        initData();

        //通过适配器加载
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(9);

        //必须与viewpager绑定，否则效果就看不出来
        frag_two_tabLayout.setupWithViewPager(viewPager);
        frag_two_tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        frag_two_tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



    }


    /**
     * 自定义Popupwindow，在刷新成功和加载成功之后弹出
     */
    private void showPopup(View parent) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.showtoast, null);
        TextView textView = view.findViewById(R.id.show_toast);
        textView.setText("又为您找到了10条数据");
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        popupWindow.setAnimationStyle(R.style.MyPopupWindow);
        popupWindow.showAsDropDown(parent);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        for(String title : titles){
            Frag_Two_Frag_Item instance = Frag_Two_Frag_Item.getInstance(title);
            frag_two_frag_items.add(instance);
        }

        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return frag_two_frag_items.get(position);
            }

            @Override
            public int getCount() {
                return frag_two_frag_items.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        };
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.frag_two_viewpager);
        frag_two_tabLayout = view.findViewById(R.id.frag_two_tabLayout);
        frag_two_img_add = view.findViewById(R.id.frag_two_img_add);

        frag_two_img_add.setOnClickListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                A.C_Log(getActivity(), "------->position=" + position);
            }

            @Override
            public void onPageSelected(int position) {
                A.C_Log(getActivity(), "onPageSelected----> + position=" + position);
                DEFAULTFRAGMENT = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                A.C_Log(getActivity(), "onPageScrollStateChanged");
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(getContext(), R.color.cyan));
        }
    }

    /**
     * 设置tablayout的指示器宽度
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    @Deprecated
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.frag_two_img_add){
            startActivity(new Intent(getActivity(), AddTabItemActivity.class));
        }
    }

    /**
     * 将选中的值传进来并显示
     * @param SELECTEDFRAGMENT
     */
    public static void getSelectFrag(int SELECTEDFRAGMENT) {
        viewPager.setCurrentItem(SELECTEDFRAGMENT, false);
    }
}