package com.cc.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
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

import java.util.Random;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = RegisterActivity.this;

    EditText et_account, et_pass, et_compass;

    TextView txt_bt, txt_yhxy;

    CheckBox cb_check;

    String account, pass, compass;

    ImageView img_back;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));

    }

    @Override
    public int intiLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        img_back = findViewById(R.id.img_back);

        et_account = findViewById(Helper.getResId(activity, "et_account"));
        et_pass = findViewById(Helper.getResId(activity, "et_pass"));
        et_compass = findViewById(Helper.getResId(activity, "et_compass"));
        txt_bt = findViewById(Helper.getResId(activity, "txt_bt"));
        cb_check = findViewById(Helper.getResId(activity, "cb_check"));
        txt_yhxy = findViewById(Helper.getResId(activity, "txt_yhxy"));

        //为用户协议设置下划线
        txt_yhxy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        img_back.setOnClickListener(this);
        txt_bt.setOnClickListener(this);
        txt_yhxy.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    /**
     * 判断字符创是否为纯数字
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == Helper.getResId(activity, "img_back")){
            back();
        }else if(temdId == Helper.getResId(activity, "txt_bt")){
            account = et_account.getText().toString().trim();
            pass = et_pass.getText().toString().trim();
            compass = et_compass.getText().toString().trim();
            if(TextUtils.isEmpty(account)
                    || TextUtils.isEmpty(pass)
                    || TextUtils.isEmpty(compass)){
                Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "notnull")), Toast.LENGTH_SHORT).show();
                return;
            }
            if(pass.length() < 6 || compass.length() < 6 || account.length() < 6){
                Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "minsex")), Toast.LENGTH_SHORT).show();
                return;
            }
            if(isInteger(account)){
                Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "notinteger")), Toast.LENGTH_SHORT).show();
                return;
            }
            if(!pass.equals(compass)){
                Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "pass_inconsistent")), Toast.LENGTH_SHORT).show();
                return;
            }
            if(!cb_check.isChecked()){
                Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "select_yhxy")), Toast.LENGTH_SHORT).show();
                return;
            }
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在注册...");
            dialog.show();
            register(account, pass);
        }else if(temdId == Helper.getResId(activity, "txt_yhxy")){
            Intent intent = new Intent(activity, AgreementActivity.class);
            startActivity(intent);
            back();
        }
    }

    /**
     * 返回方法
     */
    private void back() {
        ActivityMan.getmInstance().finishActivity(activity);
    }

    /**
     * 通过随机数
     * @param leng
     * @return
     */
    private String getNo(int leng) {
        String ret = "";
        int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < leng; i++)
            result = result * 10 + array[i];
        ret = String.format("%d", result);
        return ret;
    }

    /**
     * 注册方法
     * @param account
     * @param pass
     */
    private void register(String account, String pass) {
        User_Account_Info user = new User_Account_Info();
        user.setUserid("1");
        user.setAccountid(getNo(8));
        user.setUsername(account);
        user.setPassword(pass);
        user.setPhonenum("11111111111");
        user.setLoginstate("1");
        A.C_Log(activity, user.toString());
        int register_result = SQLiteDB.getInstance(activity).saveUser(user);
        if(register_result == 1){
            A.C_Log(activity, "账号注册成功！已存储至数据库。");
            dialog.dismiss();
            back();
        }else {
            dialog.dismiss();
            Toast.makeText(activity, getResources().getString(Helper.getResStr(activity, "register_error")), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            back();
        }
        return false;
    }

}
