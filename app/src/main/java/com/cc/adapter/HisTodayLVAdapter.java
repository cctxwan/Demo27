package com.cc.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.TimeLineInfo;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HisTodayLVAdapter extends RecyclerView.Adapter<HisTodayLVAdapter.ViewHolder> {

    Activity context;

    List<TimeLineInfo> data;

    LayoutInflater inflater;

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    LruCache<String, BitmapDrawable> mMemoryCache;

    public HisTodayLVAdapter(Activity activity, List<TimeLineInfo> datas){
        this.context = activity;
        this.data = datas;

        inflater = LayoutInflater.from(activity);


        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable drawable) {
                return drawable.getBitmap().getByteCount();
            }
        };
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @param drawable
     *            LruCache的值，这里传入从网络上下载的BitmapDrawable对象。
     */
    public void addBitmapToMemoryCache(String key, BitmapDrawable drawable) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, drawable);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的BitmapDrawable对象，或者null。
     */
    public BitmapDrawable getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 异步下载图片的任务。
     *
     * @author guolin
     */
    class BitmapWorkerTask extends AsyncTask<String, Void, BitmapDrawable> {

        private ImageView mImageView;

        public BitmapWorkerTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            String imageUrl = params[0];
            // 在后台开始下载图片
            Bitmap bitmap = downloadBitmap(imageUrl);
            BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            addBitmapToMemoryCache(imageUrl, drawable);
            return drawable;
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            if (mImageView != null && drawable != null) {
                mImageView.setImageDrawable(drawable);
            }
        }

        /**
         * 建立HTTP请求，并获取Bitmap对象。
         *
         * @param imageUrl
         *            图片的URL地址
         * @return 解析后的Bitmap对象
         */
        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.histoday_lv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.txt_yltime.setText(data.get(position).getYear() + ":" + data.get(position).getMonth() + ":" + data.get(position).getDay());
        viewHolder.txt_nltime.setText(data.get(position).getLunar());
        viewHolder.txt_lss_title.setText(data.get(position).getTitle());
        viewHolder.txt_dec.setText(data.get(position).getDes());

        //显示图片
        if(!data.get(position).getPic().equals("")){
            Picasso.with(context).load(data.get(position).getPic()).into(viewHolder.img_pic);
        }
//        viewHolder.img_pic.setTag(data.get(position).getPic());
//        BitmapDrawable drawable = getBitmapFromMemoryCache(data.get(position).getPic());
//        if (drawable != null) {
//            viewHolder.img_pic.setImageDrawable(drawable);
//        } else {
//            BitmapWorkerTask task = new BitmapWorkerTask(viewHolder.img_pic);
//            task.execute(data.get(position).getPic());
//        }
//
//        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_yltime, txt_nltime, txt_lss_title, txt_dec;
        public ImageView img_pic;

        public ViewHolder(View rootView) {
            super(rootView);
            this.txt_yltime = rootView.findViewById(R.id.txt_yltime);
            this.txt_nltime = rootView.findViewById(R.id.txt_nltime);
            this.txt_lss_title = rootView.findViewById(R.id.txt_lss_title);
            this.txt_dec = rootView.findViewById(R.id.txt_dec);

            this.img_pic = rootView.findViewById(R.id.img_pic);
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
