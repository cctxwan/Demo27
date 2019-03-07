package com.cc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.BS_Info;

import java.util.List;

public class BSFragAdapter extends BaseAdapter {

    Activity activity;
    List<BS_Info> datas;
    LayoutInflater inflater;
    ViewHolder viewHolder;

    public BSFragAdapter(Activity activity, List<BS_Info> datas){
        this.activity = activity;
        this.datas = datas;

        inflater = LayoutInflater.from(activity);
    }

    /**
     * 定义一个可以单击的item接口
     */
    public BSFragAdapter.onItemClickLiester onItemClickLiester;

    /**
     * setOnItemClick
     * @param onItemClickLiester
     */
    public void setOnItemClickLiester(BSFragAdapter.onItemClickLiester onItemClickLiester) {
        this.onItemClickLiester = onItemClickLiester;
    }

    public interface onItemClickLiester{
        void succ(View view, int postion);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.bs_gv_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text_bh.setText(datas.get(position).getBihua());
        viewHolder.text_bs.setText(datas.get(position).getBushou());
        viewHolder.lin_bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickLiester.succ(v, position);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView text_bs;
        public TextView text_bh;
        public LinearLayout lin_bs;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.text_bs = rootView.findViewById(R.id.txt_bs);
            this.text_bh = rootView.findViewById(R.id.txt_bh);
            this.lin_bs = rootView.findViewById(R.id.lin_bs);
        }
    }

}
