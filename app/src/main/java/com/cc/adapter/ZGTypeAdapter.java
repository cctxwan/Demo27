package com.cc.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cc.R;
import com.cc.model.ZGTypeInfo;

import java.util.List;

/**
 * 梦境类型适配器
 */
public class ZGTypeAdapter extends RecyclerView.Adapter<ZGTypeAdapter.ViewHolder> {

    Activity context;

    List<ZGTypeInfo> datas;

    LayoutInflater inflater;

    View view;

    public ZGTypeAdapter(Activity activity, List<ZGTypeInfo> datas){
        this.context = activity;
        this.datas = datas;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zgtype_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.zgtype_item_name.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView zgtype_item_name;

        LinearLayout lin_zgtype;

        public ViewHolder(View rootView) {
            super(rootView);
            zgtype_item_name = rootView.findViewById(R.id.zgtype_item_name);
            lin_zgtype = rootView.findViewById(R.id.lin_zgtype);

            lin_zgtype.setOnClickListener(new View.OnClickListener() {
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
