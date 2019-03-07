package com.cc.HTTP;

import java.util.HashMap;
import java.util.Set;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http请求封装类
 */
public class HttpRequest {

    //全局使用
    private static OkHttpClient mOkHttpClient = new OkHttpClient();


    /**
     * get请求
     * @param url
     * @param callback
     */
    public static void get(String url, Callback callback) {  //这里没有返回，也可以返回string
        Request request = new Request
                .Builder()
                .get()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * post请求
     * @param url
     * @param paramsMap
     * @return
     */
    public static void post(String url, HashMap<String, String > paramsMap, Callback callback){  //这里没有返回，也可以返回string
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Set<String> keySet = paramsMap.keySet();
        for(String key:keySet) {
            String value = paramsMap.get(key);
            formBodyBuilder.add(key,value);
        }
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request
                .Builder()
                .post(formBody)
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

}
