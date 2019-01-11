package com.qcloud.myapplication.weight

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.qcloud.myapplication.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class CustomToolbar@JvmOverloads constructor (
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : Toolbar(mContext, attrs, defStyleAttr), View.OnClickListener {

    private var toolbarView: View? = null
    private var isBack: Boolean = true              // 是否显示返回键
    private var isTitle: Boolean = true             // 是否显示标题
    private var isRightBtn: Boolean = false         // 是否显示右按钮

    private var titleColor: Int = Color.BLACK       // 标题颜色
    private var rightBtnColor: Int = Color.BLACK    // 右按钮颜色
    private var barBackground: Int = 0              // 标题栏背景颜色
    @DrawableRes
    private var backIcon: Int = 0                   // 返回图标
    @StringRes
    private var rightBtn: Int = 0                   // 右按钮
    @StringRes
    private var titleText: Int = 0                  // 标题

    var onBtnClickListener: OnBtnClickListener? = null

    init {
        initAttrs(attrs)
        initBar()
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = this.mContext.obtainStyledAttributes(attrs, R.styleable.CustomBar)
            try {
                // 返回
                isBack = typedArray.getBoolean(R.styleable.CustomBar_is_back, true)
                backIcon = typedArray.getResourceId(
                    R.styleable.CustomBar_back_icon,
                    R.mipmap.icon_back
                )

                // 标题
                isTitle = typedArray.getBoolean(R.styleable.CustomBar_is_title, true)
                titleText = typedArray.getResourceId(
                    R.styleable.CustomBar_title_text,
                    R.string.app_name
                )
                titleColor = typedArray.getColor(R.styleable.CustomBar_title_color, Color.BLACK)

                // 右按钮
                isRightBtn = typedArray.getBoolean(R.styleable.CustomBar_is_right_btn, false)
                rightBtn = typedArray.getResourceId(
                    R.styleable.CustomBar_right_btn,
                    R.string.btn_confirm
                )
                rightBtnColor = typedArray.getColor(R.styleable.CustomBar_right_btn_color, Color.BLACK)

                barBackground = typedArray.getColor(
                    R.styleable.CustomBar_bar_background, ContextCompat.getColor(mContext,
                        R.color.colorPrimary
                    ))
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun initBar() {
        toolbarView = LayoutInflater.from(mContext).inflate(R.layout.custom_toolbar, LinearLayout(mContext), false)
        addView(toolbarView!!, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, resources.getDimensionPixelOffset(
            R.dimen.appbar_height
        )))

        toolbarView!!.setBackgroundColor(barBackground)
        // 标题
        if (isTitle) {
            tv_title.visibility = View.VISIBLE
            tv_title.setText(titleText)
            tv_title.setTextColor(titleColor)
        } else {
            tv_title.visibility = View.GONE
        }

        // 返回
        if (isBack) {
            iv_back.visibility = View.VISIBLE
            iv_back.setImageResource(backIcon)
        } else {
            iv_back.visibility = View.GONE
        }
        iv_back.setOnClickListener(this)

        // 右按钮
        if (isRightBtn) {
            tv_right.visibility = View.VISIBLE
            tv_right.setText(rightBtn)
            tv_right.setTextColor(rightBtnColor)
        } else {
            tv_right.visibility = View.GONE
        }
        tv_right.setOnClickListener(this)
    }

    /**
     * 设置标题
     * */
    fun setTitleText(title: String) {
        tv_title?.text = title
    }

    /**
     * 设置标题
     * */
    fun setTitleRes(@StringRes titleRes: Int) {
        tv_title?.setText(titleRes)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_back -> {
                    // 返回键
                    if (onBtnClickListener != null) {
                        onBtnClickListener!!.onBtnClick(v, null)
                    } else {
                        ActivityCompat.finishAfterTransition(mContext as Activity)
                    }
                }
                R.id.tv_right -> {
                    onBtnClickListener?.onBtnClick(v, null)
                }
            }
        }
    }

    interface OnBtnClickListener {
        fun onBtnClick(view: View, value: String?)
    }

    interface OnTextChangeListener {
        fun onTextChange(value: Editable?)
    }
}