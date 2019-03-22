package com.xwc.smokerapp.base

/**
 * Description：v层基类
 * author：Smoker
 * 2019/3/22 11:24
 */
interface BaseView {
    fun loadError(message: String, isShow: Boolean = true)
}