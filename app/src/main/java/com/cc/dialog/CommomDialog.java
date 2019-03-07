package com.cc.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.cc.HTTP.HTTPURLS;
import com.cc.HTTP.HttpRequest;
import com.cc.R;
import com.cc.activity.JokesActivity;
import com.cc.model.JokesNew;
import com.cc.model.SJJokeNow;
import com.cc.utils.A;
import com.cc.utils.Helper;
import com.google.gson.Gson;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 封装的dialog
 */
public class CommomDialog extends Dialog implements View.OnClickListener{

    //切换图像控件
    private TextView photo;
    private TextView album;


    //手机号注册确认发送框控件
    TextView phonedialog_content;
    TextView phonedialog_cancel;
    TextView phonedialog_submit;

    static final int SUCC_CODE = 0;


    //每日笑话
    ImageView img_todayjoke_close;
    TextView txt_todayjoke_content, txt_todayjoke_time, txt_change;

    //地图定位-我的地图
    ImageView img_mymap_close;
    TextView txt_mymap_name;
    MapView web_mymap;
    private boolean followMove=true;

    //地图定位-我的位置
    ImageView img_position_close, img_position_get;
    TextView txt_position_name;
    MapView web_myposition;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == SUCC_CODE){
                A.C_Log((Activity) mContext, "加载成功");
                JokesNew info = (JokesNew) msg.obj;
                txt_todayjoke_content.setText(info.getContent());
            }else{
                Toast.makeText(mContext, R.string.getDataError, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String title;
    private String content;
    private Context mContext;
    private OnCloseListener listener;
    private OnClose2Listener listener2;
    private int num;

    public CommomDialog(Context context, int themeResId, OnCloseListener listener, int num) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.num = num;
    }

    public CommomDialog(Context context, int themeResId, String title, OnCloseListener listener, int num) {
        super(context, themeResId);
        this.title = title;
        this.mContext = context;
        this.listener = listener;
        this.num = num;
    }

    public CommomDialog(Context context, int themeResId, String title, OnClose2Listener listener2, int num) {
        super(context, themeResId);
        this.title = title;
        this.mContext = context;
        this.listener2 = listener2;
        this.num = num;
    }

    public CommomDialog(Context context, int themeResId, String title, String content, OnCloseListener listener, int num) {
        super(context, themeResId);
        this.title = title;
        this.mContext = context;
        this.listener = listener;
        this.content = content;
        this.num = num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(num == 1){
            setContentView(Helper.getLayoutId(mContext, "dialog_commom"));
            setCanceledOnTouchOutside(false);
            initView(savedInstanceState);
        }else if(num == 2){
            setContentView(Helper.getLayoutId(mContext, "getcode_dialog"));
            setCanceledOnTouchOutside(false);
            initView(savedInstanceState);
        }else if(num == 3){
            setContentView(Helper.getLayoutId(mContext, "todayjoke_dialog"));
            setCanceledOnTouchOutside(false);
            initView(savedInstanceState);
        }else if(num == 4){
            setContentView(Helper.getLayoutId(mContext, "my_map"));
            setCanceledOnTouchOutside(false);
            initView(savedInstanceState);
        }else if(num == 5){
            setContentView(Helper.getLayoutId(mContext, "my_position"));
            setCanceledOnTouchOutside(false);
            initView(savedInstanceState);
        }
    }

    private void initView(Bundle savedInstanceState){
        if(num == 1){
            photo = findViewById(Helper.getResId(mContext, "photo"));
            album = findViewById(Helper.getResId(mContext, "album"));
            photo.setOnClickListener(this);
            album.setOnClickListener(this);
        }else if(num == 2){
            phonedialog_content = findViewById(Helper.getResId(mContext, "phonedialog_content"));
            phonedialog_cancel = findViewById(Helper.getResId(mContext, "phonedialog_cancel"));
            phonedialog_submit = findViewById(Helper.getResId(mContext, "phonedialog_submit"));


            phonedialog_content.setText(title == null ? "无法获取内容" : title);


            phonedialog_cancel.setOnClickListener(this);
            phonedialog_submit.setOnClickListener(this);
        }else if(num == 3){
            img_todayjoke_close = findViewById(R.id.img_todayjoke_close);
            txt_todayjoke_content = findViewById(R.id.txt_todayjoke_content);
            txt_todayjoke_time = findViewById(R.id.txt_todayjoke_time);
            txt_change = findViewById(R.id.txt_change);

            txt_todayjoke_time.setText(content);
            txt_todayjoke_content.setText(title);

            img_todayjoke_close.setOnClickListener(this);
            txt_change.setOnClickListener(this);
        }else if(num == 4){
            img_mymap_close = findViewById(R.id.img_mymap_close);
            web_mymap = findViewById(R.id.web_mymap);
            txt_mymap_name = findViewById(R.id.txt_mymap_name);

            txt_mymap_name.setText(title);
            img_mymap_close.setOnClickListener(this);
            //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
            web_mymap.onCreate(savedInstanceState);
            final AMap aMap = web_mymap.getMap();
            UiSettings mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
            //显示地图指南针
            mUiSettings.setCompassEnabled(true);
            mUiSettings.setScaleControlsEnabled(true);//比例尺
            mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);

            MyLocationStyle myLocationStyle;
            //初始化定位蓝点样式类
            myLocationStyle = new MyLocationStyle();
            //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            myLocationStyle.interval(2000);
            //设置定位蓝点的Style
            aMap.setMyLocationStyle(myLocationStyle);
            //设置默认定位按钮是否显示，非必需设置。
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            aMap.setMyLocationEnabled(true);
            aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    if (followMove) {
                        aMap.animateCamera(CameraUpdateFactory.newLatLng(aMap.getCameraPosition().target));
                    }
                }
            });
            aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
                @Override
                public void onTouch(MotionEvent motionEvent) {
                    if (followMove) {
                        //用户拖动地图后，不再跟随移动，直到用户点击定位按钮
                        followMove = false;
                    }
                }
            });
        }else if(num == 5){
            txt_position_name = findViewById(R.id.txt_position_name);
            img_position_close = findViewById(R.id.img_position_close);
            web_myposition = findViewById(R.id.web_myposition);
            img_position_get = findViewById(R.id.img_position_get);

            img_position_get.setOnClickListener(this);
            img_position_close.setOnClickListener(this);
            txt_position_name.setText(title);

            //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
            web_myposition.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onClick(View v) {
        if(num == 1){
            switch (v.getId()){
                case R.id.photo:
                    if(listener != null){
                        listener.onClick(this, "photo");
                    }
                    break;
                case R.id.album:
                    if(listener != null){
                        listener.onClick(this, "album");
                    }
                    break;
            }
        }else if(num == 2){
            switch (v.getId()){
                case R.id.phonedialog_submit:
                    if(listener != null){
                        listener.onClick(this, "submit");
                    }
                    break;
                case R.id.phonedialog_cancel:
                    if(listener != null){
                        dismiss();
                    }
                    break;
            }
        }else if(num == 3){
            switch (v.getId()){
                case R.id.img_todayjoke_close:
                    dismiss();
                    break;
                case R.id.txt_change:
                    A.C_Log((Activity) mContext, "换一换");
                    handler.post(getData);
                    break;
            }
        }else if(num == 4){
            switch (v.getId()){
                case R.id.img_mymap_close:
                    dismiss();
                    break;
            }
        }else if(num == 5){
            switch (v.getId()){
                case R.id.img_position_close:
                    dismiss();
                    break;
                case R.id.img_position_get:
                    if(listener != null){
                        listener.onClick(this, "get_position");
                    }
            }
        }
    }

    Runnable getData = new Runnable() {
        @Override
        public void run() {
            HttpRequest.get(HTTPURLS.joke_get + "&key=" + JokesActivity.Joke_APPKEY, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    A.C_Log((Activity) mContext, "数据获取成功");
                    String result = response.body().string();
                    JsonDta(result);
                }
            });
        }
    };

    /**
     * 解析json
     * @param result
     */
    private void JsonDta(String result) {
        Message message = handler.obtainMessage();
        SJJokeNow obj = new Gson().fromJson(result, SJJokeNow.class);
        if(obj.getError_code() != 0){
            message.arg1 = obj.getError_code();
            handler.sendMessage(message);
        }else {
            A.C_Log((Activity) mContext, "------>" + obj.getReason());
            JokesNew info = null;
            for (int i = 0; i < obj.getResult().size(); i ++){
                info = new JokesNew();
                info.setHashId(obj.getResult().get(i).getHashId());
                info.setContent(obj.getResult().get(i).getContent());
                info.setUnixtime(obj.getResult().get(i).getUnixtime());
            }
            message.arg1 = obj.getError_code();
            message.obj = info;
            handler.sendMessage(message);
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, String content);
    }

    public interface OnClose2Listener{
        void onClick(Dialog dialog, MapView view);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
