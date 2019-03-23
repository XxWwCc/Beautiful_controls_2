package com.xwc.smokerapp.ui.weight

import android.app.Application
import com.xwc.smokerapp.enums.NetEnum
import timber.log.Timber

/**
 * Description：
 * author：Smoker
 * 2019/1/24 15:38
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (NetEnum.IS_DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant()
        }
    }

}