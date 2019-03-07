package com.cc.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.activity.HoroscopeActivity;
import com.cc.model.YS_Month_Info;
import com.cc.utils.A;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 运势--本月界面
 */
public class XZYS_BY_Fragment extends Fragment {

    View view;

    ImageView img_xzysby_load, img_xzby_icon;
    NestedScrollView ns_xzysby;

    TextView txt_xzby_name, txt_xzby_work, txt_xzby_health, txt_xzby_love,
            txt_xzby_money, txt_xzby_all, txt_xzby_month;

    static final int SUCC_CODE = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.obj != null && !msg.obj.equals("")){
                YS_Month_Info data = (YS_Month_Info) msg.obj;
                if(data.getError_code() == SUCC_CODE){
                    A.C_Log(getActivity(), "请求成功");
                    //请求成功，关闭所谓的动画
                    img_xzysby_load.setVisibility(View.GONE);
                    img_xzysby_load.clearAnimation();
                    //将请求成功的值赋值给textview控件
                    txt_xzby_all.setText(getResources().getString(R.string.xz_work) + "：" + data.getAll());
                    txt_xzby_work.setText(getResources().getString(R.string.xz_work) + "：" + data.getWork());
                    txt_xzby_health.setText(getResources().getString(R.string.xz_health) + "：" + data.getHealth());
                    txt_xzby_love.setText(getResources().getString(R.string.xz_love) + "：" + data.getLove());
                    txt_xzby_money.setText(getResources().getString(R.string.xz_money) + "：" + data.getMoney());
                    txt_xzby_month.setText(getResources().getString(R.string.xz_job) + "：" + data.getMonth());
                }else{
                    img_xzysby_load.setVisibility(View.GONE);
                    img_xzysby_load.clearAnimation();
                    Toast.makeText(getActivity(), R.string.getDataError, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.xzys_by_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView(){
        img_xzysby_load = view.findViewById(R.id.img_xzysby_load);
        ns_xzysby = view.findViewById(R.id.ns_xzysby);

        img_xzby_icon = view.findViewById(R.id.img_xzby_icon);
        txt_xzby_name = view.findViewById(R.id.txt_xzby_name);
        txt_xzby_work = view.findViewById(R.id.txt_xzby_work);
        txt_xzby_health = view.findViewById(R.id.txt_xzby_health);
        txt_xzby_love = view.findViewById(R.id.txt_xzby_love);
        txt_xzby_money = view.findViewById(R.id.txt_xzby_money);
        txt_xzby_all = view.findViewById(R.id.txt_xzby_all);
        txt_xzby_month = view.findViewById(R.id.txt_xzby_month);
    }

    /**
     * 初始化数据，并添加默认值（天秤座）
     */
    private void initData(){
        //通过activity的imgs给icon和name赋值
        for (int i = 0; i < HoroscopeActivity.imgs.size(); i++){
            if(XZYSItemFrag.title.equals(HoroscopeActivity.imgs.get(i).getName())){
                //星座名和icon
                img_xzby_icon.setImageResource(HoroscopeActivity.imgs.get(i).getIcon());
                txt_xzby_name.setText(XZYSItemFrag.title);
            }
        }

        img_xzysby_load.setVisibility(View.VISIBLE);
        A.openA(getActivity(), img_xzysby_load);
        //通过网络请求地址去获取当前的星座的具体运势（本周）
        handler.post(getDatas);
    }

    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> par = new HashMap<>();
            par.put("key", HoroscopeActivity.HOR_APPKEY);
            par.put("consName", XZYSItemFrag.title);
            par.put("type", "month");
            HttpRequest.post(HTTPURLS.ysquery, (HashMap<String, String>) par, new Callback() {
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
        YS_Month_Info info = new Gson().fromJson(result, YS_Month_Info.class);
        if(info.getError_code() != 0){
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }else{
            message.obj = info;
            handler.sendMessage(message);
        }
    }

}
