package com.cc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.R;
import com.cc.fragment.TwoFragment;
import com.cc.model.Add_MyTabItemInfo;

import java.util.List;

/**
 * AddTabItemGVAdapter
 * 加载AddMyTabItem的数据
 */
public class AddMyTabItemGVAdapter extends BaseAdapter {

    List<Add_MyTabItemInfo> data;

    Activity context;

    LayoutInflater inflater;

    ViewHolder viewHolder;

    //判断是否显示删除图标，true是显示，false是不显示
    public static boolean isShowDelete = false;

    /**
     * 获取isShowDelete值
     * @param isShowDelete
     */
    public void setIsShowDelete(boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
        notifyDataSetChanged();
    }

    public AddMyTabItemGVAdapter(Activity activity, List<Add_MyTabItemInfo> datas) {
        this.context = activity;
        this.data = datas;

        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Add_MyTabItemInfo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.addmyitem_gv_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //赋值
        viewHolder.txt_myitem.setText(data.get(position).name);
        viewHolder.txt_myitem.setBackgroundResource(position == TwoFragment.DEFAULTFRAGMENT ? R.drawable.gv_selected_text_ty : R.drawable.gv_text_ty);

        viewHolder.img_myitem.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);
        viewHolder.img_myitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你当前要删除的position为：" + position + "去除adapter里面的数据源；去掉原始数据源", Toast.LENGTH_LONG).show();
            }
        });


        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_myitem;
        public ImageView img_myitem;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_myitem = rootView.findViewById(R.id.txt_myitem);
            this.img_myitem = rootView.findViewById(R.id.img_myitem);
        }

    }

}
