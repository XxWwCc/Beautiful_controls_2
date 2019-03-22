package com.qcloud.myapplication.model

import com.qcloud.myapplication.beans.FruitBean
import com.qcloud.myapplication.net.DataCallback

/**
 * Description：
 * author：Smoker
 * 2019/3/22 16:04
 */
interface IMainModel {
    fun getFruitList(callback: DataCallback<List<FruitBean>>)
}