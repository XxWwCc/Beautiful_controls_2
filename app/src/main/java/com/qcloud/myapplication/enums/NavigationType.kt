package com.qcloud.myapplication.enums

/**
 * Description：导航类型
 * author：Smoker
 * 2019/3/22 17:07
 */
object NavigationType {
    const val TYPE_BAIDU = 1
    const val TYPE_GAODE = 2
    const val TYPE_TENXUN = 3

    fun getName(type: Int) : String {
        return when (type) {
            TYPE_BAIDU -> "百度地图"
            TYPE_GAODE -> "高德地图"
            else -> "腾讯地图"
        }
    }
}