package com.cc.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.R;
import com.cc.fragment.XZYSItemFrag;
import com.cc.model.XZ_Icon;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.ActivityMan;

import java.util.ArrayList;
import java.util.List;

/**
 * 星座运势
 */
public class HoroscopeActivity extends BaseActivity  implements View.OnClickListener {

    public static String HOR_APPKEY = "578ad7305684ed6d678ffcdcdca3760b";
    Activity activity = HoroscopeActivity.this;
    ImageView xzys_img_back, img_xzkzq;
    TextView txt_selected_xz;
    LinearLayout lin_zk;
    RelativeLayout rel_xz;

    boolean isZhanKai = false;

    public static List<XZ_Icon> imgs = new ArrayList<>();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    PopupWindow popupWindow;
    ViewPager xz_viewpager;
    List<XZYSItemFrag> itemFragList = new ArrayList<>();
    static FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_horoscope;
    }

    @Override
    public void initView() {
        xzys_img_back = findViewById(R.id.xzys_img_back);
        txt_selected_xz = findViewById(R.id.txt_selected_xz);
        lin_zk = findViewById(R.id.lin_zk);
        rel_xz = findViewById(R.id.rel_xz);
        img_xzkzq = findViewById(R.id.img_xzkzq);
        xz_viewpager = findViewById(R.id.xz_viewpager);

        xzys_img_back.setOnClickListener(this);
        lin_zk.setOnClickListener(this);
    }

    @Override
    public void initData() {
        imgs.add(new XZ_Icon(R.mipmap.baiyang, getResources().getString(R.string.byz)));
        imgs.add(new XZ_Icon(R.mipmap.jinniu, getResources().getString(R.string.jnz)));
        imgs.add(new XZ_Icon(R.mipmap.shuangzi, getResources().getString(R.string.shuangzz)));
        imgs.add(new XZ_Icon(R.mipmap.juxie, getResources().getString(R.string.jxz)));
        imgs.add(new XZ_Icon(R.mipmap.shizi, getResources().getString(R.string.shizz)));
        imgs.add(new XZ_Icon(R.mipmap.chunv, getResources().getString(R.string.cnz)));
        imgs.add(new XZ_Icon(R.mipmap.tianping, getResources().getString(R.string.tcz)));
        imgs.add(new XZ_Icon(R.mipmap.tianxie, getResources().getString(R.string.txz)));
        imgs.add(new XZ_Icon(R.mipmap.sheshou, getResources().getString(R.string.ssz)));
        imgs.add(new XZ_Icon(R.mipmap.mojie, getResources().getString(R.string.mjz)));
        imgs.add(new XZ_Icon(R.mipmap.shuiping, getResources().getString(R.string.spz)));
        imgs.add(new XZ_Icon(R.mipmap.shuangyu, getResources().getString(R.string.syz)));

        txt_selected_xz.setText(getResources().getString(R.string.tcz));

        initFrag(getResources().getString(R.string.tcz));
    }

    /**
     * 初始化frag
     */
    void initFrag(final String title){
        XZYSItemFrag instance = XZYSItemFrag.getInstance(title);
        itemFragList.clear();
        itemFragList.add(instance);
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return itemFragList.get(position);
            }

            @Override
            public int getCount() {
                return itemFragList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title;
            }
        };

        xz_viewpager.setAdapter(fragmentPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.xzys_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        } else if (temdId == R.id.lin_zk){
            if(isZhanKai){
                isZhanKai = false;
                popupWindow.dismiss();
                img_xzkzq.setImageResource(R.mipmap.zhankai);
            }else {
                isZhanKai = true;
                //打开一个PopUpWindow，用于展开星座列表
                openPopUpWindow(rel_xz);
                img_xzkzq.setImageResource(R.mipmap.shouqi);
            }
        }else if(temdId == R.id.byz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.byz));
            initFrag(getResources().getString(R.string.byz));
        }else if(temdId == R.id.jnz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.jnz));
            initFrag(getResources().getString(R.string.jnz));
        }else if(temdId == R.id.shuangzz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.shuangzz));
            initFrag(getResources().getString(R.string.shuangzz));
        }else if(temdId == R.id.jxz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.jxz));
            initFrag(getResources().getString(R.string.jxz));
        }else if(temdId == R.id.shizz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.shizz));
            initFrag(getResources().getString(R.string.shizz));
        }else if(temdId == R.id.cnz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.cnz));
            initFrag(getResources().getString(R.string.cnz));
        }else if(temdId == R.id.tcz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.tcz));
            initFrag(getResources().getString(R.string.tcz));
        }else if(temdId == R.id.txz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.txz));
            initFrag(getResources().getString(R.string.txz));
        }else if(temdId == R.id.ssz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.ssz));
            initFrag(getResources().getString(R.string.ssz));
        }else if(temdId == R.id.mjz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.mjz));
            initFrag(getResources().getString(R.string.mjz));
        }else if(temdId == R.id.spz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.spz));
            initFrag(getResources().getString(R.string.spz));
        }else if(temdId == R.id.syz){
            popupWindow.dismiss();
            isZhanKai = false;
            img_xzkzq.setImageResource(R.mipmap.zhankai);
            txt_selected_xz.setText(getResources().getString(R.string.syz));
            initFrag(getResources().getString(R.string.syz));
        }
    }

    private void openPopUpWindow(RelativeLayout rel_xz) {
        View view = LayoutInflater.from(activity).inflate(R.layout.showxzlist, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.findViewById(R.id.byz).setOnClickListener(this);
        view.findViewById(R.id.jnz).setOnClickListener(this);
        view.findViewById(R.id.shuangzz).setOnClickListener(this);
        view.findViewById(R.id.jxz).setOnClickListener(this);
        view.findViewById(R.id.shizz).setOnClickListener(this);
        view.findViewById(R.id.cnz).setOnClickListener(this);
        view.findViewById(R.id.tcz).setOnClickListener(this);
        view.findViewById(R.id.txz).setOnClickListener(this);
        view.findViewById(R.id.ssz).setOnClickListener(this);
        view.findViewById(R.id.mjz).setOnClickListener(this);
        view.findViewById(R.id.spz).setOnClickListener(this);
        view.findViewById(R.id.syz).setOnClickListener(this);
        popupWindow.showAsDropDown(rel_xz);
    }

}
