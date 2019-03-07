package com.cc.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.adapter.WXJXLVAdapter;
import com.cc.db.SQLiteDB;
import com.cc.model.WXJXInfo;
import com.cc.model.setWXJXInfo;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 微信精选
 */
public class WXFeaturedActivity extends BaseActivity implements View.OnClickListener  {

    //key
    private static final String WX_APPKEY = "f0bd63aaba2a63f7cb438647af815744";

    //当前页
    private static final int INDEX_PAGE = 2;

    //页数的最大数值
    private static final int PAGE_COUNT = 50;
    static final int SUCC_CODE = 0;

    //分页加载
    public static boolean add_data = false;
    public static int add_datacount = 20;

    Activity activity = WXFeaturedActivity.this;
    ImageView wx_img_back, img_wx_load;

    NestedScrollView wx_ns;
    RecyclerView wx_listview;

    //上拉加载  下拉刷新
    RefreshLayout activity_wxfeature_refreshLayout;

    /**save*/
    SharedPreferences sf;

    /**
     * 用来处理网络请求的数据
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE){
                A.C_Log(activity, "请求成功");
                img_wx_load.clearAnimation();
                img_wx_load.setVisibility(View.GONE);
                //通过适配器去加载数据到RV上
                adapter = new WXJXLVAdapter(activity, datas);
                wx_listview.setLayoutManager(new LinearLayoutManager(activity));
                wx_listview.setAdapter(adapter);

                adapter.setLinster(new WXJXLVAdapter.ItemOnClickLinster() {
                    @Override
                    public void textItemOnClick(View view, int position) {
                        A.C_Log(activity, "------->position=" + position);
                        Intent intent = new Intent(activity, WXDetailActivity.class);
                        intent.putExtra("id", datas.get(position).getId());
                        intent.putExtra("url", datas.get(position).getUrl());
                        intent.putExtra("title", datas.get(position).getTitle());
//                        intent.putExtra("img", datas.get(position).getFirstImg());
                        intent.putExtra("source", datas.get(position).getSource());
                        startActivity(intent);
                    }
                });
            }else{
                img_wx_load.clearAnimation();
                img_wx_load.setVisibility(View.GONE);
                Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };

    List<setWXJXInfo> datas = new ArrayList<>();
    List<setWXJXInfo> datas2 = new ArrayList<>();
    WXJXLVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_wxfeatured;
    }

    @Override
    public void initView() {
        wx_img_back = findViewById(R.id.wx_img_back);
        img_wx_load = findViewById(R.id.img_wx_load);
        wx_ns = findViewById(R.id.wx_ns);
        wx_listview = findViewById(R.id.wx_listview);
        findViewById(R.id.img_wx_load);


        activity_wxfeature_refreshLayout = findViewById(R.id.activity_wxfeature_refreshLayout);
        //设置refreshLayout的一些操作
        //第一次进来自动刷新
//        activity_joke_refreshLayout.autoRefresh();

        //越界回弹
        activity_wxfeature_refreshLayout.setEnableOverScrollBounce(false);

        //在刷新或者加载的时候不允许操作视图
        activity_wxfeature_refreshLayout.setDisableContentWhenRefresh(true);
        activity_wxfeature_refreshLayout.setDisableContentWhenLoading(true);

        //监听列表在滚动到底部时触发加载事件（默认true）
        activity_wxfeature_refreshLayout.setEnableAutoLoadmore(false);

        /**
         * 正在下拉刷新数据中
         */
        activity_wxfeature_refreshLayout.setEnableRefresh(false);

