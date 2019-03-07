package com.cc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.FourListViewInfo;
import com.cc.utils.AsyncImageLoader;
import com.cc.view.MyListView;

import java.util.List;

/**
 * Created by admin on 2018/7/25.
 */
public class FourLVAdapter extends BaseAdapter {

    LayoutInflater inflater;

    AsyncImageLoader asyncImageLoader;

    Activity activity;

    List<FourListViewInfo> datas;

    MyListView four_lv;

    public onItemClickLiester onItemClickLiester;

    public void setOnItemClickLiester(onItemClickLiester onItemClickLiester) {
        this.onItemClickLiester = onItemClickLiester;
    }

    public interface onItemClickLiester{
        void succ(View view, int postion);
    }

    public FourLVAdapter(Activity activity, List<FourListViewInfo> datas, MyListView four_lv) {
        this.activity = activity;
        this.datas = datas;
        this.four_lv = four_lv;

        inflater = LayoutInflater.from(activity);
        this.asyncImageLoader = new AsyncImageLoader();
    }

    @Override
    public int getCount() {
        return datas.size() > 0 ? datas.size() : null;
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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.four_lv_item, null);
        }
        convertView.setTag(position);

        FourListViewInfo info = (FourListViewInfo) getItem(position);
        final ImageView fourlv_item_img = convertView.findViewById(R.id.fourlv_item_img);
        //加载loading动画
        Animation rotateAnimation = AnimationUtils.loadAnimation(activity, R.anim.loading);
        LinearInterpolator interpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(interpolator);
        fourlv_item_img.startAnimation(rotateAnimation);


        TextView fourlv_item_txt = convertView.findViewById(R.id.fourlv_item_txt);
        fourlv_item_txt.setText(info.getName());
        ImageView fourlv_item_stateimg = convertView.findViewById(R.id.fourlv_item_stateimg);
        RelativeLayout rel_lv = convertView.findViewById(R.id.rel_lv);
        rel_lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickLiester.succ(v, position);
            }
        });
        fourlv_item_stateimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickLiester.succ(v, position);
            }
        });

        fourlv_item_img.setBackgroundResource(info.getImg_url());
        fourlv_item_img.clearAnimation();

        return convertView;
    }
}
