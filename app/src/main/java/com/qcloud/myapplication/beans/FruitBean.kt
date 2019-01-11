package com.qcloud.myapplication.beans

import java.io.Serializable

/**
 * Description：
 * author：Smoker
 * 2018/12/3 17:51
 */
class FruitBean : Serializable {
    var name = ""
    var image = 0

    override fun toString(): String {
        return "FruitBean(name='$name', image='$image')"
    }
}