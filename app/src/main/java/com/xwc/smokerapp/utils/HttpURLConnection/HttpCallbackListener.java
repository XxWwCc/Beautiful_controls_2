package com.xwc.smokerapp.utils.HttpURLConnection;

/**
 * Description：请求回调接口
 * author：Smoker
 * 2019/2/27 11:40
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
