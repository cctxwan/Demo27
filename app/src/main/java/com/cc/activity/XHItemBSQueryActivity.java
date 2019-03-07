package com.cc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.adapter.BYBSFragAdapter;
import com.cc.model.BYBS_Info;
import com.cc.model.setByBS_Info;
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

public class XHItemBSQueryActivity extends BaseActivity implements View.OnClickListener {

    public static String BUSHOU = "";
    public static int SUCC_CODE = 0;
    Activity activity = XHItemBSQueryActivity.this;
    ImageView xhbsquery_img_back, img_bsquery_load;
    GridView xh_bsquery_gv;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE){
                A.C_Log(activity, "请求成功");
                img_bsquery_load.clearAnimation();
                img_bsquery_load.setVisibility(View.GONE);
                adapter = new BYBSFragAdapter(activity, datas);
                xh_bsquery_gv.setAdapter(adapter);
;            }else{
                img_bsquery_load.clearAnimation();
                img_bsquery_load.setVisibility(View.GONE);
                Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };
    List<BYBS_Info> datas = new ArrayList<>();
    BYBSFragAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_xhitem_bsquery;
    }

    @Override
    public void initView() {
        xhbsquery_img_back = findViewById(R.id.xhbsquery_img_back);
        img_bsquery_load = findViewById(R.id.img_bsquery_load);
        xh_bsquery_gv = findViewById(R.id.xh_bsquery_gv);

        xhbsquery_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if(intent == null) return;
        BUSHOU = intent.getStringExtra(BUSHOU);

        img_bsquery_load.setVisibility(View.VISIBLE);
        A.openA(activity, img_bsquery_load);
        handler.post(getDatasByBS);
    }

    Runnable getDatasByBS = new Runnable() {
        @Override
        public void run() {
            Map<String, String> par = new HashMap<>();
            par.put("key", XHDictionaryActivity.XH_APPKEY);
            par.put("word", BUSHOU);
            par.put("pageszie", 50 + "");
            HttpRequest.post(HTTPURLS.querybs, (HashMap<String, String>) par, new Callback() {
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
        setByBS_Info info = new Gson().fromJson(result, setByBS_Info.class);
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
                BYBS_Info obj = new BYBS_Info();
                obj.setId(info.getResult().getList().get(i).getId());
                obj.setBihua(info.getResult().getList().get(i).getBihua());
                obj.setBushou(info.getResult().getList().get(i).getBushou());
                obj.setPinyin(info.getResult().getList().get(i).getPinyin());
                obj.setPy(info.getResult().getList().get(i).getPy());
                obj.setWubi(info.getResult().getList().get(i).getWubi());
                obj.setZi(info.getResult().getList().get(i).getZi());
                datas.add(obj);
            }
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.xhbsquery_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

}
