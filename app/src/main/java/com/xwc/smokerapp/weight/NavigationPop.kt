package com.xwc.smokerapp.weight

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.xwc.smokerapp.R
import com.xwc.smokerapp.base.BasePopWindow
import com.xwc.smokerapp.utils.ScreenUtil

/**
 * Description：选择导航类型
 * author：Smoker
 * 2019/3/23 16:28
 */
class NavigationPop(context: Context) : BasePopWindow(context) {

    private var baidu: TextView? = null
    private var tenxun: TextView? = null
    private var gaode: TextView? = null

    override val viewId: Int
        get() = R.layout.pop_navigation

    override val animId: Int
        get() = R.style.AnimationPopupWindow_bottom_to_up

    init {
        width = ScreenUtil.getScreenWidth(mContext) - 50
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        initView()
    }

    private fun initView() {
        baidu = mView?.findViewById(R.id.tv_baidu)
        tenxun = mView?.findViewById(R.id.tv_tenxun)
        gaode = mView?.findViewById(R.id.tv_gaode)

        baidu?.setOnClickListener {
            onPopWindowViewClick?.onViewClick(it)
            dismiss()
        }
        tenxun?.setOnClickListener {
            onPopWindowViewClick?.onViewClick(it)
            dismiss()
        }
        gaode?.setOnClickListener {
            onPopWindowViewClick?.onViewClick(it)
            dismiss()
        }
    }
}