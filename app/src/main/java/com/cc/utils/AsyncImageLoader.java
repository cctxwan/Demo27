package com.cc.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * 异步获取网络图片并加载
 */
public class AsyncImageLoader {

    Handler handler = new Handler();

    HashMap<String, SoftReference<Bitmap>> imageCache;

    public AsyncImageLoader() {
        imageCache = new HashMap<String, SoftReference<Bitmap>>();//图片缓存
    }

    // 回调函数
    public interface ImageCallback {
        void onImageLoad(Integer t, Bitmap bitmap);
        void onError(Integer t);
    }

    public Bitmap loadDrawable(final Integer pos, final String imageUrl,
                                 final ImageCallback imageCallback) {
        new Thread() {
            @Override
            public void run() {

                LoadImg(pos, imageUrl, imageCallback);

            }
        }.start();
        return null;
    }// loadDrawable---end

    public void LoadImg(final Integer pos, final String imageUrl,
                        final ImageCallback imageCallback) {
        // 首先判断是否在缓存中
        // 但有个问题是：ImageCache可能会越来越大，以至用户内存用光，所以要用SoftReference（弱引用）来实现
        if (imageCache.containsKey(imageUrl)) {
            SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
            final Bitmap bitmap = softReference.get();
            if (bitmap != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageCallback.onImageLoad(pos, bitmap);
                    }
                });
                return;
            }
        }
        // 尝试从URL中加载
        final Bitmap bitmap = httpBitmap(imageUrl);
        if (bitmap != null) {
            imageCache.put(imageUrl, new SoftReference<>(bitmap));
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageCallback.onImageLoad(pos, bitmap);
            }
        });

    }

    public static Bitmap httpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
