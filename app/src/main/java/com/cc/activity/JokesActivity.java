package com.cc.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.adapter.JokeLVAdapter;
import com.cc.dialog.CommomDialog;
import com.cc.model.JokesNew;
import com.cc.model.SJJokeNow;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 笑话大全
 */
public class JokesActivity extends BaseActivity implements View.OnClickListener {

    public static String Joke_APPKEY = "e9bbc8a5de090451bd5da96dc574a94a";

    static final int SUCC_CODE = 0;

    Activity activity = JokesActivity.this;

    ImageView joke_img_back, joke_img_load;

    RecyclerView joke_rv;

    JokeLVAdapter adapter;

    boolean isRef, isLoad = false;

    RefreshLayout activity_joke_refreshLayout;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE && isLoad || isRef){
                adapter.notifyDataSetChanged();
            } else if(msg.arg1 == SUCC_CODE){
                joke_img_load.clearAnimation();
                joke_img_load.setVisibility(View.GONE);
                adapter = new JokeLVAdapter(activity, datas);
                joke_rv.setLayoutManager(new LinearLayoutManager(activity));
                joke_rv.setAdapter(adapter);

                adapter.setLinster(new JokeLVAdapter.ItemOnClickLinster() {
                    @Override
                    public void textItemOnClick(View view, int position) {
                        A.C_Log(activity, "----->position=" + position);
                        //打开一个窗口
                        openWindow(position);
                    }
                });
            }else{
                joke_img_load.clearAnimation();
                joke_img_load.setVisibility(View.GONE);
                Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 通过position去查找唯一的一条信息
     * @param position
     */
    private void openWindow(int position) {
        new CommomDialog(activity, R.style.todayjoke_dialog, datas.get(position).getContent(), datas.get(position).getUpdatetime(), new CommomDialog.OnCloseListener() {

            @Override
            public void onClick(Dialog dialog, String content) {

            }
        }, 3).show();
    }

    List<JokesNew> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_jokes;
    }

    @Override
    public void initView() {
        joke_img_back = findViewById(R.id.joke_img_back);
        joke_img_load = findViewById(R.id.joke_img_load);
        joke_rv = findViewById(R.id.joke_rv);

        activity_joke_refreshLayout = findViewById(R.id.activity_joke_refreshLayout);

        //设置refreshLayout的一些操作
        //第一次进来自动刷新
//        activity_joke_refreshLayout.autoRefresh();

        //越界回弹
        activity_joke_refreshLayout.setEnableOverScrollBounce(false);

        //在刷新或者加载的时候不允许操作视图
        activity_joke_refreshLayout.setDisableContentWhenRefresh(true);
        activity_joke_refreshLayout.setDisableContentWhenLoading(true);

        //监听列表在滚动到底部时触发加载事件（默认true）
        activity_joke_refreshLayout.setEnableAutoLoadmore(false);

        /**
         * 正在下拉刷新数据中
         */
        activity_joke_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                A.C_Log(activity, "刷新最新的笑话");
                //数据加载完后调用这行结束刷新
//                activity_joke_refreshLayout.finishRefresh(0000);
                isRef = true;
                handler.post(getRefreshDatas);
            }
        });

        /**
         * 正在上拉加载数据中
         */
        activity_joke_refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                A.C_Log(activity, "加载早些的笑话");
//                activity_joke_refreshLayout.finishLoadmore(0000);
                isLoad = true;
                handler.post(getLoadmoreDatas);
            }
        });

        joke_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        joke_img_load.setVisibility(View.VISIBLE);
        A.openA(activity, joke_img_load);
        handler.post(getDatas);
    }

    /**
     * getDatas
     */
    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            HttpRequest.get(HTTPURLS.joke_get + "&key=" + Joke_APPKEY, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    A.C_Log(activity, "数据获取成功");
                    String result = response.body().string();
                    JsonDta(result);
                }
            });
        }
    };

    /**
     * 解析json
     * @param result
     */
    private void JsonDta(String result) {
        Message message = handler.obtainMessage();
        SJJokeNow obj = new Gson().fromJson(result, SJJokeNow.class);
        if(obj.getError_code() != 0){
            message.arg1 = obj.getError_code();
            handler.sendMessage(message);
        }else {
            if(isRef){
                A.C_Log(activity, "------>" + obj.getReason());
                List<JokesNew> json = new ArrayList<>();
                for (int i = 0; i < obj.getResult().size(); i ++){
                    JokesNew info = new JokesNew();
                    info.setHashId(obj.getResult().get(i).getHashId());
                    info.setContent(obj.getResult().get(i).getContent());
                    info.setUnixtime(obj.getResult().get(i).getUnixtime());
                    json.add(info);
                }
                for(int i = 0 ; i < datas.size() ; i++) {
                    json.add(datas.get(i));
                }
                datas.clear();
                for(int i = 0 ; i < json.size() ; i++) {
                    datas.add(json.get(i));
                }
                isRef = false;
//                datas = json;
            }else {
                A.C_Log(activity, "------>" + obj.getReason());
                for (int i = 0; i < obj.getResult().size(); i ++){
                    JokesNew info = new JokesNew();
                    info.setHashId(obj.getResult().get(i).getHashId());
                    info.setContent(obj.getResult().get(i).getContent());
                    info.setUnixtime(obj.getResult().get(i).getUnixtime());
                    datas.add(info);
                }
            }
//            datas = removeDuplicate(datas);
            message.arg1 = obj.getError_code();
            handler.sendMessage(message);
        }
    }

    public static List removeDuplicate(List list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 加载刷新的数据
     */
    Runnable getRefreshDatas = new Runnable() {
        @Override
        public void run() {
            HttpRequest.get(HTTPURLS.joke_get + "&key=" + JokesActivity.Joke_APPKEY, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    activity_joke_refreshLayout.finishRefresh(0000 , false);
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    A.C_Log(activity, "数据获取成功");
                    activity_joke_refreshLayout.finishRefresh(0000 , true);
                    String result = response.body().string();
                    JsonDta(result);
                }
            });
        }
    };

    /**
     * 加载上拉的数据
     */
    Runnable getLoadmoreDatas = new Runnable() {
        @Override
        public void run() {
            HttpRequest.get(HTTPURLS.joke_get + "&key=" + JokesActivity.Joke_APPKEY, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    activity_joke_refreshLayout.finishLoadmore(0000 , false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    A.C_Log(activity, "数据获取成功");
                    activity_joke_refreshLayout.finishLoadmore(0000 , true);
                    String result = response.body().string();
                    JsonDta(result);
                }
            });
        }
    };

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.joke_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

}
