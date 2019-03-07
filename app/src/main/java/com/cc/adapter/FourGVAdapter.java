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
import com.cc.model.FourGridViewInfo;
import com.cc.utils.AsyncImageLoader;
import com.cc.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/7/24.
 */

public class FourGVAdapter extends BaseAdapter {

    Activity activity;

    List<FourGridViewInfo> datas = new ArrayList<>();

    MyGridView four_gv;

    LayoutInflater inflater;

    AsyncImageLoader asyncImageLoader;

    public onItemClickLiester onItemClickLiester;

    public void setOnItemClickLiester(onItemClickLiester onItemClickLiester) {
        this.onItemClickLiester = onItemClickLiester;
    }

    public interface onItemClickLiester{
        void succ(View view, int postion);
    }

    public FourGVAdapter(Activity activity, List<FourGridViewInfo> datas, MyGridView four_gv) {
        this.activity = activity;
        this.datas = datas;
        this.four_gv = four_gv;

        inflater = LayoutInflater.from(activity);
        this.asyncImageLoader = new AsyncImageLoader();
    }

    @Override
    public int getCount() {
        return datas.size() > 0 ? datas.size() : 0;
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
            convertView = inflater.inflate(R.layout.four_gv_item ,null);
        }
        convertView.setTag(position);

        FourGridViewInfo info = (FourGridViewInfo) getItem(position);
        final ImageView fourgv_item_img = convertView.findViewById(R.id.fourgv_item_img);
        //加载loading动画
        Animation rotateAnimation = AnimationUtils.loadAnimation(activity, R.anim.loading);
        LinearInterpolator interpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(interpolator);
        fourgv_item_img.startAnimation(rotateAnimation);
        RelativeLayout lin_item = convertView.findViewById(R.id.lin_item);
        lin_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickLiester.succ(v, position);
            }
        });

        ImageView fourgv_img_rr = convertView.findViewById(R.id.fourgv_img_rr);
        if(info.getAdd_data() == 0){
            fourgv_img_rr.setVisibility(View.GONE);
        }
        TextView fourgv_item_txt = convertView.findViewById(R.id.fourgv_item_txt);
        fourgv_item_txt.setText(info.getName());
        fourgv_item_img.setBackgroundResource(info.getImg_url());
        fourgv_item_img.clearAnimation();


        return convertView;
    }
}
