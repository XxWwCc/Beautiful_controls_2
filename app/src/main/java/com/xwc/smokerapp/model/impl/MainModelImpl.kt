package com.xwc.smokerapp.model.impl

import com.xwc.smokerapp.beans.FruitBean
import com.xwc.smokerapp.enums.NavigationType
import com.xwc.smokerapp.model.IMainModel
import com.xwc.smokerapp.net.DataCallback

/**
 * Description：
 * author：Smoker
 * 2018/12/21 14:35
 */
class MainModelImpl : IMainModel {
    override fun getFruitList(callback: DataCallback<List<FruitBean>>) {
        val list: MutableList<FruitBean> = ArrayList()
        var bean = FruitBean()
        bean.key = NavigationType.TYPE_BAIDU
        bean.name = NavigationType.getName(NavigationType.TYPE_BAIDU)
        bean.image = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=754965178,1416923748&fm=26&gp=0.jpg"
        list.add(bean)

        bean = FruitBean()
        bean.key = NavigationType.TYPE_GAODE
        bean.name = NavigationType.getName(NavigationType.TYPE_GAODE)
        bean.image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553235381346&di=9bc7cdc1bc2d442b90b3c2124c9e14d3&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fc7dacb5a33a9652316381d7bf1f3558a0ae1cfa054e41-nkEa4X_fw658"
        list.add(bean)

        bean = FruitBean()
        bean.key = NavigationType.TYPE_TENXUN
        bean.name = NavigationType.getName(NavigationType.TYPE_TENXUN)
        bean.image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553234943501&di=0852a85e01e7f4f642f04ea8a975d656&imgtype=0&src=http%3A%2F%2Fis3.mzstatic.com%2Fimage%2Fthumb%2FPurple3%2Fv4%2Fa0%2F96%2F33%2Fa09633fc-fbcd-3b60-1e6c-8630a8c44651%2Fmzl.vrgjacpe.jpg%2F0x0ss-30.jpg"
        list.add(bean)

        callback.onSuccess(list, "加载数据成功")
    }
}