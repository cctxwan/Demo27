package com.cc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.Add_AllTabItemInfo;

import java.util.List;

/**
 * AddAllTabItemGVAdapter
 * 加载AddTabItem的数据
 */
public class AddAllTabItemGVAdapter extends BaseAdapter {

    List<Add_AllTabItemInfo> data;

    Activity context;

    LayoutInflater inflater;

    ViewHolder viewHolder;

    public static boolean isShowdetele = false;

    public AddAllTabItemGVAdapter(Activity activity, List<Add_AllTabItemInfo> datas) {
        this.context = activity;
        this.data = datas;

        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Add_AllTabItemInfo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.addallitem_gv_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //赋值
        viewHolder.txt_allitem.setText(data.get(position).name);
        viewHolder.img_allitem.setVisibility(isShowdetele ? View.VISIBLE : View.GONE);

        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_allitem;
        public ImageView img_allitem;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_allitem = rootView.findViewById(R.id.txt_allitem);
            this.img_allitem = rootView.findViewById(R.id.img_allitem);
        }

    }

}
