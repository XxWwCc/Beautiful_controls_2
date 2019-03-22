package com.qcloud.myapplication.beans

/**
 * Description：event时间的载体
 * author：Smoker
 * 2019/3/22 11:34
 */
class EventBean {
    var type = 0
    var obj: Any? = null

    override fun toString(): String {
        return "EventBean(type=$type, obj=$obj)"
    }
}