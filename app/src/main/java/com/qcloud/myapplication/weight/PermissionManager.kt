package com.qcloud.myapplication.weight

import android.app.Activity
import android.content.Context
import android.support.v4.app.ActivityCompat

/**
 * Description：申请权限
 * author：Smoker
 * 2019/1/23 10:24
 */
class PermissionManager(activity: Activity) {

    private var mActivity: Activity? = null
    private var permissions: Array<String>? = null

    init {
        mActivity = activity
    }

    fun setRequest(vararg requests: String) {
        permissions = Array(requests.size){
            requests[it]
        }
    }

    fun build() {
        if (mActivity != null && permissions != null) {
            ActivityCompat.requestPermissions(mActivity!!, permissions!!, 1)
        }
    }
}