        /**
         * 正在上拉加载数据中
         */
        activity_wxfeature_refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                A.C_Log(activity, "加载早些的文章");
                add_data = true;
                add_datacount += 10;
                getBdDates();
            }
        });

        wx_listview.setNestedScrollingEnabled(false);

        wx_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        img_wx_load.setVisibility(View.VISIBLE);
        img_wx_load.setImageResource(R.drawable.cctx);
        try {
            if(isFirstInAPPWX()){
                handler.post(getNetDates);
            }else {
                getBdDates();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否第一次进入微信精选文章
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public boolean isFirstInAPPWX() throws PackageManager.NameNotFoundException {
        //获取包信息
        PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
        //当前app包的版本号（XML配置中的version）
        int currentVersion = info.versionCode;
        //本地存储文件
        sf = PreferenceManager.getDefaultSharedPreferences(activity);
        //将当前获取到的版本号存储起来，因为第一次运行，所以存入0
        int lastVersion = sf.getInt("/data/xml/firstinappwx.xml", 0);
        //当前版本号大于之前版本号说明该版本号第一次进入，故加载welcome页面启动动画效果
        if(currentVersion > lastVersion){
            //因为第一次进入，所以肯定会执行这段代码，执行之后，下次进入就应该将将0改为当前版本存储
            sf.edit().putInt("/data/xml/firstinappwx.xml", currentVersion).commit();
            return true;
        }
        return false;
    }

    /**
     * 执行网络请求
     */
    Runnable getNetDates = new Runnable() {
        @Override
        public void run() {
            Map<String, String> par = new HashMap<>();
            par.put("key", WX_APPKEY);
            par.put("pno", INDEX_PAGE + "");
            par.put("ps", PAGE_COUNT + "");
            HttpRequest.post(HTTPURLS.wxjx, (HashMap<String, String>) par, new Callback() {
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
        Message message = handler.obtainMessage();
        WXJXInfo info = new Gson().fromJson(result, WXJXInfo.class);
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
                //遍历数组进对象
                setWXJXInfo data = new setWXJXInfo();
                data.setId(info.getResult().getList().get(i).getId());
                data.setFirstImg(info.getResult().getList().get(i).getFirstImg());
                data.setMark(info.getResult().getList().get(i).getMark());
                data.setSource(info.getResult().getList().get(i).getSource());
                data.setTitle(info.getResult().getList().get(i).getTitle());
                data.setUrl(info.getResult().getList().get(i).getUrl());
                datas.add(data);
                datas2.add(data);
            }
            //将所有的信息保存进本地数据库
            SQLiteDB.getInstance(activity).saveWXFeature(SQLiteDB.getInstance(activity).getUsers().get(0).getAccountid(), datas2, false, false);
            A.C_Log(activity, "SQLiteDB.getInstance(activity).selectWXFeature().size()=" + SQLiteDB.getInstance(activity).selectWXFeature().size());
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }
    }

    /**
     * 从本地数据库中查询
     */
    private void getBdDates() {
        A.C_Log(activity, "SQLiteDB.getInstance(activity).selectWXFeature().size()=" + SQLiteDB.getInstance(activity).selectWXFeature().size());

        //获取消息，并从本地数据库中查询，判断非空数据
        Message message = handler.obtainMessage();
        List<setWXJXInfo> setWXJXInfos = SQLiteDB.getInstance(activity).selectWXFeature();
        if(setWXJXInfos.size() == 0) return;

        //关闭加载动画
        activity_wxfeature_refreshLayout.finishLoadmore(2000 , true);

        //如果大于全部数据，说明已经全部加载完成
        if(add_datacount > setWXJXInfos.size()){
            A.C_Toast(activity, "数据已全部加载完成", 2);
            return;
        }

        //分页加载
        if(setWXJXInfos.size() > 20){
            A.C_Log(activity, "当前数据量大于20，使用分页加载，只加载20条数据");
            for (int i = add_datacount; i < setWXJXInfos.size();){
                setWXJXInfos.remove(add_datacount);
            }
        }

        //清空datas，重新赋值给datas，并刷新adapter
        datas.clear();
        for (int i = 0; i < setWXJXInfos.size(); i++){
            setWXJXInfo infos = new setWXJXInfo();
            infos.setId(setWXJXInfos.get(i).getId());
            infos.setUrl(setWXJXInfos.get(i).getUrl());
            infos.setTitle(setWXJXInfos.get(i).getTitle());
            infos.setSource(setWXJXInfos.get(i).getSource());
            datas.add(infos);
        }

        //发送消息给handler，通知adapter工作
        message.arg1 = 0;
        handler.sendMessage(message);
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.wx_img_back){
            goback();
        }
    }

    /**
     * 返回
     */
    private void goback(){
        add_datacount = 20;
        ActivityMan.getmInstance().finishActivity(activity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                goback();
                break;
            default:
                break;
        }
        return false;
    }
}
