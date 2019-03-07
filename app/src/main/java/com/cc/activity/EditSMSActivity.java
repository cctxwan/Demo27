package com.cc.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cc.R;
import com.cc.controller.SMSController;
import com.cc.model.Festival;
import com.cc.model.FestivalInfos;
import com.cc.model.SMSdetail;
import com.cc.utils.A;
import com.cc.view.FlowLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * 点击节日显示的一条短信内容（可编辑，可发送）
 */
public class EditSMSActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity = EditSMSActivity.this;

    //常量
    private static final int CODE_REQUEST = 1;

    static String FID = "fid";

    static String MID = "mid";

    int fid;

    int mid;

    EditText editsms_et;

    Button editsms_bt;

    FlowLayout editsms_fl;

    FloatingActionButton editsms_fab;

    Festival festival;

    SMSdetail smSdetail;

    FrameLayout load_ly;

    //存储获取到的联系人名称
    Set<String> cNames = new HashSet<>();

    //存储获取到的手机号
    Set<String> cNums = new HashSet<>();

    LayoutInflater inflater;

    //
    public static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    public static final String ACTION_DELIVER_MSG = "ACTION_DELIVER_MSG";

    PendingIntent spi;
    PendingIntent dpi;

    BroadcastReceiver sendbr;
    BroadcastReceiver deliverbr;

    SMSController smsController = new SMSController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sms);
        inflater = LayoutInflater.from(this);
        initView();
        initData();
        initRecivers();
    }

    /**
     * 广播
     */
    private void initRecivers() {
        Intent send_intent = new Intent(ACTION_SEND_MSG);
        spi = PendingIntent.getBroadcast(this, 0, send_intent, 0);
        Intent deliver_intent = new Intent(ACTION_DELIVER_MSG);
        dpi = PendingIntent.getBroadcast(this, 0, deliver_intent, 0);

        //注册广播
        registerReceiver(sendbr = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(getResultCode() == RESULT_OK){
                    load_ly.setVisibility(View.GONE);
                    A.C_Log(activity, "短信发送成功");
                }else{
                    A.C_Log(activity, "短信发送失败");
                }
            }
        }, new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(deliverbr = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(getResultCode() == RESULT_OK){
                    A.C_Log(activity, "短信已成功收到");
                }
            }
        }, new IntentFilter(ACTION_DELIVER_MSG));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sendbr);
        unregisterReceiver(deliverbr);
    }

    /**
     * 获取联系人的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //有值
        if(requestCode == CODE_REQUEST){
            //有值
            if(resultCode == RESULT_OK){
                //通过data取值
                Uri uri = data.getData();

                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                String phoneNum = getcNums(cursor);
                if(!TextUtils.isEmpty(phoneNum)){
                    cNums.add(phoneNum);
                    cNames.add(cName);

                    addTag(cName);
                }
            }
        }
    }

    /**
     * 去构造textView并加入到fl中去
     * @param cName
     */
    private void addTag(String cName) {
        TextView view = (TextView) inflater.inflate(R.layout.activity_sms_detail_phonenum, editsms_fl, false);
        view.setText(cName);
        editsms_fl.addView(view);
    }

    /**
     * 获取联系人的手机号码
     * @param cursor
     * @return
     */
    private String getcNums(Cursor cursor) {
        //定义一个numb值
        String numb = null;
        //这里应该是获取联系人
        int num = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        if(num > 0){
            //获取联系人的id
            int cNum = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor query = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + cNum, null, null);
            query.moveToFirst();
            //拿到手机号码
            numb = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //关闭
            query.close();
        }
        cursor.close();
        return numb;
    }

    /**
     * 实例化控件
     */
    private void initView() {
        editsms_et = (EditText) findViewById(R.id.editsms_et);
        editsms_fl = (FlowLayout) findViewById(R.id.editsms_fl);
        editsms_fab = (FloatingActionButton) findViewById(R.id.editsms_fab);
        editsms_bt = (Button) findViewById(R.id.editsms_bt);
        load_ly = (FrameLayout) findViewById(R.id.load_ly);

        load_ly.setVisibility(View.GONE);
        editsms_bt.setOnClickListener(this);
        editsms_fab.setOnClickListener(this);
    }

    /**
     * get数据
     */
    private void initData() {
        fid = getIntent().getIntExtra(FID, -1);
        mid = getIntent().getIntExtra(MID, -1);

        //如果是-1，说明是点击了发送按钮（无内容的）
        if(mid != -1){
            //通过mid，去节日信息对象里面去指定对象，并显示在et上
            smSdetail = FestivalInfos.getmInstance().getBySMSId(mid);
            editsms_et.setText(smSdetail.getSmsdetail());
        }
    }

    /**
     * 跳转至本activity
     * @param context
     * @param fid
     * @param mid
     */
    public static void toActivity(Context context, int fid, int mid){
        Intent intent = new Intent(context, EditSMSActivity.class);
        intent.putExtra(FID, fid);
        intent.putExtra(MID, mid);
        context.startActivity(intent);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.editsms_bt){
            //去获取联系人并返回结果
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, CODE_REQUEST);
        }else if(temdId == R.id.editsms_fab){
            //发送短信
            if(cNums.size() == 0){
                A.C_Log(activity, "联系人不能为空");
                return;
            }
            String msg = editsms_et.getText().toString().trim();
            if(TextUtils.isEmpty(msg)){
                A.C_Log(activity, "短信内容不能为空");
                return;
            }
            load_ly.setVisibility(View.VISIBLE);
            smsController.sendMsg(cNums, msg, spi, dpi);
        }
    }
}
