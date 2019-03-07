package com.cc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 自定义listview
 */
public class MyREListView extends ListView implements AbsListView.OnScrollListener, View.OnTouchListener {
    public MyREListView(Context context) {
        super(context);
    }

    public MyREListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyREListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                return true;
            default:
                break;
        }
        return true;
    }

    /**
     *监听着ListView的滑动状态改变。官方的有三种状态SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING、SCROLL_STATE_IDLE：
     * SCROLL_STATE_TOUCH_SCROLL：手指正拖着ListView滑动
     * SCROLL_STATE_FLING：ListView正自由滑动
     * SCROLL_STATE_IDLE：ListView滑动后静止
     * */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * firstVisibleItem: 表示在屏幕中第一条显示的数据在adapter中的位置
     * visibleItemCount：则表示屏幕中最后一条数据在adapter中的数据，
     * totalItemCount则是在adapter中的总条数
     * */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

}
