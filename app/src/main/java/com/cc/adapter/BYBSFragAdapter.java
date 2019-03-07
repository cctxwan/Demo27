package com.cc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.BYBS_Info;

import java.util.List;

public class BYBSFragAdapter extends BaseAdapter {

    Activity activity;
    List<BYBS_Info> datas;
    LayoutInflater inflater;
    ViewHolder viewHolder;

    public BYBSFragAdapter(Activity activity, List<BYBS_Info> datas){
        this.activity = activity;
        this.datas = datas;

        inflater = LayoutInflater.from(activity);
    }

    /**
     * 定义一个可以单击的item接口
     */
    public BYBSFragAdapter.onItemClickLiester onItemClickLiester;

    /**
     * setOnItemClick
     * @param onItemClickLiester
     */
    public void setOnItemClickLiester(BYBSFragAdapter.onItemClickLiester onItemClickLiester) {
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
            convertView = inflater.inflate(R.layout.bsquery_gv_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt_bsquery_dufa.setText(datas.get(position).getPy());
        viewHolder.txt_bsquery_hanzi.setText(datas.get(position).getZi());
        viewHolder.txt_bsquery_pinyin.setText(datas.get(position).getPinyin());
        viewHolder.txt_bsquery_wubi.setText(datas.get(position).getWubi());
        viewHolder.txt_bsquery_bushou.setText(datas.get(position).getBushou());
        viewHolder.txt_bsquery_bihua.setText(datas.get(position).getBihua());

        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_bsquery_dufa,
                txt_bsquery_hanzi,
                txt_bsquery_pinyin,
                txt_bsquery_wubi,
                txt_bsquery_bushou,
                txt_bsquery_bihua;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_bsquery_dufa = rootView.findViewById(R.id.txt_bsquery_dufa);
            this.txt_bsquery_hanzi = rootView.findViewById(R.id.txt_bsquery_hanzi);
            this.txt_bsquery_pinyin = rootView.findViewById(R.id.txt_bsquery_pinyin);
            this.txt_bsquery_wubi = rootView.findViewById(R.id.txt_bsquery_wubi);
            this.txt_bsquery_bushou = rootView.findViewById(R.id.txt_bsquery_bushou);
            this.txt_bsquery_bihua = rootView.findViewById(R.id.txt_bsquery_bihua);
        }
    }

}
