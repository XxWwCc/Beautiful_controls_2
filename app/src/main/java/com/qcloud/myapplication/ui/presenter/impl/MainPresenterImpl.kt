package com.qcloud.myapplication.ui.presenter.impl

import com.qcloud.myapplication.base.BasePresenter
import com.qcloud.myapplication.beans.FruitBean
import com.qcloud.myapplication.model.IMainModel
import com.qcloud.myapplication.model.impl.MainModelImpl
import com.qcloud.myapplication.net.DataCallback
import com.qcloud.myapplication.ui.presenter.IMainPresenter
import com.qcloud.myapplication.ui.view.IMainView

/**
 * Description：首页
 * author：Smoker
 * 2019/3/22 14:30
 */
class MainPresenterImpl : BasePresenter<IMainView>(), IMainPresenter {

    private val mModule: IMainModel = MainModelImpl()

    override fun initList(step: Int) {
        if (step == 0) {
            val list: MutableList<FruitBean> = ArrayList()
            for (i in 0 until 20) {
                val bean = FruitBean()
                bean.image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553252855038&di=eb2d1f11c1a8539483c4dff6e47d9e4d&imgtype=0&src=http%3A%2F%2Fphoto.16pic.com%2F00%2F13%2F27%2F16pic_1327650_b.jpg"
                bean.name = "苹果"
                bean.key = 0
                list.add(bean)
            }
            mView?.replaceList(list)
        } else {
            mModule.getFruitList(object : DataCallback<List<FruitBean>> {
                override fun onSuccess(t: List<FruitBean>?, message: String?) {
                    if (t != null) {
                        mView?.replaceList(t)
                    } else {
                        mView?.loadError("请求数据失败")
                    }
                }

                override fun onError(status: Int, message: String) {
                    mView?.loadError(message)
                }
            })
        }
    }
}