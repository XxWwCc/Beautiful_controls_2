package com.qcloud.myapplication.net

import com.qcloud.myapplication.utils.TokenUtil

/**
 * Description：
 * author：Smoker
 * 2018/12/21 14:09
 */
class FrameRequest {
    fun getAppParams() : HashMap<String, Any> {
        val params = HashMap<String, Any>()


        if (!TokenUtil.getToken().isNullOrBlank()) {
            params["app_token"] = TokenUtil.getToken()!!
        }
        return params
    }

}