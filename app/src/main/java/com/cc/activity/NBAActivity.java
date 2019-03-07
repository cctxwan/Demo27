package com.cc.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.adapter.NBATeamInfoAdapter;
import com.cc.adapter.NBATeamSCAdapter;
import com.cc.fragment.NBA_Notbegin_Fragment;
import com.cc.fragment.NBA_ing_Fragment;
import com.cc.fragment.NBA_over_Fragment;
import com.cc.model.NBASCInfo;
import com.cc.model.NBATeamInfo;
import com.cc.model.setNBASC_Info;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.cc.view.GridDividerItemDecoration;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * NBA赛事
 */
public class NBAActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = NBAActivity.this;
    ImageView nba_img_back;

    /** NBA导航栏 */
    TextView txt_about, txt_nba_notbegin, txt_nba_ing, txt_nba_over;

    /** 第一个fragment */
    public static final int PAGE_COMMON = 0;
    /** 第二个fragment */
    public static final int PAGE_TRANSLUCENT = 1;
    /** 第三个fragment */
    public static final int PAGE_COORDINATOR = 2;

    /** 管理fragment */
    private HashMap<Integer,Fragment> fragments = new HashMap<>();

    //当前activity的fragment控件
    private int fragmentContentId = R.id.fragment_nba_content;

    /** 设置默认的fragment */
    private int currentTab;

    PopupWindow pop_bottom;
    PopupWindow pop_vs;
    PopupWindow pop_sc;
    PopupWindow pop_teamsc;

    //球队对战的et
    EditText et_team_zhu, et_team_ke;

    //共用字段
    public static String ZHU = "ZHU";
    public static String KE = "KE";

    RelativeLayout rel_top;

    //key
    public static final String NBA_APPKEY = "07136fd874cfa29493d3a21562d00395";

    //标识默认为false
    public static boolean isVSshow = false;
    public static boolean isSCshow = false;
    public static boolean isAboutshow = false;

    //球队信息加载适配器
    NBATeamInfoAdapter adapter;
    NBATeamSCAdapter scAdapter;

    //赛程信息球队的数据
    List<NBATeamInfo> datas = new ArrayList<>();
    List<NBASCInfo> sc_datas = new ArrayList<>();

    //获取球队名称
    private String et_Team = "";

    public static int SUCC_CODE = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE){
                A.C_Log(activity, "请求成功");
                open_TeamSC_popwindows();
            }else{
                Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);

        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));

        initFrag();
        // 设置默认的Fragment
        defaultFragment();
        SelectColor(1);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_nba;
    }

    /**
     * 默认加载的frag
     */
    private void defaultFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId, fragments.get(PAGE_TRANSLUCENT));
        currentTab = PAGE_TRANSLUCENT;
        ft.commit();
    }

    @Override
    public void initData() {
        datas.add(new NBATeamInfo(1, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2236314729,1963438139&fm=58&bpow=474&bpoh=484", "76人"));
        datas.add(new NBATeamInfo(2, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1566481117,1935154086&fm=58&bpow=5000&bpoh=5622", "凯尔特人"));
        datas.add(new NBATeamInfo(3, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3521732245,2485910933&fm=58&bpow=5000&bpoh=4055", "尼克斯"));
        datas.add(new NBATeamInfo(4, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3300417429,3397737289&fm=58&bpow=1024&bpoh=1024", "篮网"));
        datas.add(new NBATeamInfo(5, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=926968749,2371275299&fm=58&bpow=5000&bpoh=5000", "猛龙"));
        datas.add(new NBATeamInfo(6, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1160785054,4209995522&fm=58&bpow=5000&bpoh=4845", "黄蜂"));
        datas.add(new NBATeamInfo(7, "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2450594186,2099126213&fm=58&bpow=5000&bpoh=5000", "老鹰"));
        datas.add(new NBATeamInfo(8, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3728394418,3364356539&fm=58&bpow=5000&bpoh=6899", "热火"));
        datas.add(new NBATeamInfo(9, "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1819753250,2103101445&fm=58&bpow=5000&bpoh=3634", "魔术"));
        datas.add(new NBATeamInfo(10, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=528750363,866202324&fm=58&bpow=5000&bpoh=4999", "奇才"));
        datas.add(new NBATeamInfo(11, "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2482065200,1302233082&fm=58&bpow=5000&bpoh=6193", "雄鹿"));
        datas.add(new NBATeamInfo(12, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3459032800,2923796794&fm=58&bpow=1014&bpoh=1024", "公牛"));
        datas.add(new NBATeamInfo(13, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3391261690,2529005707&fm=58&bpow=3459&bpoh=5000", "骑士"));
        datas.add(new NBATeamInfo(14, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1775051586,884401526&fm=58&bpow=450&bpoh=418", "步行者"));
        datas.add(new NBATeamInfo(15, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=646762162,2689537402&fm=58&bpow=5000&bpoh=5000", "活塞"));
        datas.add(new NBATeamInfo(16, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3476349652,3739181458&fm=58&bpow=5000&bpoh=3801", "快船"));
        datas.add(new NBATeamInfo(17, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3154681923,2260920487&fm=58&bpow=5000&bpoh=3086", "湖人"));
        datas.add(new NBATeamInfo(18, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2734540038,3634198010&fm=58&bpow=5000&bpoh=5704", "国王"));
        datas.add(new NBATeamInfo(19, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2624964291,3519607654&fm=58&bpow=838&bpoh=1024", "勇士"));
        datas.add(new NBATeamInfo(20, "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=182720300,3568251185&fm=179&app=42&f=PNG?w=121&h=140", "太阳"));
        datas.add(new NBATeamInfo(21, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2941139388,1155452808&fm=58&bpow=5000&bpoh=6519", "灰熊"));
        datas.add(new NBATeamInfo(22, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3135543829,2859436328&fm=58&bpow=5000&bpoh=3736", "鹈鹕"));
        datas.add(new NBATeamInfo(23, "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2602715048,1763469380&fm=58&bpow=1028&bpoh=1024", "独行侠"));
        datas.add(new NBATeamInfo(24, "https://ss1.baidu.com/70cFfyinKgQFm2e88IuM_a/forum/pic/item/562c11dfa9ec8a131caffce8fa03918fa0ecc005.jpg", "火箭"));
        datas.add(new NBATeamInfo(25, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1418414035,1493396258&fm=58&bpow=450&bpoh=418", "马刺"));
        datas.add(new NBATeamInfo(26, "https://ss3.baidu.com/7LsWdDW5_xN3otqbppnN2DJv/xiaodu/pic/item/b812c8fcc3cec3fde32433ffdd88d43f87942725.jpg", "爵士"));
        datas.add(new NBATeamInfo(27, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=231721273,3539698178&fm=179&app=42&f=PNG?w=121&h=140", "掘金"));
        datas.add(new NBATeamInfo(28, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2311361823,3584739495&fm=58&bpow=5000&bpoh=4608", "雷霆"));
        datas.add(new NBATeamInfo(29, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3634447446,2330526893&fm=58&bpow=4999&bpoh=5000", "森林狼"));
        datas.add(new NBATeamInfo(30, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3239059377,3085172775&fm=58&bpow=5000&bpoh=4322", "开拓者"));
    }

    @Override
    public void initView() {
        rel_top = findViewById(R.id.rel_top);
        nba_img_back = findViewById(R.id.nba_img_back);
        txt_about = findViewById(R.id.txt_about);

        txt_nba_notbegin = findViewById(R.id.txt_nba_notbegin);
        txt_nba_ing = findViewById(R.id.txt_nba_ing);
        txt_nba_over = findViewById(R.id.txt_nba_over);

        txt_about.setOnClickListener(this);
        nba_img_back.setOnClickListener(this);
        txt_nba_notbegin.setOnClickListener(this);
        txt_nba_ing.setOnClickListener(this);
        txt_nba_over.setOnClickListener(this);
    }

    private void initFrag() {
        fragments.put(PAGE_COMMON, new NBA_Notbegin_Fragment());
        fragments.put(PAGE_TRANSLUCENT, new NBA_ing_Fragment());
        fragments.put(PAGE_COORDINATOR, new NBA_over_Fragment());
    }

    /**
     * 当页面选中时改变当前的导航栏蓝色和图片的状态
     * @param position 当前页面
     */
    public void SelectColor(int position) {
        if(position == 0){
            //给底部到导航栏的image更换图片
            txt_nba_notbegin.setTextColor(Color.BLACK);
            txt_nba_ing.setTextColor(Color.WHITE);
            txt_nba_over.setTextColor(Color.WHITE);
        } else if (position == 1){
            txt_nba_ing.setTextColor(Color.BLACK);
            txt_nba_notbegin.setTextColor(Color.WHITE);
            txt_nba_over.setTextColor(Color.WHITE);
        } else if (position == 2){
            txt_nba_over.setTextColor(Color.BLACK);
            txt_nba_ing.setTextColor(Color.WHITE);
            txt_nba_notbegin.setTextColor(Color.WHITE);
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

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.nba_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }else if(temdId == R.id.txt_nba_notbegin){
            changeTab(PAGE_COMMON);
        }else if(temdId == R.id.txt_nba_ing){
            changeTab(PAGE_TRANSLUCENT);
        }else if(temdId == R.id.txt_nba_over){
            changeTab(PAGE_COORDINATOR);
        }else if(temdId == R.id.txt_about){
            //弹出下拉框
            if(isAboutshow){
                isAboutshow = false;
                pop_bottom.dismiss();
                if(isVSshow){
                    isVSshow = false; pop_vs.dismiss();
                }
            }else{
                open_popwindows(txt_about);
            }
        }else if(temdId == R.id.txt_teamvs_sel){
            if(isVSshow){
                isVSshow = false;
                pop_vs.dismiss();
            }else{
                isAboutshow = false;
                pop_bottom.dismiss();
                open_VS_popwindows();
            }
        }else if(temdId == R.id.txt_teamsc_sel){
            if(isSCshow){
                isSCshow = false;
                pop_vs.dismiss();
            }else{
                isAboutshow = false;
                pop_bottom.dismiss();
                open_SC_popwindows();
            }
        }else if(temdId == R.id.txt_vs_sel){
            //查询球队对战的信息
            String zhu = et_team_zhu.getText().toString().trim();
            String ke = et_team_ke.getText().toString().trim();
            if(zhu.equals("") || zhu == null && ke.equals("") || ke == null) {
                Toast.makeText(activity, getResources().getText(R.string.notteam), Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(activity, TeamVSActivity.class);
            intent.putExtra(ZHU, zhu);
            intent.putExtra(KE, ke);
            startActivity(intent);
            pop_vs.dismiss();
        }else if(temdId == R.id.nba_txt_aboutpop_cancle){
            isVSshow = false;
            pop_vs.dismiss();
        }else if(temdId == R.id.nba_txt_aboutpopsc_cancle){
            isSCshow = false;
            pop_sc.dismiss();
        }else if(temdId == R.id.nba_txt_teamsc_cancle){
            pop_teamsc.dismiss();
        }
    }

    /**
     * 通过球队名称去查询该球队的赛程信息
     */
    Runnable getscdatas_byTeam = new Runnable() {
        @Override
        public void run() {
            A.C_Log(activity, "查询" + et_Team + "队的赛程信息");
            Map<String, String> par = new HashMap<>();
            par.put("key", NBAActivity.NBA_APPKEY);
            par.put("team", et_Team);
            HttpRequest.post(HTTPURLS.nba_sc, (HashMap<String, String>) par, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    JsonDta(result);
                }
            });
        }
    };

    /**
     * 解析json数据
     * @param result
     */
    private void JsonDta(String result) {
        sc_datas.clear();
        Message message = handler.obtainMessage();
        setNBASC_Info info = new Gson().fromJson(result, setNBASC_Info.class);
        if(info.getError_code() != 0){
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }else{
            A.C_Log(activity, "------>" + info.getReason());
            /**
             * 循环list集合
             * 遍历所有信息保存至对象中并添加给datas去显示
             */
            for (int i = 0; i < info.getResult().getList().size(); i++){
                NBASCInfo obj = new NBASCInfo();
                obj.setM_time(info.getResult().getList().get(i).getTime());
                obj.setPlayer1(info.getResult().getList().get(i).getPlayer1());
                obj.setPlayer1logo(info.getResult().getList().get(i).getPlayer1logo());
                obj.setPlayer2(info.getResult().getList().get(i).getPlayer2());
                obj.setPlayer2logo(info.getResult().getList().get(i).getPlayer2logo());
                obj.setScore(info.getResult().getList().get(i).getScore());
                obj.setStatus(info.getResult().getList().get(i).getStatus());
                obj.setLink1text(info.getResult().getList().get(i).getLink1text());
                obj.setLink2text(info.getResult().getList().get(i).getLink2text());
                sc_datas.add(obj);
            }
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }
    }

    /**
     * 具体球队赛程的展现
     */
    private void open_TeamSC_popwindows(){
        View view = LayoutInflater.from(activity).inflate(R.layout.nba_teamsc_bottom, null);
        TextView nba_txt_teamsc_cancle = view.findViewById(R.id.nba_txt_teamsc_cancle);
        nba_txt_teamsc_cancle.setOnClickListener(this);
        RecyclerView nba_teamsc_rv = view.findViewById(R.id.nba_teamsc_rv);
        nba_teamsc_rv.setLayoutManager(new LinearLayoutManager(activity));
        scAdapter = new NBATeamSCAdapter(activity, sc_datas);

        nba_teamsc_rv.setAdapter(scAdapter);
        pop_teamsc = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_teamsc.setBackgroundDrawable(new ColorDrawable());
        pop_teamsc.setTouchable(true);
        pop_teamsc.setOutsideTouchable(false);
        pop_teamsc.setAnimationStyle(R.style.nba_about_pop);
        pop_teamsc.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=1.0f;
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });

        pop_teamsc.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 查询球队赛程
     */
    private void open_SC_popwindows() {
        isSCshow = true;
        View view = LayoutInflater.from(activity).inflate(R.layout.nba_sc_bottom, null);
        TextView nba_txt_aboutpopsc_cancle = view.findViewById(R.id.nba_txt_aboutpopsc_cancle);
        nba_txt_aboutpopsc_cancle.setOnClickListener(this);
        RecyclerView nba_sc_rv = view.findViewById(R.id.nba_sc_rv);
        nba_sc_rv.setLayoutManager(new GridLayoutManager(activity, 5));
        nba_sc_rv.addItemDecoration(new GridDividerItemDecoration(activity));
        adapter = new NBATeamInfoAdapter(activity, datas);
        adapter.setLinster(new NBATeamInfoAdapter.ItemOnClickLinster() {
            @Override
            public void textItemOnClick(View view, int position) {
                isSCshow = false;
                pop_sc.dismiss();
                et_Team = datas.get(position).getName();
                new Thread(getscdatas_byTeam){}.start();
            }
        });
        nba_sc_rv.setAdapter(adapter);
        pop_sc = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_sc.setBackgroundDrawable(new ColorDrawable());
        pop_sc.setTouchable(true);
        pop_sc.setOutsideTouchable(false);
        pop_sc.setAnimationStyle(R.style.nba_about_pop);
        pop_sc.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=1.0f;
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });

        pop_sc.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 查询球队对战
     */
    private void open_VS_popwindows() {
        isVSshow = true;
        View view = LayoutInflater.from(activity).inflate(R.layout.nba_vs_bottom, null);
        et_team_zhu = view.findViewById(R.id.et_team_zhu);
        et_team_ke = view.findViewById(R.id.et_team_ke);
        TextView txt_vs_sel = view.findViewById(R.id.txt_vs_sel);
        TextView nba_txt_aboutpop_cancle = view.findViewById(R.id.nba_txt_aboutpop_cancle);
        txt_vs_sel.setOnClickListener(this);
        nba_txt_aboutpop_cancle.setOnClickListener(this);
        pop_vs = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_vs.setBackgroundDrawable(new ColorDrawable());
        pop_vs.setTouchable(true);
        pop_vs.setOutsideTouchable(false);
        pop_vs.setAnimationStyle(R.style.nba_about_pop);
        pop_vs.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=1.0f;
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });

        pop_vs.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 这个是右上角展开的pop
     * @param parent
     */
    private void open_popwindows(View parent) {
        isAboutshow = true;
        View view = LayoutInflater.from(activity).inflate(R.layout.nba_bottom, null);
        TextView txt_teamvs_sel = view.findViewById(R.id.txt_teamvs_sel);
        TextView txt_teamsc_sel = view.findViewById(R.id.txt_teamsc_sel);
        txt_teamvs_sel.setOnClickListener(this);
        txt_teamsc_sel.setOnClickListener(this);
        pop_bottom = new PopupWindow(view, 400, LinearLayout.LayoutParams.WRAP_CONTENT);
        pop_bottom.setBackgroundDrawable(new ColorDrawable());
        pop_bottom.setTouchable(true);
        pop_bottom.setOutsideTouchable(false);
        pop_bottom.showAsDropDown(parent);
        pop_bottom.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=1.0f;
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });
        //显示位置
        pop_bottom.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        //这个代表背景的一个虚色
        lp.alpha=0.7f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }
}
