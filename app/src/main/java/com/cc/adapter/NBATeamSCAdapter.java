package com.cc.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cc.R;
import com.cc.model.NBASCInfo;

import java.util.List;

/**
 * 赛程适配器
 */
public class NBATeamSCAdapter extends RecyclerView.Adapter<NBATeamSCAdapter.ViewHolder> {

    Activity context;

    List<NBASCInfo> datas;

    LayoutInflater inflater;

    View view;

    public NBATeamSCAdapter(Activity activity, List<NBASCInfo> datas){
        this.context = activity;
        this.datas = datas;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nba_teamsc_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //比赛状态
        if(datas.get(position).getStatus().equals("0")){
            holder.txt_nbasc_state.setText(context.getResources().getText(R.string.nobegin));
        }else if(datas.get(position).getStatus().equals("1")){
            holder.txt_nbasc_state.setText(context.getResources().getText(R.string.ing));
        }else if(datas.get(position).getStatus().equals("2")){
            holder.txt_nbasc_state.setText(context.getResources().getText(R.string.over));
        }
        //比赛时间
        holder.txt_nbasc_gametime.setText(context.getResources().getString(R.string.gametime) + datas.get(position).getM_time());
        //比赛主队
        holder.txt_nbasc_teamzhu.setText(datas.get(position).getPlayer1());
        //客队
        holder.txt_nbasc_teamke.setText(datas.get(position).getPlayer2());
        //比分
        holder.txt_nbasc_score.setText(context.getResources().getString(R.string.gamescore) + datas.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_nbasc_state, txt_nbasc_gametime, txt_nbasc_teamzhu, txt_nbasc_teamke, txt_nbasc_score, txt_nbasc_gamevideo, txt_nbasc_gamecount;

        public ViewHolder(View rootView) {
            super(rootView);
            txt_nbasc_state = rootView.findViewById(R.id.txt_nbasc_state);
            txt_nbasc_gametime = rootView.findViewById(R.id.txt_nbasc_gametime);
            txt_nbasc_teamzhu = rootView.findViewById(R.id.txt_nbasc_teamzhu);
            txt_nbasc_teamke = rootView.findViewById(R.id.txt_nbasc_teamke);
            txt_nbasc_score = rootView.findViewById(R.id.txt_nbasc_score);
            txt_nbasc_gamevideo = rootView.findViewById(R.id.txt_nbasc_gamevideo);
            txt_nbasc_gamecount = rootView.findViewById(R.id.txt_nbasc_gamecount);

            txt_nbasc_gamevideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //比赛视频
                }
            });
            txt_nbasc_gamecount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //技术统计
                }
            });
        }
    }
}
