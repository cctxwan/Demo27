package com.cc.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.R;
import com.cc.model.News;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FragTwoItemAdapter extends RecyclerView.Adapter<FragTwoItemAdapter.ViewHolder> {

    Activity context;

    List<News> data;

    LayoutInflater inflater;

    View view;

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    LruCache<String, BitmapDrawable> mMemoryCache;


    public FragTwoItemAdapter(Activity activity, List<News> datas){
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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_two_frag_listview_item, parent, false);
        return new ViewHolder(view);
    }

    /** 标记是否正在滑动，如果为true，就暂停加载图片 */
    protected boolean isScrolling = false;

    /**
     * 赋值
     * @param scrolling
     */
    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        //改变item的背景
        ChangeItemBG(viewHolder);

        viewHolder.txt_title.setText(data.get(position).getTitle());
        viewHolder.txt_content.setText(data.get(position).getAuthor_name());
        viewHolder.txt_time.setText(data.get(position).getDate());

        //给图片占位
        viewHolder.img_icon.setImageDrawable(context.getDrawable(R.mipmap.ic_launcher));
        viewHolder.img_icon.setTag(data.get(position).getThumbnail_pic_s());
        if(!isScrolling){
            BitmapDrawable drawable = getBitmapFromMemoryCache(data.get(position).getThumbnail_pic_s());
            if (drawable != null) {
                viewHolder.img_icon.setImageDrawable(drawable);
//                viewHolder.img_icon.setTag(drawable);
            } else {
                BitmapWorkerTask task = new BitmapWorkerTask(viewHolder.img_icon);
                task.execute(data.get(position).getThumbnail_pic_s());
            }
        }else{

        }

        viewHolder.itemView.setTag(position);
    }

    /**
     * 改变背景
     * @param viewHolder
     */
    private void ChangeItemBG(ViewHolder viewHolder) {
        viewHolder.txt_title.setBackgroundResource(R.color.transparent);
        viewHolder.txt_content.setBackgroundResource(R.color.transparent);
        viewHolder.txt_time.setBackgroundResource(R.color.transparent);
        RelativeLayout.LayoutParams txt_title = (RelativeLayout.LayoutParams) viewHolder.txt_title.getLayoutParams();
        txt_title.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        txt_title.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.txt_title.setLayoutParams(txt_title);
        LinearLayout.LayoutParams txt_content = (LinearLayout.LayoutParams) viewHolder.txt_content.getLayoutParams();
        txt_content.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        txt_content.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.txt_content.setLayoutParams(txt_content);
        LinearLayout.LayoutParams txt_time = (LinearLayout.LayoutParams) viewHolder.txt_time.getLayoutParams();
        txt_time.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        txt_time.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.txt_time.setLayoutParams(txt_time);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * //自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_title, txt_content, txt_time;
        public ImageView img_icon;
        public LinearLayout lin_all;

        public ViewHolder(View rootView) {
            super(rootView);
            this.lin_all = rootView.findViewById(R.id.lin_all);
            this.img_icon = rootView.findViewById(R.id.img_icon);
            this.txt_title = rootView.findViewById(R.id.txt_title);
            this.txt_content = rootView.findViewById(R.id.txt_content);
            this.txt_time = rootView.findViewById(R.id.txt_time);

            lin_all.setOnClickListener(new View.OnClickListener() {
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
