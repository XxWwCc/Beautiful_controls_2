package com.xwc.smokerapp.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Description：
 * author：Smoker
 * 2019/3/23 17:18
 */
object ScreenUtil {

    fun getScreenWidth(context: Context) : Int {
        val outMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    fun getScreenHeight(context: Context) : Int {
        val outMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

}