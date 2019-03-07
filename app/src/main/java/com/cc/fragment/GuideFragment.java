package com.cc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cc.R;
import com.cc.activity.MainActivity;
import com.cc.utils.Helper;

/**
 * Created by admin on 2018/7/18.
 */

public class GuideFragment extends Fragment {

    private int[] bgImage = {
            R.mipmap.guide_bg_4,
            R.mipmap.guide_bg_2,
            R.mipmap.guide_bg_3,
            R.mipmap.guide_bg_1,};

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(Helper.getLayoutId(getActivity(), "guide_fragment"), null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        TextView guide_bt = view.findViewById(Helper.getResId(getActivity(), "guide_bt"));
        guide_bt.setBackgroundResource(Helper.getResDraw(getActivity(), "guide_txt"));
        guide_bt.setText(getActivity().getResources().getString(Helper.getResStr(getActivity(), "guide_begin")));
        RelativeLayout guide_rel = view.findViewById(Helper.getResId(getActivity(), "guide_rel"));

        int index = getArguments().getInt("index");
        guide_rel.setBackgroundResource(bgImage[index]);
        guide_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });
        guide_bt.setVisibility(index == 3 ? View.VISIBLE : View.GONE);
    }

    /**
     * 跳往主页面
     */
    void toMainActivity(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

}
