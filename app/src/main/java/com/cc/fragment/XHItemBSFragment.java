package com.cc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.activity.XHDictionaryActivity;
import com.cc.activity.XHItemBSQueryActivity;
import com.cc.adapter.BSFragAdapter;
import com.cc.model.BS_Info;
import com.cc.model.setBS_Info;
import com.cc.utils.A;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class XHItemBSFragment extends Fragment {

    View view;

    static final int SUCC_CODE = 0;

    GridView xh_bs_gv;

    ImageView img_bs_load;

    BSFragAdapter adapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE){
                A.C_Log(getActivity(), "请求成功");
                img_bs_load.clearAnimation();
                img_bs_load.setVisibility(View.GONE);
                //通过适配器去加载数据到RV上
                adapter = new BSFragAdapter(getActivity(), datas);
                xh_bs_gv.setAdapter(adapter);
                adapter.setOnItemClickLiester(new BSFragAdapter.onItemClickLiester() {
                    @Override
                    public void succ(View view, int postion) {
                        A.C_Log(getActivity(), "--->position=" + postion);
                        Intent intent = new Intent(getActivity(), XHItemBSQueryActivity.class);
                        intent.putExtra(XHItemBSQueryActivity.BUSHOU, datas.get(postion).getBushou());
                        startActivity(intent);
                    }
                });
            }else{
                img_bs_load.clearAnimation();
                img_bs_load.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };

    List<BS_Info> datas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.xh_bs_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initData() {
        img_bs_load.setVisibility(View.VISIBLE);
        A.openA(getActivity(), img_bs_load);
        handler.post(getDatas);
    }

    /**
     * GetData
     * 获取数据
     * 并通过sendMessage通知消息队列更新消息
     */
    Runnable getDatas = new Runnable() {
        @Override
        public void run() {
            Map<String, String> par = new HashMap<>();
            par.put("key", XHDictionaryActivity.XH_APPKEY);
            HttpRequest.post(HTTPURLS.bushou, (HashMap<String, String>) par, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    A.C_Log(getActivity(), "数据获取成功");
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
        setBS_Info info = new Gson().fromJson(result, setBS_Info.class);
        if(info.getError_code() != 0){
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }else{
            A.C_Log(getActivity(), "------>" + info.getReason());
            /**
             * 循环list集合
             * 遍历所有信息保存至对象中并添加给datas去显示
             */
            for (int i = 0; i < info.getResult().size(); i++){
                BS_Info obj = new BS_Info();
                obj.setId(info.getResult().get(i).getId());
                obj.setBihua(info.getResult().get(i).getBihua());
                obj.setBushou(info.getResult().get(i).getBushou());
                datas.add(obj);
            }
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }
    }

    private void initView() {
        xh_bs_gv = view.findViewById(R.id.xh_bs_gv);
        img_bs_load = view.findViewById(R.id.img_bs_load);
    }

}
