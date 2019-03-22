package com.qcloud.myapplication.utils

import android.app.Activity
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import java.util.*

/**
 * Description：activity管理器
 * author：Smoker
 * 2019/3/22 13:56
 */
class AppManager private constructor() {

    fun addActivity(@NonNull activity: Activity) {
        mActivityStack.add(activity)
    }

    fun getTopActivity() : Activity? = mActivityStack.lastElement()

    fun killActivity(activity: Activity?) {
        if (activity != null) {
            mActivityStack.remove(activity)
            activity.finish()
        }
    }

    fun killAllActivity() {
        mActivityStack.asSequence()
            .filterNotNull()
            .forEach { it.finish() }
        mActivityStack.clear()
    }

    /**
     * 杀死所有活动剩下指定的一个
     * */
    fun killAllActivityWithoutOne(cls: Class<*>) {
        for (activity in mActivityStack) {
            if (activity.javaClass == cls) {
                continue
            } else {
                ActivityCompat.finishAfterTransition(activity)
                mActivityStack.remove(activity)
            }
        }
    }

    object AppHolder {
        var instance: AppManager = AppManager()
    }

    companion object {
        private val mActivityStack: Stack<Activity> = Stack()

        val instance: AppManager
            get() = AppHolder.instance
    }
}