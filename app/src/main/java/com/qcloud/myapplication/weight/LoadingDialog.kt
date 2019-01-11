package com.qcloud.myapplication.weight

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import com.qcloud.myapplication.R

/**
 * Description：
 * author：Smoker
 * 2018/12/19 11:18
 */
class LoadingDialog(context: Context) : Dialog(context) {
    private val animationDrawable: AnimationDrawable?

    init {
        setContentView(R.layout.dialog_loading)
        window?.setBackgroundDrawable(ColorDrawable(0))

        // 去除Holo主题的蓝色线条
        try {
            val dividerID = context.resources.getIdentifier("android:id/titleDivider", null, null)
            val divider = findViewById<View>(dividerID)
            divider?.setBackgroundColor(Color.TRANSPARENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setCanceledOnTouchOutside(false)
        val imageView = findViewById<ImageView>(R.id.img_loading)
        animationDrawable = imageView.drawable as AnimationDrawable
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        animationDrawable?.start()
    }
}