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
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import android.widget.Toast
import java.util.*


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

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val day =  calendar.get(Calendar.DAY_OF_MONTH)

        tv_load_success.setOnClickListener{
            val cdp = CalendarDatePickerDialogFragment()
                .setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    Toast.makeText(this, "$year-${fillZero(monthOfYear+1)}-${fillZero(dayOfMonth)}", Toast.LENGTH_LONG).show()
                }
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(year, month, day)
                .setDoneText("确定")
                .setCancelText("取消")
            cdp.show(supportFragmentManager, "")
        }
    }

    /**
     * 给数字1~9前面添加 0
     * */
    private fun fillZero(number: Int) : String {
        return if (number < 10) "0" + number.toString() else number.toString()
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