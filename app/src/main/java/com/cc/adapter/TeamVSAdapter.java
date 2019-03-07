package com.cc.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cc.R;
import com.cc.model.NbaVSInfo;

import java.util.List;

public class TeamVSAdapter extends RecyclerView.Adapter<TeamVSAdapter.ViewHolder> {

    Activity context;

    List<NbaVSInfo> datas;

    LayoutInflater inflater;

    View view;

    public TeamVSAdapter(Activity activity, List<NbaVSInfo> datas){
        this.context = activity;
        this.datas = datas;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_vs_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(datas.get(position).getStatus().equals("0")){
            holder.txt_vs_state.setText(context.getResources().getText(R.string.nobegin));
        }else if(datas.get(position).getStatus().equals("1")){
            holder.txt_vs_state.setText(context.getResources().getText(R.string.ing));
        }else if(datas.get(position).getStatus().equals("2")){
            holder.txt_vs_state.setText(context.getResources().getText(R.string.over));
        }
        holder.txt_vs_zhu.setText(datas.get(position).getPlayer1());
        holder.txt_vs_ke.setText(datas.get(position).getPlayer2());
        holder.txt_vs_scort.setText(datas.get(position).getScore());
        holder.txt_vs_video.setText(datas.get(position).getLink1text());
        holder.txt_vs_datacount.setText(datas.get(position).getLink2text());
        holder.txt_vs_gametime.setText(datas.get(position).getTime());

        //加载图片
        Glide.with(context).load(datas.get(position).getPlayer1logo()).into(holder.img_vs_zhu);
        Glide.with(context).load(datas.get(position).getPlayer2logo()).into(holder.img_vs_ke);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_vs_zhu, img_vs_ke;

        public TextView txt_vs_state, txt_vs_zhu, txt_vs_ke,
                txt_vs_scort, txt_vs_video, txt_vs_datacount, txt_vs_gametime;

        public ViewHolder(View rootView) {
            super(rootView);
            this.img_vs_zhu = rootView.findViewById(R.id.img_vs_zhu);
            this.img_vs_ke = rootView.findViewById(R.id.img_vs_ke);
            this.txt_vs_zhu = rootView.findViewById(R.id.txt_vs_zhu);
            this.txt_vs_ke = rootView.findViewById(R.id.txt_vs_ke);
            this.txt_vs_state = rootView.findViewById(R.id.txt_vs_state);
            this.txt_vs_scort = rootView.findViewById(R.id.txt_vs_scort);
            this.txt_vs_video = rootView.findViewById(R.id.txt_vs_video);
            this.txt_vs_datacount = rootView.findViewById(R.id.txt_vs_datacount);
            this.txt_vs_gametime = rootView.findViewById(R.id.txt_vs_gametime);

        }
    }

}
