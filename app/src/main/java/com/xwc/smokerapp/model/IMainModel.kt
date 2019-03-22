package com.xwc.smokerapp.model

import com.xwc.smokerapp.beans.FruitBean
import com.xwc.smokerapp.net.DataCallback

/**
 * Description：
 * author：Smoker
 * 2019/3/22 16:04
 */
interface IMainModel {
    fun getFruitList(callback: DataCallback<List<FruitBean>>)
}