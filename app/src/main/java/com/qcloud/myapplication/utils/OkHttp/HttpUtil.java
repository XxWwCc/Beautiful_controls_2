package com.qcloud.myapplication.utils.OkHttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Description：okhttp3网络请求
 * author：Smoker
 * 2019/2/27 13:40
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
