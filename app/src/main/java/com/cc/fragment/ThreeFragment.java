package com.cc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cc.R;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.Helper;

/**
 * Created by admin on 2018/4/10.
 */

public class ThreeFragment extends Fragment implements View.OnClickListener {

    View view;

    ListView three_listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_three, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(getContext(), R.color.cyan));
        initView();
//        initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(getContext(), R.color.cyan));
        }
    }

//    private void initData() {
////        List<User_Information> user = SQLiteDB.getInstance(getActivity()).selectInformation();
//        List<User_Information> users = SQLiteDB.getInstance(getActivity()).selectByIdInformation("1");
//        if(users.size() == 0){
//            return;
//        }
//        information_FragmentAdapter = new Information_FragmentAdapter(getActivity(), users, three_listview);
//        three_listview.setAdapter(information_FragmentAdapter);
//    }

    private void initView() {
        three_listview = view.findViewById(Helper.getResId(getActivity(), "three_listview"));
    }

    @Override
    public void onClick(View v) {

    }

}
