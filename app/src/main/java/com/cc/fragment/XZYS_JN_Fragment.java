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
import com.cc.model.YS_Tear_Info;
import com.cc.utils.A;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 运势--今年界面
 */
public class XZYS_JN_Fragment extends Fragment {

    View view;
    ImageView img_xzysjn_load, img_xzjn_icon;
    NestedScrollView ns_xzysjn;

    TextView txt_xzjn_name, txt_xzjn_gaishu, txt_xzjn_shuoming,
            txt_xzjn_shiye, txt_xzjn_ganqing, txt_xzjn_caiyun,
            txt_xzjn_health, txt_xzjn_luckeyStone;

    static final int SUCC_CODE = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.obj != null && !msg.obj.equals("")){
                YS_Tear_Info data = (YS_Tear_Info) msg.obj;
                if(data.getError_code() == SUCC_CODE){
                    A.C_Log(getActivity(), "请求成功");
                    //请求成功，关闭所谓的动画
                    img_xzysjn_load.setVisibility(View.GONE);
                    img_xzysjn_load.clearAnimation();
                    //将请求成功的值赋值给textview控件
                    txt_xzjn_gaishu.setText(getResources().getString(R.string.xz_gaishu) + "：" + data.getMima().getInfo());
                    txt_xzjn_shuoming.setText(getResources().getString(R.string.xz_shuoming) + "：" + data.getMima().getText());
                    txt_xzjn_shiye.setText(getResources().getString(R.string.xz_shiye) + "：" + data.getCareer());
                    txt_xzjn_ganqing.setText(getResources().getString(R.string.xz_ganqing) + "：" + data.getLove());
                    txt_xzjn_health.setText(getResources().getString(R.string.xz_jiankang) + "：" + data.getHealth());
                    txt_xzjn_caiyun.setText(getResources().getString(R.string.xz_caiyun) + "：" + data.getFinance());
                    txt_xzjn_luckeyStone.setText(getResources().getString(R.string.xz_luckeytone) + "：" + data.getLuckeyStone());
                }else{
                    img_xzysjn_load.setVisibility(View.GONE);
                    img_xzysjn_load.clearAnimation();
                    Toast.makeText(getActivity(), R.string.getDataError, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.xzys_jn_layout, container, false);
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
        img_xzysjn_load = view.findViewById(R.id.img_xzysjn_load);
        ns_xzysjn = view.findViewById(R.id.ns_xzysjn);

        img_xzjn_icon = view.findViewById(R.id.img_xzjn_icon);
        txt_xzjn_name = view.findViewById(R.id.txt_xzjn_name);
        txt_xzjn_gaishu = view.findViewById(R.id.txt_xzjn_gaishu);
        txt_xzjn_shuoming = view.findViewById(R.id.txt_xzjn_shuoming);
        txt_xzjn_shiye = view.findViewById(R.id.txt_xzjn_shiye);
        txt_xzjn_ganqing = view.findViewById(R.id.txt_xzjn_ganqing);
        txt_xzjn_health = view.findViewById(R.id.txt_xzjn_health);
        txt_xzjn_caiyun = view.findViewById(R.id.txt_xzjn_caiyun);
        txt_xzjn_luckeyStone = view.findViewById(R.id.txt_xzjn_luckeyStone);
    }

    /**
     * 初始化数据，并添加默认值（天秤座）
     */
    private void initData(){
        //通过activity的imgs给icon和name赋值
        for (int i = 0; i < HoroscopeActivity.imgs.size(); i++){
            if(XZYSItemFrag.title.equals(HoroscopeActivity.imgs.get(i).getName())){
                //星座名和icon
                img_xzjn_icon.setImageResource(HoroscopeActivity.imgs.get(i).getIcon());
                txt_xzjn_name.setText(XZYSItemFrag.title);
            }
        }

        img_xzysjn_load.setVisibility(View.VISIBLE);
        A.openA(getActivity(), img_xzysjn_load);
        //通过网络请求地址去获取当前的星座的具体运势（今年）
        handler.post(getDatas);
    }

    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> par = new HashMap<>();
            par.put("key", HoroscopeActivity.HOR_APPKEY);
            par.put("consName", XZYSItemFrag.title);
            par.put("type", "year");
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
        YS_Tear_Info info = new Gson().fromJson(result, YS_Tear_Info.class);
        if(info.getError_code() != 0){
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }else{
            message.obj = info;
            handler.sendMessage(message);
        }
    }

}
