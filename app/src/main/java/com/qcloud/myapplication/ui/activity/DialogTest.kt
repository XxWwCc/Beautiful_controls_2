package com.qcloud.myapplication.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.util.DisplayMetrics
import android.view.WindowManager
import com.qcloud.myapplication.R
import com.qcloud.myapplication.weight.LoadingDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_dialog_test.*
import java.util.concurrent.TimeUnit
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import android.widget.Toast
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.util.*

/**
 * Description：加载弹窗
 * author：Smoker
 * 2018/12/19 11:15
 */
class DialogTest : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
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
                    tv_load_success.text = "$year-${fillZero(monthOfYear+1)}-${fillZero(dayOfMonth)}"
                    loadImage()
                }
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(year, month, day)
                .setDoneText("确定")
                .setCancelText("取消")
            cdp.show(supportFragmentManager, "")
        }
    }

    private fun loadImage() {
        val url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548136930551&di=182c19e3bda4f8436370e0b96ad71487&imgtype=0&src=http%3A%2F%2Fpic.kekenet.com%2F2015%2F0727%2F57891437981090.gif"
        // picasso无法加载gif动态图，使用只会是静图
        Picasso.with(this)
            .load(url)
            .placeholder(R.mipmap.icon_no_network)      // 加载未完成显示图片
            .error(R.mipmap.icon_no_data)               // 加载出错图片
            .resize(800, 600)     /*重设宽高 可用resizeDimen（）代替，使用 dimen.xml
                                                 fit()方法可以不用计算大小调用resize，他直接把图片裁剪成imageview的大小*/
            .centerInside()                             // 宽或高已经到边缘时，不进行拉伸变形
            .rotate(90f)                   /*以（0，0）为中心旋转180度，可以使用rotate(degrees, x, y)指定旋转中心*/
            .transform(CropCircleTransformation())  // 使用的是一个专门支持picasso的框架
            .into(iv_congratulations)
    }

    /**
     * 给数字1~9前面添加 0
     * */
    private fun fillZero(number: Int) : String {
        return if (number < 10) "0" + number.toString() else number.toString()
    }

    /**
     * 获取屏幕宽度
     * */
    private fun getScreenWidth() : Int {
        val dm = DisplayMetrics()
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    /**
     * 获取屏幕高度
     * */
    private fun getScreenHeight() : Int {
        val dm = DisplayMetrics()
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
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