package com.qcloud.myapplication.activity

import android.Manifest
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import com.qcloud.myapplication.adapter.FruitAdapter
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.widget.GridLayoutManager
import android.telephony.SmsManager
import android.transition.Slide
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.qcloud.myapplication.beans.FruitBean
import com.qcloud.myapplication.R
import com.qcloud.myapplication.weight.CustomToolbar
import com.qcloud.myapplication.weight.PermissionManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val fruits: MutableList<FruitBean> = ArrayList()

    private val code = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slide = Slide()
            slide.duration = 500
            window.exitTransition = slide
        }

        initView()
        initFruits()
    }

    private fun initView() {
        custom_toolbar.onBtnClickListener = object : CustomToolbar.OnBtnClickListener {
            override fun onBtnClick(view: View, value: String?) {
                when (view.id) {
                    R.id.iv_back -> drawer_layout.openDrawer(GravityCompat.START)
                }
            }
        }
        nav_view.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.nav_account -> {
                    BannerTestActivity.openActivity(this)
                }
                R.id.nav_setting -> {
                    WebViewActivity.openActivity(this)
                }
                R.id.nav_phone -> {
                    DialogTest.openActivity(this)
                }
                R.id.nav_telephone -> {
                    getDate()
                }
                R.id.nav_time -> {

                }
            }
            drawer_layout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
        fab.setOnClickListener{
            view ->  Snackbar.make(view, "Send Notice", Snackbar.LENGTH_SHORT)
                            .setAction("Yes", View.OnClickListener {
                                checkPermissions()
                            })
                            .show()
        }

        val layoutManager = GridLayoutManager(this, 2)
        list_fruit.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruits)
        list_fruit.adapter = adapter
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh.setOnRefreshListener {
            refreshFruits()
        }
    }

    /**
     * 检查权限
     * */
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkIs360Rom() && !NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                //判断是否是360手机并且是否打开通知功能
                requestToSetting()
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, Array<String>(1) { Manifest.permission.SEND_SMS }, code)
                } else {
                    sendNotice()
                }
            }
        } else {
            sendNotice()
        }
    }

    /**
     * 检查是否360系统手机
     * */
    fun checkIs360Rom(): Boolean {
        // fix issue https://github.com/zhaozepeng/FloatWindowPermission/issues/9
        return Build.MANUFACTURER.contains("QiKU") || Build.MANUFACTURER.contains("360")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            code -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendNotice()
                } else {
                    Toast.makeText(this, "发送短信的权限已被拒绝，无法发送短信", Toast.LENGTH_SHORT).show()
                }
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                    requestToSetting()
                } else {
                    Toast.makeText(this, "请去设置中打开发送短信的权限，否则无法发送短信", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun requestToSetting() {
        val record = AlertDialog.Builder(this)
            .setNegativeButton("取消"){_,_ -> }
            .setPositiveButton("确定") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, 2)
            }
        if (checkIs360Rom()) {
            record.setMessage("请开启发送通知功能")
        } else {
            record.setMessage("请开启发送短信功能")
        }
        record.show()
    }

    /**
     * 发送一条消息
     * */
    private fun sendNotice() {
        val CHANNEL_ID = "channel_id_1"
        val CHANNEL_NAME = "channel_name_1"
        val intent = Intent(this, BannerTestActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(notificationChannel)
        }
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("This is SmokerApp notice title")
            .setContentText("This is content text")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.smoker)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentIntent(pi)
            .setAutoCancel(true)
//            .setSound(Uri.fromFile(File("/system/media/audio/ringtones/luna.ogg")))
//            .setVibrate(longArrayOf(0, 1000, 1000, 1000))
//            .setLights(Color.GREEN, 1000, 1000)
            .setDefaults(NotificationCompat.DEFAULT_ALL) //默认的
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()
        manager.notify(1,notification)
    }

    /**
     * 选择时间弹窗
     * */
    private fun getDate() {
        var date = ""
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val day =  calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                date = "$year-${fillZero(month + 1)}-${fillZero(day)}"
                Toast.makeText(this, date, Toast.LENGTH_LONG).show()
            }, year, month, day)
        dialog.show()
        val window = dialog.window
        window?.setLayout(getScreenWidth() - 40, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
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

    private fun refreshFruits() {
        Thread(Runnable {
            kotlin.run {
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                runOnUiThread{
                    kotlin.run {
                        initFruits()
                        swipe_refresh.isRefreshing = false
                    }
                }
            }
        }).start()
    }

    private fun initFruits() {
        for(i in 0 until 50) {
            val bean = FruitBean()
            bean.name = "Apple"
            bean.image = R.mipmap.smoker
            fruits.add(bean)
        }
    }
}
