package com.qcloud.myapplication.net

/**
 * Description：
 * author：Smoker
 * 2019/3/22 16:09
 */
interface DataCallback<in T> {
    fun onSuccess(t: T?, message: String?)

    fun onError(status: Int, message: String)
}