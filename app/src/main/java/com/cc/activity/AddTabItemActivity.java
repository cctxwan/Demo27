package com.cc.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.cc.R;
import com.cc.adapter.AddAllTabItemGVAdapter;
import com.cc.adapter.AddMyTabItemGVAdapter;
import com.cc.fragment.TwoFragment;
import com.cc.model.Add_AllTabItemInfo;
import com.cc.model.Add_MyTabItemInfo;
import com.cc.systemstatusbar.StatusBarCompat;
import com.cc.utils.A;
import com.cc.utils.ActivityMan;
import com.cc.view.MyGridView;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个activity主要用来加载twofrag里面的tablayout的item
 * 动态与数据库交互，并可选择tabitem（新增，删除）进行操作
 */
public class AddTabItemActivity extends BaseActivity implements
        View.OnClickListener,
        AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener {

    //当前选中的gv的item，，默认为0
    public static int SELECTEDFRAGMENT = 0;

    Activity activity = AddTabItemActivity.this;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    List<Add_MyTabItemInfo> datas = new ArrayList<>();
    List<Add_AllTabItemInfo> add_datas = new ArrayList<>();

    ImageView add_tabitem_img_back;
    TextView add_tabitem_txt_mytab, add_tabitem_txt_select, add_tabitem_txt_edit, add_tabitem_txt_alltab, add_tabitem_txt_add;
    MyGridView add_mytabitem_gridview, add_alltabitem_gridview;

    AddMyTabItemGVAdapter addMyTabItemGVAdapter;
    AddAllTabItemGVAdapter addAllTabItemGVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(false);
        StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.cyan));
        A.C_Log(activity, "==" + TwoFragment.DEFAULTFRAGMENT);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_add_tab_item;
    }

    @Override
    public void initView() {
        add_tabitem_img_back = findViewById(R.id.add_tabitem_img_back);
        add_tabitem_txt_mytab = findViewById(R.id.add_tabitem_txt_mytab);
        add_tabitem_txt_select = findViewById(R.id.add_tabitem_txt_select);
        add_tabitem_txt_edit = findViewById(R.id.add_tabitem_txt_edit);
        add_mytabitem_gridview = findViewById(R.id.add_mytabitem_gridview);
        add_alltabitem_gridview = findViewById(R.id.add_alltabitem_gridview);

        add_tabitem_txt_alltab = findViewById(R.id.add_tabitem_txt_alltab);
        add_tabitem_txt_add = findViewById(R.id.add_tabitem_txt_add);

        add_tabitem_img_back.setOnClickListener(this);
        add_tabitem_txt_edit.setOnClickListener(this);
        add_tabitem_txt_select.setOnClickListener(this);

        add_mytabitem_gridview.setOnItemLongClickListener(this);
        add_mytabitem_gridview.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        initMyData();
        initAllData();
    }

    /**
     * 给alldatas填充数据
     */
    private void initAllData() {
        add_datas.add(new Add_AllTabItemInfo(1, "文化", 1));
        add_datas.add(new Add_AllTabItemInfo(2, "财产", 1));
        add_datas.add(new Add_AllTabItemInfo(3, "游戏", 1));
        add_datas.add(new Add_AllTabItemInfo(4, "动漫", 1));
        add_datas.add(new Add_AllTabItemInfo(5, "理财", 1));
        add_datas.add(new Add_AllTabItemInfo(6, "问答", 1));
        add_datas.add(new Add_AllTabItemInfo(7, "情感", 1));
        add_datas.add(new Add_AllTabItemInfo(8, "职场", 1));
        add_datas.add(new Add_AllTabItemInfo(9, "家居", 1));
        add_datas.add(new Add_AllTabItemInfo(10, "电竞", 1));
        add_datas.add(new Add_AllTabItemInfo(11, "数码", 1));
        add_datas.add(new Add_AllTabItemInfo(12, "星座", 1));
        add_datas.add(new Add_AllTabItemInfo(13, "教育", 1));
        add_datas.add(new Add_AllTabItemInfo(14, "美容", 1));
        add_datas.add(new Add_AllTabItemInfo(15, "搏击", 1));
        add_datas.add(new Add_AllTabItemInfo(16, "健康", 1));
        add_datas.add(new Add_AllTabItemInfo(17, "旅游", 1));
        add_datas.add(new Add_AllTabItemInfo(18, "综艺", 1));
        add_datas.add(new Add_AllTabItemInfo(19, "育儿", 1));

        addAllTabItemGVAdapter = new AddAllTabItemGVAdapter(activity, add_datas);
        add_alltabitem_gridview.setAdapter(addAllTabItemGVAdapter);
    }

    /**
     * 给Mydatas填充数据
     */
    private void initMyData() {
        datas.add(new Add_MyTabItemInfo(1, "头条", 0));
        datas.add(new Add_MyTabItemInfo(2, "社会", 1));
        datas.add(new Add_MyTabItemInfo(3, "国内", 1));
        datas.add(new Add_MyTabItemInfo(4, "国际", 1));
        datas.add(new Add_MyTabItemInfo(5, "娱乐", 1));
        datas.add(new Add_MyTabItemInfo(6, "体育", 1));
        datas.add(new Add_MyTabItemInfo(7, "军事", 1));
        datas.add(new Add_MyTabItemInfo(8, "科技", 1));
        datas.add(new Add_MyTabItemInfo(9, "财经", 1));
        datas.add(new Add_MyTabItemInfo(10,"时尚", 1));

        addMyTabItemGVAdapter = new AddMyTabItemGVAdapter(activity, datas);
        add_mytabitem_gridview.setAdapter(addMyTabItemGVAdapter);
    }



    @Override
    public void onClick(View v) {
        int temdId = v.getId();
        if(temdId == R.id.add_tabitem_txt_select){
        }else if(temdId == R.id.add_tabitem_img_back){
            ActivityMan.getmInstance().finishActivity(activity);
        }else if(temdId == R.id.add_tabitem_txt_edit){
            //GridView的item添加边框
            if(!AddMyTabItemGVAdapter.isShowDelete){
                add_tabitem_txt_edit.setText(R.string.complete);
                addMyTabItemGVAdapter.setIsShowDelete(true);
            }else{
                add_tabitem_txt_edit.setText(R.string.mytabedit);
                addMyTabItemGVAdapter.setIsShowDelete(false);
            }
        }
    }




    /**
     * GV的长按点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if(AddMyTabItemGVAdapter.isShowDelete){
            addMyTabItemGVAdapter.setIsShowDelete(false);
        }else{
            addMyTabItemGVAdapter.setIsShowDelete(true);
            add_tabitem_txt_edit.setText(R.string.complete);
        }
        return true;
    }

    /**
     * GV的点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        A.C_Log(activity, "position=" + position);
        ActivityMan.getmInstance().finishActivity(activity);
        //点击position，赋给SELECTEDFRAGMENT，使用于TwoFragment中
        SELECTEDFRAGMENT = position;
        //根据Item的值去往twofrag界面对应的frag里面
        TwoFragment.getSelectFrag(SELECTEDFRAGMENT);
    }
}
