package com.cc.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.cc.R;
import com.cc.fragment.SMSblessFragment;
import com.cc.model.FestivalInfos;
import com.cc.model.SMSdetail;

/**
 * 点击节日显示的所有短信
 * cc
 */
public class SMSblessdetailActivity extends AppCompatActivity implements View.OnClickListener {

    ListView smsdetail_listView;

    FloatingActionButton smsdetail_fab;

    ArrayAdapter<SMSdetail> adapter;

    LayoutInflater inflater;

    int SMSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsblessdetail);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //实例化li
        inflater = LayoutInflater.from(this);
        //获取参数值
        SMSID = getIntent().getIntExtra(SMSblessFragment.SMSID, -1);

        /**
         * 通过adapter将数据绑定到item上
         */
        smsdetail_listView.setAdapter(adapter = new ArrayAdapter<SMSdetail>(this, -1, FestivalInfos.getmInstance().getSMSbyid(SMSID)){
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if(convertView == null){
                    convertView = inflater.inflate(R.layout.activity_smsdetail_item, parent, false);
                }
                TextView smsdetail_item_txt = convertView.findViewById(R.id.smsdetail_item_txt);
                Button smsdetail_bt = convertView.findViewById(R.id.smsdetail_bt);

                smsdetail_item_txt.setText(getItem(position).getSmsdetail());
                //通过发送按钮将内容传入详情界面
                smsdetail_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditSMSActivity.toActivity(SMSblessdetailActivity.this, SMSID, getItem(position).getId());
                    }
                });
                return  convertView;
            }
        });
    }

    /**
     * 实例化控件
     */
    private void initView() {
        smsdetail_listView = (ListView) findViewById(R.id.smsdetail_listView);
        smsdetail_fab = (FloatingActionButton) findViewById(R.id.smsdetail_fab);

        smsdetail_fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditSMSActivity.toActivity(SMSblessdetailActivity.this, SMSID, -1);
    }
}
