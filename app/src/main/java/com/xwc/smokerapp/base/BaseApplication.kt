package com.xwc.smokerapp.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
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

    /**
     * 解决Android 5.0以下方法超限
     * 打包apk文件时会提示方法超限
     * */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        var mApplication: BaseApplication? = null
        var mAppManager: AppManager? = null
    }
}