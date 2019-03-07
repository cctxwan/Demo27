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
import com.cc.activity.XHItemPYQueryActivity;
import com.cc.adapter.PYFragAdapter;
import com.cc.model.PY_Info;
import com.cc.model.setPY_Info;
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

public class XHItemPYFragment extends Fragment {

    View view;

    static final int SUCC_CODE = 0;

    GridView xh_py_gv;

    ImageView img_py_load;

    PYFragAdapter adapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE){
                A.C_Log(getActivity(), "请求成功");
                img_py_load.clearAnimation();
                img_py_load.setVisibility(View.GONE);
                //通过适配器去加载数据到RV上
                adapter = new PYFragAdapter(getActivity(), datas);
                xh_py_gv.setAdapter(adapter);
                adapter.setOnItemClickLiester(new PYFragAdapter.onItemClickLiester() {
                    @Override
                    public void succ(View view, int postion) {
                        A.C_Log(getActivity(), "--->position=" + postion);
                        Intent intent = new Intent(getActivity(), XHItemPYQueryActivity.class);
                        intent.putExtra(XHItemPYQueryActivity.PINYIN, datas.get(postion).getPinyin());
                        startActivity(intent);
                    }
                });
            }else{
                img_py_load.clearAnimation();
                img_py_load.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };

    List<PY_Info> datas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.xh_py_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initData() {
        img_py_load.setVisibility(View.VISIBLE);
        A.openA(getActivity(), img_py_load);
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
            HttpRequest.post(HTTPURLS.pinyin, (HashMap<String, String>) par, new Callback() {
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
        setPY_Info info = new Gson().fromJson(result, setPY_Info.class);
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
                PY_Info obj = new PY_Info();
                obj.setId(info.getResult().get(i).getId());
                obj.setPinyin(info.getResult().get(i).getPinyin());
                obj.setPinyin_key(info.getResult().get(i).getPinyin_key());
                datas.add(obj);
            }
            message.arg1 = info.getError_code();
            handler.sendMessage(message);
        }
    }

    private void initView() {
        xh_py_gv = view.findViewById(R.id.xh_py_gv);
        img_py_load = view.findViewById(R.id.img_py_load);
    }

}
