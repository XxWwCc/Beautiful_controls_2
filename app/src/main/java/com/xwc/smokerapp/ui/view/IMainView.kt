package com.xwc.smokerapp.ui.view

import com.xwc.smokerapp.base.BaseView
import com.xwc.smokerapp.beans.FruitBean

/**
 * Description：首页
 * author：Smoker
 * 2019/3/22 14:29
 */
interface IMainView : BaseView {
    fun replaceList(list: List<FruitBean>)
}