package com.cc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.BYPY_Info;

import java.util.List;

public class BYPYFragAdapter extends BaseAdapter {

    Activity activity;
    List<BYPY_Info> datas;
    LayoutInflater inflater;
    ViewHolder viewHolder;

    public BYPYFragAdapter(Activity activity, List<BYPY_Info> datas){
        this.activity = activity;
        this.datas = datas;

        inflater = LayoutInflater.from(activity);
    }

    /**
     * 定义一个可以单击的item接口
     */
    public BYPYFragAdapter.onItemClickLiester onItemClickLiester;

    /**
     * setOnItemClick
     * @param onItemClickLiester
     */
    public void setOnItemClickLiester(BYPYFragAdapter.onItemClickLiester onItemClickLiester) {
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
            convertView = inflater.inflate(R.layout.pyquery_gv_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt_pyquery_dufa.setText(datas.get(position).getPy());
        viewHolder.txt_pyquery_hanzi.setText(datas.get(position).getZi());
        viewHolder.txt_pyquery_pinyin.setText(datas.get(position).getPinyin());
        viewHolder.txt_pyquery_wubi.setText(datas.get(position).getWubi());
        viewHolder.txt_pyquery_bushou.setText(datas.get(position).getBushou());
        viewHolder.txt_pyquery_bihua.setText(datas.get(position).getBihua());

        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_pyquery_dufa,
                txt_pyquery_hanzi,
                txt_pyquery_pinyin,
                txt_pyquery_wubi,
                txt_pyquery_bushou,
                txt_pyquery_bihua;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_pyquery_dufa = rootView.findViewById(R.id.txt_pyquery_dufa);
            this.txt_pyquery_hanzi = rootView.findViewById(R.id.txt_pyquery_hanzi);
            this.txt_pyquery_pinyin = rootView.findViewById(R.id.txt_pyquery_pinyin);
            this.txt_pyquery_wubi = rootView.findViewById(R.id.txt_pyquery_wubi);
            this.txt_pyquery_bushou = rootView.findViewById(R.id.txt_pyquery_bushou);
            this.txt_pyquery_bihua = rootView.findViewById(R.id.txt_pyquery_bihua);
        }
    }

}
