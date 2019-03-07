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
import com.cc.adapter.BYPYFragAdapter;
import com.cc.model.BYPY_Info;
import com.cc.model.setByPY_Info;
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

public class XHItemPYQueryActivity extends BaseActivity implements View.OnClickListener {

    public static String PINYIN = "";
    public static int SUCC_CODE = 0;
    Activity activity = XHItemPYQueryActivity.this;
    ImageView xhpyquery_img_back, img_pyquery_load;
    GridView xh_pyquery_gv;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE){
                A.C_Log(activity, "请求成功");
                img_pyquery_load.clearAnimation();
                img_pyquery_load.setVisibility(View.GONE);
                adapter = new BYPYFragAdapter(activity, datas);
                xh_pyquery_gv.setAdapter(adapter);
            }else{
                img_pyquery_load.clearAnimation();
                img_pyquery_load.setVisibility(View.GONE);
                Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };
    List<BYPY_Info> datas = new ArrayList<>();
    BYPYFragAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_xhitem_pyquery;
    }

    @Override
    public void initView() {
        xhpyquery_img_back = findViewById(R.id.xhpyquery_img_back);
        img_pyquery_load = findViewById(R.id.img_pyquery_load);
        xh_pyquery_gv = findViewById(R.id.xh_pyquery_gv);

        xhpyquery_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if(intent == null) return;
        PINYIN = intent.getStringExtra(PINYIN);

        img_pyquery_load.setVisibility(View.VISIBLE);
        A.openA(activity, img_pyquery_load);
        handler.post(getDatasByPY);
    }

    Runnable getDatasByPY = new Runnable() {
        @Override
        public void run() {
            Map<String, String> par = new HashMap<>();
            par.put("key", XHDictionaryActivity.XH_APPKEY);
            par.put("word", PINYIN);
            par.put("pageszie", 50 + "");
            HttpRequest.post(HTTPURLS.querypy, (HashMap<String, String>) par, new Callback() {
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
        setByPY_Info info = new Gson().fromJson(result, setByPY_Info.class);
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
                BYPY_Info obj = new BYPY_Info();
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
        if(temdId == R.id.xhpyquery_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

}
