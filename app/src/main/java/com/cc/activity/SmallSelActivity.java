package com.cc.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.MapView;
import com.cc.R;
import com.cc.adapter.SmallSelAdapter;
import com.cc.dialog.CommomDialog;
import com.cc.model.SmallSelInfo;
import com.cc.services.GeTuiPushService;
import com.cc.services.getIntentService;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.cc.view.GridDividerItemDecoration;
import com.igexin.sdk.PushManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 小查询
 */
public class SmallSelActivity extends BaseActivity implements View.OnClickListener {

    RecyclerView small_rv;

    Activity activity = SmallSelActivity.this;

    ImageView small_img_back;

    List<SmallSelInfo> datas = new ArrayList<>();

    SmallSelAdapter adapter;

    ProgressDialog dwdialog;

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);

        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));

        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(activity, GeTuiPushService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(activity, getIntentService.class);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_small_sel;
    }

    @Override
    public void initView() {
        small_img_back = findViewById(R.id.small_img_back);
        small_rv = findViewById(R.id.small_rv);

        small_img_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        datas.add(new SmallSelInfo("0", R.mipmap.dt, "地图", "1"));
        datas.add(new SmallSelInfo("1", R.mipmap.dw, "定位", "1"));
        datas.add(new SmallSelInfo("2", R.mipmap.dh, "导航", "1"));
        datas.add(new SmallSelInfo("3", R.mipmap.dh, "导航", "1"));

        //设置RecyclerView的数据加载
        small_rv.setLayoutManager(new GridLayoutManager(activity, 2));
        small_rv.addItemDecoration(new GridDividerItemDecoration(activity));
        adapter = new SmallSelAdapter(activity, datas);
        small_rv.setAdapter(adapter);

        adapter.setLinster(new SmallSelAdapter.ItemOnClickLinster() {
            @Override
            public void textItemOnClick(View view, int position) {
                A.C_Log(activity, "position=" + position + ",name=" + datas.get(position).getName());

                if(datas.get(position).getId().equals("0")){
                    new CommomDialog(activity, R.style.dialog, "我的地图", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, String content) {

                        }

                    }, 4).show();
                }else if(datas.get(position).getId().equals("1")){
                    new CommomDialog(activity, R.style.dialog, "我的位置", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, String content) {
                            dialog.dismiss();
                            A.C_Log(activity, "get_position=" + content);
                            dwdialog = new ProgressDialog(activity);
                            dwdialog.setMessage("正在获取您的位置");
                            dwdialog.show();
                            //获取位置的方法
                            getPosition();
                        }
                    }, 5).show();
                }else if(datas.get(position).getId().equals("2")){

                }else if(datas.get(position).getId().equals("3")){

                }
            }
        });
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    /**
     * 获取位置的方法
     */
    private void getPosition() {
//初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        /* //设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景） 设置了场景就不用配置定位模式等
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null != locationClient){
            locationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            locationClient.stopLocation();
            locationClient.startLocation();
        }*/
        // 同时使用网络定位和GPS定位,优先返回最高精度的定位结果,以及对应的地址描述信息
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //只会使用网络定位
        /* mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);*/
        //只使用GPS进行定位
        /*mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);*/
        // 设置为单次定位 默认为false
        /*mLocationOption.setOnceLocation(true);*/
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。默认连续定位 切最低时间间隔为1000ms
        mLocationOption.setInterval(10000);
        //设置是否返回地址信息（默认返回地址信息）
        /*mLocationOption.setNeedAddress(true);*/
        //关闭缓存机制 默认开启 ，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存,不区分单次定位还是连续定位。GPS定位结果不会被缓存。
        /*mLocationOption.setLocationCacheEnable(false);*/
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (!isGpsEnabled(getApplicationContext())) {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.hasNotOpenGps), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        A.C_Log(activity, "定位成功");
                        dwdialog.dismiss();
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        double currentLat = amapLocation.getLatitude();//获取纬度
                        double currentLon = amapLocation.getLongitude();//获取经度
                        float sd = amapLocation.getSpeed();  //速度
                        String country = amapLocation.getCountry();  //国家
                        String province = amapLocation.getProvince();  //省份
                        String city = amapLocation.getCity();  //城市
                        String district = amapLocation.getDistrict(); //城区
                        String street = amapLocation.getStreet();  //街道
                        String cityCode = amapLocation.getCityCode();  //城市编码信息
                        String adCode = amapLocation.getAdCode();  //区域编码信息
                        A.C_Log(activity, "您当前位置的纬度（currentLat）是：" + currentLat + "，您当前位置的经度（currentLon）是：" + currentLon);
                        A.C_Log(activity, "您当前位于" + country + "" + province + "" + city + "" + district + "" + street + "" + cityCode + "" + adCode);
                        A.C_Log(activity, "您当前位置的风速是：" + sd);
                        amapLocation.getAccuracy();//获取精度信息
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        A.C_Log(activity, "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        }
    };


    /**
     * 判断GPS是否开启
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.small_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationClient!=null) {
            mLocationClient.onDestroy();//销毁定位客户端。
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mLocationClient!=null) {
            mLocationClient.startLocation(); // 启动定位
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationClient!=null) {
            mLocationClient.stopLocation();//停止定位
        }
    }

}
