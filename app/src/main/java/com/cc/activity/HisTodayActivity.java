package com.cc.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.adapter.HisTodayLVAdapter;
import com.cc.model.HisTodayInfo;
import com.cc.model.TimeLineInfo;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.cc.utils.CustomDatePicker;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 历史上的今天
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class HisTodayActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = HisTodayActivity.this;

    RelativeLayout selectTime;
    TextView currentTime;
    CustomDatePicker customDatePicker;

    RecyclerView timeline_listview;

    //请求网络数据的appkey
    static final String HisToday_APPKEY = "a00bd107815534cdc0ad270491b26524";
    static final int SUCC_CODE = 0;

    HisTodayLVAdapter adapter;
    NestedScrollView histoday_ns;

    ImageView img_load, timeline_img_back;
    List<TimeLineInfo> datas = new ArrayList<>();

    int month, day;

    /**
     * 用来处理网络请求的数据
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE){
                A.C_Log(activity, "请求成功");
                img_load.clearAnimation();
                img_load.setVisibility(View.GONE);
                //通过适配器去加载数据到RV上
                adapter = new HisTodayLVAdapter(activity, datas);
                timeline_listview.setLayoutManager(new LinearLayoutManager(activity));
                timeline_listview.setAdapter(adapter);
            }else{
                img_load.clearAnimation();
                img_load.setVisibility(View.GONE);
                Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_his_today;
    }

    @Override
    public void initView() {
        timeline_img_back = findViewById(R.id.timeline_img_back);

        selectTime = findViewById(R.id.selectTime);
        currentTime = findViewById(R.id.currentTime);

        histoday_ns = findViewById(R.id.histoday_ns);
        timeline_listview = findViewById(R.id.timeline_listview);
        img_load = findViewById(R.id.img_load);

        timeline_listview.setNestedScrollingEnabled(false);
        timeline_img_back.setOnClickListener(this);
        selectTime.setOnClickListener(this);
//        histoday_ns.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                A.C_Log(activity, "ccccccccc");
//            }
//        });
    }

    @Override
    public void initData() {
        initDatePicker();
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        System.out.println("时间=" + now);
        month = Integer.valueOf(now.substring(5, 7));
        day = Integer.valueOf(now.substring(8, 10));
        currentTime.setText(now);
        datas.clear();
        img_load.setVisibility(View.VISIBLE);
        A.openA(activity, img_load);
        handler.post(getDatas);


        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                A.C_Log(activity, "----->" + time);
                month = Integer.valueOf(time.substring(5, 7));
                day = Integer.valueOf(time.substring(8, 10));
                currentTime.setText(time);
                datas.clear();
                img_load.setVisibility(View.VISIBLE);
                A.openA(activity, img_load);
                handler.post(getDatas);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
    }

    /**
     * getDatas
     * 通过参数去拿历史上的今天的数据
     */
    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> par = new HashMap<>();
            par.put("key", HisToday_APPKEY);
            par.put("month", month + "");
            par.put("day", day + "");
            //请求网络数据
            HttpRequest.post(HTTPURLS.lssdjt, (HashMap<String, String>) par, new Callback() {
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
     * 解析json数据
     * @param result
     */
    private void JsonDta(String result) {
        Message message = handler.obtainMessage();
        HisTodayInfo info = new Gson().fromJson(result, HisTodayInfo.class);
        if(info.getError_code() != 0){
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }else{
            A.C_Log(activity, "------>" + info.getReason());
            /**
             * 循环list集合
             * 遍历所有信息保存至对象中并添加给datas去显示
             */
            for (int i = 0; i < info.getResult().size(); i++){
                TimeLineInfo obj = new TimeLineInfo();
                obj.set_id(info.getResult().get(i).get_id());
                obj.setDay(info.getResult().get(i).getDay());
                obj.setDes(info.getResult().get(i).getDes());
                obj.setLunar(info.getResult().get(i).getLunar());
                obj.setMonth(info.getResult().get(i).getMonth());
                obj.setPic(info.getResult().get(i).getPic());
                obj.setTitle(info.getResult().get(i).getTitle());
                obj.setYear(info.getResult().get(i).getYear());
                datas.add(obj);
            }
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }
    }


    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.selectTime){
            customDatePicker.show(currentTime.getText().toString());
        }else if(temdId == R.id.timeline_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

}