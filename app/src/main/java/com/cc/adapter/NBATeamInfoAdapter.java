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
import com.cc.model.NBATeamInfo;

import java.util.List;

/**
 * NBA球队赛程适配器
 */
public class NBATeamInfoAdapter extends RecyclerView.Adapter<NBATeamInfoAdapter.ViewHolder> {

    Activity context;

    List<NBATeamInfo> datas;

    LayoutInflater inflater;

    View view;

    public NBATeamInfoAdapter(Activity activity, List<NBATeamInfo> datas){
        this.context = activity;
        this.datas = datas;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nba_teaminfo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(datas.get(position).getImg()).into(holder.img_nba_teaminfo);

        holder.txt_nba_teaminfo.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_nba_teaminfo;

        TextView txt_nba_teaminfo;

        LinearLayout lin_nba_teaminfo;

        public ViewHolder(View rootView) {
            super(rootView);
            img_nba_teaminfo = rootView.findViewById(R.id.img_nba_teaminfo);
            txt_nba_teaminfo = rootView.findViewById(R.id.txt_nba_teaminfo);
            lin_nba_teaminfo = rootView.findViewById(R.id.lin_nba_teaminfo);

            lin_nba_teaminfo.setOnClickListener(new View.OnClickListener() {
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
