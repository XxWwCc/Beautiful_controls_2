package com.qcloud.myapplication.ui.view

import com.qcloud.myapplication.base.BaseView
import com.qcloud.myapplication.beans.FruitBean

/**
 * Description：首页
 * author：Smoker
 * 2019/3/22 14:29
 */
interface IMainView : BaseView {
    fun replaceList(list: List<FruitBean>)
}