package com.cc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.R;

/**
 * Created by admin on 2018/7/18.
 */

public class HorizontalFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int index = getArguments().getInt("index");
        if(index == 0){
            view = inflater.inflate(R.layout.horizontal_one_fragment, null);
        }else if(index == 1){
            view = inflater.inflate(R.layout.horizontal_two_fragment, null);
        }
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

    }

}
