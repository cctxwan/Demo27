package com.cc.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.ZGByStrInfo;

import java.util.List;

/**
 * 根据关键字查询
 */
public class ZGByStrAdapter  extends RecyclerView.Adapter<ZGByStrAdapter.ViewHolder> {

    Activity context;

    List<ZGByStrInfo> datas;

    LayoutInflater inflater;

    View view;

    public ZGByStrAdapter(Activity activity, List<ZGByStrInfo> datas){
        this.context = activity;
        this.datas = datas;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zgbystr_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.zgbystr_txt_title.setText(datas.get(position).getTitle());
        holder.zgbystr_txt_dec.setText(datas.get(position).getDes());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView zgbystr_txt_title, zgbystr_txt_dec;

        LinearLayout zgbystr_lin;

        public ViewHolder(View rootView) {
            super(rootView);
            zgbystr_txt_title = rootView.findViewById(R.id.zgbystr_txt_title);
            zgbystr_txt_dec = rootView.findViewById(R.id.zgbystr_txt_dec);
            zgbystr_lin = rootView.findViewById(R.id.zgbystr_lin);

            zgbystr_lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Linster.textItemOnClick(v, getPosition());
                }
            });
        }
    }

    public ItemOnClickLinster Linster;

    public void setLinster(ItemOnClickLinster linster) {
        Linster = linster;
    }

    public interface ItemOnClickLinster{
        void textItemOnClick(View view, int position);
    }
}
