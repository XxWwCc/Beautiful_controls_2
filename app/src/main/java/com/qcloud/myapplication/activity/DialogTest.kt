package com.qcloud.myapplication.activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import com.qcloud.myapplication.R
import com.qcloud.myapplication.weight.LoadingDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_dialog_test.*
import java.util.concurrent.TimeUnit

/**
 * Description：
 * author：Smoker
 * 2018/12/19 11:15
 */
class DialogTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_test)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val explode = Explode()
            explode.duration = 500
            window.enterTransition = explode
        }
        val loadingDialog = LoadingDialog(this)
        Observable.timer(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {loadingDialog.show()}
        Observable.timer(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {loadingDialog.dismiss()
            tv_load_success.text = "加载成功"}
    }

    companion object {
        fun openActivity(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.startActivity(Intent(context, DialogTest::class.java), ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle())
            } else {
                context.startActivity(Intent(context, DialogTest::class.java))
            }
        }
    }
}