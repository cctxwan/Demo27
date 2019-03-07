package com.cc.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.adapter.ZGByStrAdapter;
import com.cc.adapter.ZGTypeAdapter;
import com.cc.model.ZGByStrInfo;
import com.cc.model.ZGTypeInfo;
import com.cc.model.setZGByStrInfo;
import com.cc.model.setZGTypeInfo;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.cc.view.GridDividerItemDecoration;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 周公解梦
 */
public class ZGDreamActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = ZGDreamActivity.this;

    public static final String ZGJM_APPKEY = "bcfbafdf7870ece5d581b1f2400160bf";

    public static int SUCC_CODE = 0;

    /** 0代表请求梦境类型。 1代表根据关键字请求内容。 2代表根据id请求内容 */
    public static int GETDATAS = -1;

    public static boolean isshow = false;

    ImageView zgjm_img_back, zgjm_img_zk, zgjm_img_load, zgjmbystring_img_load;

    TextView zgjm_txt_search;

    EditText zgjm_et_string;

    String et_string;

    LinearLayout zgjm_lin_top, zgjm_lin_zk;

    RelativeLayout rel_bystr;

    PopupWindow top_windows;

    List<ZGTypeInfo> type_datas = new ArrayList<>();
    List<ZGByStrInfo> str_datas = new ArrayList<>();

    RecyclerView zgjm_type_rv, zgjmbystring_rv;

    ZGTypeAdapter adapter;

    ZGByStrAdapter adapter2;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(GETDATAS == 0){
                if(msg.arg1 == SUCC_CODE){
                    A.C_Log(activity, "请求成功");
                    zgjm_img_load.clearAnimation();
                    zgjm_img_load.setVisibility(View.GONE);
                    zgjm_type_rv.setLayoutManager(new GridLayoutManager(activity, 4));
                    zgjm_type_rv.addItemDecoration(new GridDividerItemDecoration(activity));
                    adapter = new ZGTypeAdapter(activity, type_datas);
                    zgjm_type_rv.setAdapter(adapter);
                    //展开列表适配器
                    adapter.setLinster(new ZGTypeAdapter.ItemOnClickLinster() {
                        @Override
                        public void textItemOnClick(View view, int position) {
                            et_string = type_datas.get(position).getName();
                            //根据关键字去获取信息
                            new Thread(getSelByString).start();
                        }
                    });
                }else{
                    zgjm_img_load.clearAnimation();
                    zgjm_img_load.setVisibility(View.GONE);
                    Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
                }
            }else if(GETDATAS == 1){
                if(msg.arg1 == SUCC_CODE){
                    A.C_Log(activity, "请求成功");
                    rel_bystr.setVisibility(View.VISIBLE);
                    zgjmbystring_img_load.clearAnimation();
                    zgjmbystring_img_load.setVisibility(View.GONE);
                    zgjmbystring_rv.setLayoutManager(new LinearLayoutManager(activity));
                    adapter2 = new ZGByStrAdapter(activity, str_datas);
                    zgjmbystring_rv.setAdapter(adapter2);
                    //查询关键字适配器
                    adapter2.setLinster(new ZGByStrAdapter.ItemOnClickLinster() {
                        @Override
                        public void textItemOnClick(View view, int position) {

                        }
                    });
                }else{
                    zgjmbystring_img_load.clearAnimation();
                    zgjmbystring_img_load.setVisibility(View.GONE);
                    Toast.makeText(activity, R.string.getDataError, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        setTitle(false);

        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
        
        initView();
        initData();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_zgdream;
    }

    @Override
    public void initView() {
        zgjm_img_back = findViewById(R.id.zgjm_img_back);
        zgjm_lin_top = findViewById(R.id.zgjm_lin_top);
        zgjm_et_string = findViewById(R.id.zgjm_et_string);
        zgjm_img_zk = findViewById(R.id.zgjm_img_zk);
        zgjm_lin_zk = findViewById(R.id.zgjm_lin_zk);
        zgjm_txt_search = findViewById(R.id.zgjm_txt_search);

        zgjmbystring_img_load = findViewById(R.id.zgjmbystring_img_load);
        rel_bystr = findViewById(R.id.rel_bystr);
        zgjmbystring_rv = findViewById(R.id.zgjmbystring_rv);


        zgjm_img_back.setOnClickListener(this);
        zgjm_lin_zk.setOnClickListener(this);
        zgjm_txt_search.setOnClickListener(this);


        zgjm_et_string.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听
                A.C_Log(activity, "输入前的监听");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                A.C_Log(activity, "输入的内容变化的监听。start=" + start + "before=" + before + "count=" + count);
                if(start == 0){
                    rel_bystr.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                A.C_Log(activity, "输入后的监听");
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.zgjm_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        } else if(temdId == R.id.zgjm_lin_zk){
            rel_bystr.setVisibility(View.GONE);
            //展开梦境类型
            if(isshow){
                isshow = false;
                zgjm_img_zk.setImageDrawable(getResources().getDrawable(R.mipmap.shouqi));
                top_windows.dismiss();
                return;
            } else {
                isshow = true;
                zgjm_img_zk.setImageDrawable(getResources().getDrawable(R.mipmap.zhankai));
                openPopWindows();
            }
        } else if(temdId == R.id.zgjm_txt_search){
            zgjmbystring_img_load.setVisibility(View.VISIBLE);
            A.openA(activity, zgjmbystring_img_load);
            //根据关键字搜索
            et_string = zgjm_et_string.getText().toString().trim();
            if(checkNameChese(et_string) == false){
                Toast.makeText(activity, getResources().getText(R.string.inputC), Toast.LENGTH_SHORT).show();
                return;
            }
            //根据关键字去获取信息
            new Thread(getSelByString).start();
        }
    }

    /**
     * 客户端验证姓名
     * @param name
     * @return
     */
    public boolean checkNameChese(String name) {
        System.out.println("--------------------" + name);
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 验证是否为汉字
     * @param c
     * @return
     */
    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 根据关键字查询的
     */
    Runnable getSelByString = new Runnable() {
        @Override
        public void run() {
            GETDATAS = 1;
            Map<String, String> par = new HashMap<>();
            par.put("key", ZGJM_APPKEY);
            par.put("q", et_string);
            HttpRequest.post(HTTPURLS.zgjm_bystring, (HashMap<String, String>) par, new Callback() {
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

    //打开一个pop用来展示梦境类型
    private void openPopWindows() {
        View view = LayoutInflater.from(activity).inflate(R.layout.zgjm_type_list, null);
        zgjm_type_rv = view.findViewById(R.id.zgjm_type_rv);
        zgjm_img_load = view.findViewById(R.id.zgjm_img_load);
        getTypes();
        top_windows = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        top_windows.setBackgroundDrawable(new ColorDrawable());
        top_windows.setTouchable(true);
        top_windows.setOutsideTouchable(false);
        top_windows.showAsDropDown(zgjm_lin_top);

        top_windows.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=1.0f;
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });

        //设置背景色
        top_windows.showAtLocation(zgjm_lin_top, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 通过网络获取类型
     */
    private void getTypes() {
        zgjm_img_load.setVisibility(View.VISIBLE);
        A.openA(activity, zgjm_img_load);
        new Thread(runnable).start();
    }

    /**
     * getdatas
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            GETDATAS = 0;
            Map<String, String> par = new HashMap<>();
            par.put("key", ZGJM_APPKEY);
            HttpRequest.post(HTTPURLS.zgjm_type, (HashMap<String, String>) par, new Callback() {
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
        if(GETDATAS == 0){
            setZGTypeInfo info = new Gson().fromJson(result, setZGTypeInfo.class);
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
                    ZGTypeInfo obj = new ZGTypeInfo();
                    obj.setId(info.getResult().get(i).getId());
                    obj.setFid(info.getResult().get(i).getFid());
                    obj.setName(info.getResult().get(i).getName());
                    type_datas.add(obj);
                }
                message.arg1 = info.getError_code();
                handler.sendMessage(message);
            }
        }else if(GETDATAS == 1){
            str_datas.clear();
            setZGByStrInfo info = new Gson().fromJson(result, setZGByStrInfo.class);
            if(info.getError_code() != 0){
                message.arg1 = info.getError_code();
                handler.sendMessage(message);
            }else{
                A.C_Log(activity, "------>" + info.getReason());
                if(info.getResult() == null) return;
                if(info.getResult().size() < 0){
                    Toast.makeText(activity, getResources().getText(R.string.getdata_bystr), Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * 循环list集合
                 * 遍历所有信息保存至对象中并添加给datas去显示
                 */
                for (int i = 0; i < info.getResult().size(); i++){
                    ZGByStrInfo obj = new ZGByStrInfo();
                    obj.setId(info.getResult().get(i).getId());
                    obj.setTitle(info.getResult().get(i).getTitle());
                    obj.setDes(info.getResult().get(i).getDes());
                    str_datas.add(obj);
                }
                message.arg1 = info.getError_code();
                handler.sendMessage(message);
            }
        }
    }

}
