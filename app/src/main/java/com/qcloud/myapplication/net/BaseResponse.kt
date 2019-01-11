package com.qcloud.myapplication.net

import java.io.Serializable

/**
 * Description：
 * author：Smoker
 * 2018/12/21 13:52
 */
class BaseResponse<T> : Serializable {
    var url: String? = null
    var data: T? = null
    var message: String? = null
    var status: Int = 200

    override fun toString(): String {
        return "BaseResponse(url=$url, data=$data, message=$message, status=$status)"
    }
}