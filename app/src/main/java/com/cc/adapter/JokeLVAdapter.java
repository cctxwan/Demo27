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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.JokesNew;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class JokeLVAdapter extends RecyclerView.Adapter<JokeLVAdapter.ViewHolder> {

    Activity context;

    List<JokesNew> data;

    LayoutInflater inflater;

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    LruCache<String, BitmapDrawable> mMemoryCache;

    public JokeLVAdapter(Activity activity, List<JokesNew> datas){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_lv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.joke_lv_txtconent.setText(data.get(position).getContent());
        viewHolder.joke_lv_txttime.setText("时间戳：" + data.get(position).getUnixtime() + "");

        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView joke_lv_txttime;
        public TextView joke_lv_txtconent;
        public LinearLayout lin_alljoke;

        public ViewHolder(View rootView) {
            super(rootView);
            this.joke_lv_txtconent = rootView.findViewById(R.id.joke_lv_txtconent);
            this.joke_lv_txttime = rootView.findViewById(R.id.joke_lv_txttime);
            this.lin_alljoke = rootView.findViewById(R.id.lin_alljoke);

            this.lin_alljoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Linster.textItemOnClick(v, getPosition());
//                    joke_lv_txtconent.setTextColor(Color.GRAY);
//                    joke_lv_txttime.setTextColor(Color.GRAY);
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
