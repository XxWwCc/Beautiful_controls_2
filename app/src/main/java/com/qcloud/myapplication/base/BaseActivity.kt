package com.qcloud.myapplication.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import com.qcloud.myapplication.weight.LoadingDialog
import timber.log.Timber

/**
 * Description：活动基类
 * author：Smoker
 * 2019/3/22 11:22
 */
abstract class BaseActivity<V, T: BasePresenter<V>> : AppCompatActivity() {
    open var mContext: Context? = null
    open var isRunning: Boolean = false

    protected var mPresenter: T? = null

    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slide = Slide()
            slide.duration = 500
            window.exitTransition = slide
        }

        setContentView(layoutId)
        mContext = this
        isRunning = true
        BaseApplication.mAppManager?.addActivity(this)
        Timber.i("加入活动：$this")

        if (mPresenter == null) {
            mPresenter = initPresenter()
        }
        mPresenter?.attach(this as V)

        initViewAndData()
    }

    protected abstract val layoutId: Int

    protected abstract fun initPresenter(): T?

    protected abstract fun initViewAndData()

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detach()
        BaseApplication.mAppManager?.killActivity(this)
        isRunning = false
        stopLoadingDialog()
    }

    fun startLoadingDialog() {
        if (mContext != null) {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(mContext!!)
            }
            loadingDialog?.show()
        }
    }

    fun stopLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog?.dismiss()
        }
        loadingDialog = null
    }

}