package com.cc.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.PopupWindow;

import com.cc.R;

public class MyPopWindow extends PopupWindow {

    Context context;

    public MyPopWindow(Context context) {
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.two_item_pup_list, null);
        setContentView(view);
        initWindow();
    }

    private void initWindow() {
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        this.setWidth((int) (d.widthPixels * 0.35));
        this.setHeight(AbsListView.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
    }

    public void showAtBottom(View view) {
        //弹窗位置设置
        showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 10);
        //showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 10, 110);//有偏差
    }

}
