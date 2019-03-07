package com.cc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cc.R;
import com.cc.activity.OneFragBannerOneActivity;
import com.cc.adapter.ViewPagerAdapter;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.Helper;
import com.cc.utils.StatusBarUtil;
import com.cc.view.MarqueeView;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import java.util.ArrayList;
import java.util.List;

/**

 * Created by admin on 2018/4/10.
 */
public class OneFragment extends Fragment implements XBanner.XBannerAdapter, XBanner.OnItemClickListener, MarqueeView.onItemClickListener {

    private boolean isHide = false;

    /** 轮播图 */
    XBanner xBanner;

    /** 轮播图图片资源加载器 */
    List<String> imgesUrl = new ArrayList<>();

    /** 轮播图文字资源加载器 */
    List<String> textUrl = new ArrayList<>();

    private int mOffset = 0;
    private int mScrollY = 0;

    //Handler处理一些耗时操作，放在子线程中
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    MarqueeView frag_one_txt_marquee;

    String[] contentArray = new String[] {
            "游戏打造虚拟故宫&东京持刀砍人事件",
            "何洁离婚案开庭&男子掐死临盆妻子",
            "军机零件掉幼儿园&赵丽颖悼念粉丝",
            "欧豪深夜删微博&饭店倒闭当菜窖",
            "周杰伦现身学校&赵薇澄清传闻"};

    ViewPager frag_one_viewpaper;
    LinearLayout frag_one_zsq;

    /** fragments界面 */
    List<Fragment> fragments = new ArrayList<>();

    ViewPagerAdapter viewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        initView(view);
        addData();
        setAdapter();
        return view;
    }

    private void setAdapter() {
        xBanner.setmAdapter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //title
        final Toolbar toolbar = view.findViewById(R.id.frag_one_toolbar);
        //下拉刷新顶部的背景图片
        final View frag_one_parallax = view.findViewById(R.id.frag_one_parallax);
        //标题栏上的按钮
        final View fragment_one_bt_BarLayout = view.findViewById(R.id.fragment_one_bt_BarLayout);
        //sc
        final NestedScrollView frag_one_scrollView = view.findViewById(R.id.frag_one_scrollView);
        //refresh
        final RefreshLayout frag_one_refreshLayout = view.findViewById(R.id.frag_one_refreshLayout);



        //设置refreshLayout的一些操作
        //第一次进来自动刷新
        frag_one_refreshLayout.autoRefresh();

        //越界回弹
        frag_one_refreshLayout.setEnableOverScrollBounce(false);

        //在刷新或者加载的时候不允许操作视图
        frag_one_refreshLayout.setDisableContentWhenRefresh(true);
        frag_one_refreshLayout.setDisableContentWhenLoading(true);

        //监听列表在滚动到底部时触发加载事件（默认true）
        frag_one_refreshLayout.setEnableAutoLoadmore(false);


        /**
         * 正在下拉刷新数据中
         */
        frag_one_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                A.C_Log(getActivity(), "555");
                xBanner.stopAutoPlay();
                frag_one_refreshLayout.finishRefresh(2000);
                xBanner.startAutoPlay();
            }
        });

        /**
         * 正在上拉加载数据中
         */
        frag_one_refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                A.C_Log(getActivity(), "444");
                xBanner.stopAutoPlay();
                frag_one_refreshLayout.finishLoadmore(2000);
                xBanner.startAutoPlay();
            }
        });

        /**
         * 监听sc的滑动
         */
        frag_one_scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(getActivity(), R.color.cyan)&0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                A.C_Log(getActivity(), "333");
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    fragment_one_bt_BarLayout.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    frag_one_parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });


        //状态栏透明和间距处理
        StatusBarUtil.immersive(getActivity());
        StatusBarUtil.setPaddingSmart(getActivity(), toolbar);
        fragment_one_bt_BarLayout.setAlpha(0);
        toolbar.setBackgroundColor(0);
    }

    /**
     * 获取网络数据
     */
    private void addData() {
        //跑马灯加载
        frag_one_txt_marquee.setTextArray(contentArray);

        //轮播图加载
        imgesUrl.add("http://bpic.588ku.com/back_pic/03/70/72/5257b6c12d89875.jpg");
        imgesUrl.add("http://bpic.588ku.com/back_pic/00/13/15/08564453d0190aa.jpg");
        imgesUrl.add("http://bpic.588ku.com/back_pic/00/14/65/3256657136926fa.jpg");
        imgesUrl.add("http://bpic.588ku.com/back_pic/03/72/92/6657b9a240d3d1f.jpg");
        textUrl.add("1");
        textUrl.add("2");
        textUrl.add("3");
        textUrl.add("4");
        xBanner.setData(imgesUrl, textUrl);

        //横向的frag加载
        //通过循环加载fragment界面并且传入当前的index值
        for (int i = 0; i < 2; i++){
            HorizontalFragment horizontalFragment = new HorizontalFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            horizontalFragment.setArguments(bundle);
            //添加fragment
            fragments.add(horizontalFragment);
        }
        //实例化适配器将fragments界面传入适配器
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        //加载适配器
        frag_one_viewpaper.setAdapter(viewPagerAdapter);
        //实现监听（页面改变监听）
        frag_one_viewpaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //通过循环赋值给当前下标下的指示器为选中或未选中
                for (int i = 0; i < fragments.size(); i++){
                    frag_one_zsq.getChildAt(i).setBackgroundResource(
                            position == i ? Helper.getResDraw(getActivity(), "zsq_selected")
                                    : Helper.getResDraw(getActivity(), "zsq_not_selected"));
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
                7f, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
        for (int i = 0; i <fragments.size(); i++){
            View view = new View(getActivity());
            lp.setMargins(5, 5, 5, 5);
            view.setId(i);
            view.setBackgroundResource(
                    i == 0 ? Helper.getResDraw(getActivity(), "zsq_selected")
                            : Helper.getResDraw(getActivity(), "zsq_not_selected"));
            view.setLayoutParams(lp);
            frag_one_zsq.addView(view, i);
        }
    }

    private void initView(View view) {
        //轮播图
        xBanner = view.findViewById(R.id.frag_one_xbanner);
        xBanner.setPageTransformer(Transformer.Accordion);
        xBanner.startAutoPlay();
        //跑马灯
        frag_one_txt_marquee = view.findViewById(R.id.frag_one_txt_marquee);
        frag_one_txt_marquee.setOnItemClickListener(this);
        //横向的fragment
        frag_one_viewpaper = view.findViewById(R.id.frag_one_viewpaper);
        frag_one_zsq = view.findViewById(R.id.frag_one_zsq);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.translucentStatusBar(getActivity(), isHide);
            isHide = !isHide;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                frag_one_txt_marquee.resume();
            }
        }, 1000);
        xBanner.startAutoPlay();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        xBanner.stopAutoPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        frag_one_txt_marquee.destory();
    }

    @Override
    public void loadBanner(XBanner banner, Object model, View view, int position) {
        Glide
            .with(getActivity())
            .load(imgesUrl.get(position))
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .into((ImageView) view);
    }

    /**
     * 轮播图的
     * @param banner
     * @param position
     */
    @Override
    public void onItemClick(XBanner banner, int position) {
        if(position == 0){
            A.C_Log(getActivity(), "开始执行动画");
            startActivity(new Intent(getActivity(), OneFragBannerOneActivity.class));
        }
    }

    /**
     * 跑马灯的
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        A.C_Log(getActivity(), "跑马灯=" + contentArray[position]);
    }
}
