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
import com.cc.model.YS_Today_Info;
import com.cc.utils.A;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 运势--今天界面
 */
public class XZYS_JT_Fragment extends Fragment {

    View view;
    ImageView img_xzysjt_load, img_xzjt_icon;
    NestedScrollView ns_xzysjt;

    TextView txt_xzjt_name, txt_xzjt_work, txt_xzjt_all,
            txt_xzjt_color, txt_xzjt_health, txt_xzjt_love,
            txt_xzjt_money, txt_xzjt_number,
            txt_xzjt_QFriend, txt_xzjt_summary;

    static final int SUCC_CODE = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.obj != null && !msg.obj.equals("")){
                YS_Today_Info data = (YS_Today_Info) msg.obj;
                if(data.getError_code() == SUCC_CODE){
                    A.C_Log(getActivity(), "请求成功");
                    //请求成功，关闭所谓的动画
                    img_xzysjt_load.setVisibility(View.GONE);
                    img_xzysjt_load.clearAnimation();
                    //将请求成功的值赋值给textview控件
                    txt_xzjt_work.setText(getResources().getString(R.string.xz_work) + "：" + data.getWork());
                    txt_xzjt_all.setText(getResources().getString(R.string.xz_all) + "：" + data.getAll());
                    txt_xzjt_color.setText(getResources().getString(R.string.xz_color) + "：" + data.getColor());
                    txt_xzjt_health.setText(getResources().getString(R.string.xz_health) + "：" + data.getHealth());
                    txt_xzjt_love.setText(getResources().getString(R.string.xz_love) + "：" + data.getLove());
                    txt_xzjt_money.setText(getResources().getString(R.string.xz_money) + "：" + data.getMoney());
                    txt_xzjt_number.setText(getResources().getString(R.string.xz_number) + "：" + data.getNumber());
                    txt_xzjt_QFriend.setText(getResources().getString(R.string.xz_QFriend) + "：" + data.getQFriend());
                    txt_xzjt_summary.setText(getResources().getString(R.string.xz_summary) + "：" + data.getSummary());
                }else{
                    img_xzysjt_load.setVisibility(View.GONE);
                    img_xzysjt_load.clearAnimation();
                    Toast.makeText(getActivity(), R.string.getDataError, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.xzys_jt_layout, container, false);
        A.C_Log(getActivity(), "Title=" + XZYSItemFrag.title);
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
        img_xzysjt_load = view.findViewById(R.id.img_xzysjt_load);
        ns_xzysjt = view.findViewById(R.id.ns_xzysjt);

        img_xzjt_icon = view.findViewById(R.id.img_xzjt_icon);
        txt_xzjt_name = view.findViewById(R.id.txt_xzjt_name);
        txt_xzjt_work = view.findViewById(R.id.txt_xzjt_work);
        txt_xzjt_all = view.findViewById(R.id.txt_xzjt_all);
        txt_xzjt_color = view.findViewById(R.id.txt_xzjt_color);
        txt_xzjt_health = view.findViewById(R.id.txt_xzjt_health);
        txt_xzjt_love = view.findViewById(R.id.txt_xzjt_love);
        txt_xzjt_money = view.findViewById(R.id.txt_xzjt_money);
        txt_xzjt_number = view.findViewById(R.id.txt_xzjt_number);
        txt_xzjt_QFriend = view.findViewById(R.id.txt_xzjt_QFriend);
        txt_xzjt_summary = view.findViewById(R.id.txt_xzjt_summary);
    }

    /**
     * 初始化数据，并添加默认值（天秤座）
     */
    private void initData(){
        //通过activity的imgs给icon和name赋值
        for (int i = 0; i < HoroscopeActivity.imgs.size(); i++){
            if(XZYSItemFrag.title.equals(HoroscopeActivity.imgs.get(i).getName())){
                //星座名和icon
                img_xzjt_icon.setImageResource(HoroscopeActivity.imgs.get(i).getIcon());
                txt_xzjt_name.setText(XZYSItemFrag.title);
            }
        }

        img_xzysjt_load.setVisibility(View.VISIBLE);
        A.openA(getActivity(), img_xzysjt_load);
        //通过网络请求地址去获取当前的星座的具体运势（今天）
        handler.post(getDatas);
    }

    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> par = new HashMap<>();
            par.put("key", HoroscopeActivity.HOR_APPKEY);
            par.put("consName", XZYSItemFrag.title);
            par.put("type", "today");
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
        YS_Today_Info info = new Gson().fromJson(result, YS_Today_Info.class);
        if(info.getError_code() != 0){
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }else{
            message.obj = info;
            handler.sendMessage(message);
        }
    }

}
