package com.cc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.adapter.TeamVSAdapter;
import com.cc.model.NbaVSInfo;
import com.cc.model.setNbaVSInfo;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeamVSActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = TeamVSActivity.this;

    String zhu, ke = "";

    public static int SUCC_CODE = 0;

    ImageView nbavs_img_back, img_vs_load;
    RecyclerView rv_vs;

    TeamVSAdapter adapter;

    TextView txt_vs_huhuan;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE){
                A.C_Log(activity, "请求成功");
                img_vs_load.clearAnimation();
                img_vs_load.setVisibility(View.GONE);
                //设置RecyclerView的数据加载
                rv_vs.setLayoutManager(new LinearLayoutManager(activity));
                adapter = new TeamVSAdapter(activity, datas);
                rv_vs.setAdapter(adapter);
            }else{
                img_vs_load.clearAnimation();
                img_vs_load.setVisibility(View.GONE);
                Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };

    List<NbaVSInfo> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        setTitle(false);

        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));

        getTeamData();

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_team_vs;
    }

    @Override
    public void initView() {
        nbavs_img_back = findViewById(R.id.nbavs_img_back);
        img_vs_load = findViewById(R.id.img_vs_load);
        rv_vs = findViewById(R.id.rv_vs);
        findViewById(R.id.txt_vs_huhuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zteam = zhu;
                String kteam = ke;
                zhu = kteam;
                datas.clear();
                ke = zteam;
                initData();
            }
        });

        nbavs_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        img_vs_load.setVisibility(View.VISIBLE);
        A.openA(activity, img_vs_load);
        handler.post(getDatas);
    }

    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> par = new HashMap<>();
            par.put("key", NBAActivity.NBA_APPKEY);
            par.put("hteam", zhu);
            par.put("vteam", ke);
            HttpRequest.post(HTTPURLS.nba_vs, (HashMap<String, String>) par, new Callback() {
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
        setNbaVSInfo info = new Gson().fromJson(result, setNbaVSInfo.class);
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
                NbaVSInfo obj = new NbaVSInfo();
                obj.setTime(info.getResult().getList().get(i).getM_time());
                obj.setPlayer1(info.getResult().getList().get(i).getPlayer1());
                obj.setPlayer2(info.getResult().getList().get(i).getPlayer2());
                obj.setPlayer1logo(info.getResult().getList().get(i).getPlayer1logo());
                obj.setPlayer2logo(info.getResult().getList().get(i).getPlayer2logo());
                obj.setScore(info.getResult().getList().get(i).getScore());
                obj.setLink1text(info.getResult().getList().get(i).getLink1text());
                obj.setLink2text(info.getResult().getList().get(i).getLink2text());
                obj.setStatus(info.getResult().getList().get(i).getStatus());
                datas.add(obj);
            }
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }
    }


    /**
     * 获取activity传过来的数据
     */
    private void getTeamData() {
        Intent intent = getIntent();
        zhu = intent.getStringExtra(NBAActivity.ZHU);
        ke = intent.getStringExtra(NBAActivity.KE);
        A.C_Log(activity, "zhu=" + zhu + ",ke=" + ke);
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.nbavs_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

}
