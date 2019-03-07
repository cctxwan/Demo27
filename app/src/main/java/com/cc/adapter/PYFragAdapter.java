package com.cc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.PY_Info;

import java.util.List;

public class PYFragAdapter extends BaseAdapter {

    Activity activity;
    List<PY_Info> datas;
    LayoutInflater inflater;
    ViewHolder viewHolder;

    public PYFragAdapter(Activity activity, List<PY_Info> datas){
        this.activity = activity;
        this.datas = datas;

        inflater = LayoutInflater.from(activity);
    }

    /**
     * 定义一个可以单击的item接口
     */
    public PYFragAdapter.onItemClickLiester onItemClickLiester;

    /**
     * setOnItemClick
     * @param onItemClickLiester
     */
    public void setOnItemClickLiester(PYFragAdapter.onItemClickLiester onItemClickLiester) {
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
            convertView = inflater.inflate(R.layout.py_gv_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt_py.setText(datas.get(position).getPinyin());
        viewHolder.txt_pykey.setText(datas.get(position).getPinyin_key());
        viewHolder.lin_py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickLiester.succ(v, position);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_py;
        public TextView txt_pykey;
        public LinearLayout lin_py;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_py = rootView.findViewById(R.id.txt_py);
            this.txt_pykey = rootView.findViewById(R.id.txt_pykey);
            this.lin_py = rootView.findViewById(R.id.lin_py);
        }
    }

}
