package com.xwc.smokerapp.base

import com.xwc.smokerapp.beans.EventBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Description：p层基类
 * author：Smoker
 * 2019/3/22 11:23
 */
abstract class BasePresenter<V> {
    var mView: V? = null

    fun attach(view: V) {
        this.mView = view
        EventBus.getDefault().register(this)
    }

    fun detach() {
        mView = null
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    open fun onMessageEvent(event: EventBean) {

    }

}