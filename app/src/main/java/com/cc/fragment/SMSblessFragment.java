package com.cc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.cc.R;
import com.cc.activity.SMSblessdetailActivity;
import com.cc.model.FestivalInfos;
import com.cc.model.Festival;
import com.cc.utils.A;

/**
 * 加载节日并显示在gridview的fragment
 * 继承Fragment是v4的
 * 实现item的点击事件
 */
public class SMSblessFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static String SMSID = "id";

    GridView sms_gridView;

    TextView sms_item_txt;

    LayoutInflater inflater;

    ArrayAdapter<Festival> adapter;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这里加载的xml是用来显示节日的（GridView），可以根据自己的需求改变布局文件
        view = inflater.inflate(R.layout.fragment_smsbless, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //实例化lt
        inflater = LayoutInflater.from(getActivity());
        initView();
        initData();
    }

    /**
     * 通过adapter加载出来节日
     */
    private void initData() {
        sms_gridView.setAdapter(adapter = new ArrayAdapter<Festival>(getActivity(), -1, FestivalInfos.getmInstance().getInfos()){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if(convertView == null){
                    convertView = inflater.inflate(R.layout.fragment_smsbless_item, parent, false);
                }

                sms_item_txt = convertView.findViewById(R.id.sms_item_txt);
                sms_item_txt.setText(getItem(position).getName());

                return convertView;
            }
        });
    }

    /**
     * 实例化控件
     */
    private void initView() {
        //实例化gridview控件
        sms_gridView = view.findViewById(R.id.sms_gridView);

        sms_gridView.setOnItemClickListener(this);
    }

    /**
     * 点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SMSblessdetailActivity.class);
        A.C_Log(getActivity(), adapter.getItem(position).getId() + "");
        intent.putExtra(SMSID, adapter.getItem(position).getId());
        startActivity(intent);
    }
}
