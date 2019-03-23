package com.xwc.smokerapp.base

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow

/**
 * Description：
 * author：Smoker
 * 2019/3/23 16:31
 */
abstract class BasePopWindow(protected val mContext: Context) : PopupWindow(mContext) {
    var mView: View? = null
    var onPopWindowViewClick : OnPopWindowViewClick? = null

    init {
        initWindow()
    }

    private fun initWindow() {
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        mView = LayoutInflater.from(mContext).inflate(viewId, null, false)
        contentView = mView
        isFocusable = true
        animationStyle = animId
        isOutsideTouchable = true
    }

    /**设置背影颜色透明度*/
    open fun setPopWindowBgAlpha(alpha: Float) {
        val lp: WindowManager.LayoutParams = (mContext as Activity).window.attributes
        lp.alpha = alpha
        mContext.window.attributes = lp
    }

    override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
        setPopWindowBgAlpha(0.3f)
    }

    override fun dismiss() {
        super.dismiss()
        setPopWindowBgAlpha(1.0f)
    }

    abstract val viewId: Int

    abstract val animId: Int        //弹窗动画

    interface OnPopWindowViewClick {
        fun onViewClick(view: View)
    }
}