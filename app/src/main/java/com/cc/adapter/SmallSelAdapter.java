package com.cc.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cc.R;
import com.cc.model.SmallSelInfo;

import java.util.List;

public class SmallSelAdapter extends RecyclerView.Adapter<SmallSelAdapter.ViewHolder> {

    Activity context;

    List<SmallSelInfo> datas;

    LayoutInflater inflater;

    View view;

    public SmallSelAdapter(Activity activity, List<SmallSelInfo> datas){
        this.context = activity;
        this.datas = datas;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(datas.get(position).getImgurl()).into(holder.small_item_img);

        holder.small_item_name.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView small_item_img;

        TextView small_item_name;

        LinearLayout lin_small;

        public ViewHolder(View rootView) {
            super(rootView);
            small_item_img = rootView.findViewById(R.id.small_item_img);
            small_item_name = rootView.findViewById(R.id.small_item_name);
            lin_small = rootView.findViewById(R.id.lin_small);

            lin_small.setOnClickListener(new View.OnClickListener() {
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
