package com.cc.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cc.R;
import com.cc.db.SQLiteDB;
import com.cc.model.User_Account_Info;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.cc.utils.Helper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.mob.MobSDK;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.HashMap;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    Activity activity = LoginActivity.this;

    TextView txt_bt, txt_accountreg, txt_phonereg;

    ImageView img_close, img_getpass, img_qq, img_back, img_fb, img_wx;

    EditText et_account, et_pass;

    String account, password;

    ProgressDialog dialog;

    //QQ登录
    public static String QQ_APP_ID = "101496221";
    public static Tencent mTencent;
    CallbackManager callbackManager;

    //微信登录
    public static final String WX_APP_ID = "wxdaf01e3da90f40a9";
    public static final String WX_APP_SERECET = "wxdaf01e3da90f40a9";
    public static IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
        //注册QQ
        registerQQ();

        //注册微信
        registerWX();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        img_back = findViewById(R.id.img_back);

        et_account = findViewById(R.id.et_account);
        et_pass = findViewById(R.id.et_pass);
        img_close = findViewById(R.id.img_close);
        img_getpass = findViewById(R.id.img_getpass);
        txt_bt = findViewById(R.id.txt_bt);
        txt_accountreg = findViewById(R.id.txt_accountreg);
        txt_phonereg = findViewById(R.id.txt_phonereg);
        img_qq = findViewById(R.id.img_qq);
        img_fb = findViewById(R.id.img_fb);
        img_wx = findViewById(R.id.img_wx);


        img_back.setOnClickListener(this);
        img_close.setOnClickListener(this);
        img_getpass.setOnClickListener(this);
        txt_bt.setOnClickListener(this);
        txt_accountreg.setOnClickListener(this);
        txt_phonereg.setOnClickListener(this);
        img_qq.setOnClickListener(this);
        img_fb.setOnClickListener(this);
        img_wx.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTencent = Tencent.createInstance(QQ_APP_ID, this);
    }

    /**
     * wx
     */
    private void registerWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        iwxapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        // 将该app注册到微信
        iwxapi.registerApp(WX_APP_ID);
    }

    /**
     * QQ
     */
    private void registerQQ() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        A.C_Log(activity, "success");
                        Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "login_succ")),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        A.C_Log(activity, "cancel");
                        Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "login_cancel")),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        A.C_Log(activity, "error");
                        Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "login_fail")),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.img_back){
            back();
        }else if(temdId == R.id.img_close){
            et_account.setText("");
            et_pass.setText("");
        }else if(temdId == R.id.img_getpass){

        }else if(temdId == R.id.txt_bt){
            account = et_account.getText().toString().trim();
            password = et_pass.getText().toString().trim();
            if(TextUtils.isEmpty(account)
                    || TextUtils.isEmpty(password)){
                Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "notnull")),Toast.LENGTH_SHORT).show();
                return;
            }
            if(account.length() < 6 || password.length() < 6){
                Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "minsex")),Toast.LENGTH_SHORT).show();
                return;
            }
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在登录");
            dialog.show();
            User_Account_Info info = new User_Account_Info();
            selLoginObj(account, password);
        }else if(temdId == R.id.txt_accountreg){
            Intent intent = new Intent(activity, RegisterActivity.class);
            startActivity(intent);
            back();
        }else if(temdId == R.id.txt_phonereg){
            getCode();
        }else if(temdId == R.id.img_qq){
            A.C_Log(activity, "QQ_LOGIN");
            mTencent.login(this, "all", loginListener);
        }else if(temdId == R.id.img_wx){
            A.C_Log(activity, "WX_LOGIN");
            WXLogin();
        }else if(temdId == R.id.img_fb){
            A.C_Log(activity, "FB_LOGIN");
            onResume();
            LoginManager.getInstance().
                    logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
        }
    }

    /**
     * 登录微信
     */
    private void WXLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        iwxapi.sendReq(req);
    }

    //监听登录
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            A.C_Log(activity, "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 登录返回的参数
     * @param jsonObject
     */
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * QQ登录监听器
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Toast.makeText(activity, R.string.qqlogin_error, Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(activity, R.string.qqlogin_error, Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(activity, R.string.qqlogin_succ, Toast.LENGTH_SHORT).show();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            A.C_Log(activity, "onError: " + e.errorDetail);
        }

        @Override
        public void onCancel() {
            A.C_Log(activity, "onCancel: ");
        }
    }

    /**
     * 返回方法
     */
    private void back() {
        ActivityMan.getmInstance().finishActivity(LoginActivity.class);
    }

    /**
     * 保存登录对象到数据库实例
     * @verser 2018年7月26日14:32:55
     * @param account
     * @param password
     */
    private void selLoginObj(String account, String password) {
        User_Account_Info info = SQLiteDB.getInstance(activity).selectByLogin(account, password);
        if(info != null){
            dialog.dismiss();
        } else {
            Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "login_error")),Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
		/*
		移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return TextUtils.isEmpty(mobiles) ? false : mobiles.matches(telRegex);
    }

    /**
     * 获取验证码
     * @return
     */
    public void getCode() {
        MobSDK.init(activity);
        // 打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setTempCode(null);
        // 使用自定义短信模板(不设置则使用默认模板)
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    A.C_Log(activity, "获取验证码的手机号为：" + phone + "------>" + country);
                }
            }
        });
        registerPage.show(this);
    };

    protected void onStop() {
        super.onStop();
        //用完回调要注销掉，否则可能会出现内存泄露
    }

    @Override
    public boolean handleMessage(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        if (result == SMSSDK.RESULT_COMPLETE) {
            if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                //提交验证码成功
                A.C_Log(activity, "isSmart=666");
                SMSSDK.unregisterAllEventHandler();
            }else if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                boolean isSmart = (boolean) data;
                A.C_Log(activity, "isSmart=" + isSmart);
            }
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            back();
        }
        return false;
    }
}