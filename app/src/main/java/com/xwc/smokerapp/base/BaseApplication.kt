package com.xwc.smokerapp.base

import android.app.Application
import com.xwc.smokerapp.utils.AppManager
import com.xwc.smokerapp.utils.SharedUtil
import timber.log.Timber

/**
 * Description：
 * author：Smoker
 * 2019/3/22 11:45
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        mApplication = this
        mAppManager = AppManager.instance

        SharedUtil.initSharedPreference(this)
        if (true) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant()
        }
    }

    companion object {
        var mApplication: BaseApplication? = null
        var mAppManager: AppManager? = null
    }
}