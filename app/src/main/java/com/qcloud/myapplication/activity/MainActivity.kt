package com.qcloud.myapplication.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.qcloud.myapplication.adapter.FruitAdapter
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.view.GravityCompat
import android.support.v7.widget.GridLayoutManager
import android.transition.Slide
import android.util.Log
import android.view.View
import android.widget.Toast
import com.qcloud.myapplication.beans.FruitBean
import com.qcloud.myapplication.R
import com.qcloud.myapplication.weight.CustomToolbar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val fruits: MutableList<FruitBean> = ArrayList()
    private var imageUri: Uri? = null
    private var headLayout: View? = null
    private var headImage: CircleImageView? = null

    private val code = 1
    private val TAKE_PHOTO = 1

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
        headLayout = nav_view.inflateHeaderView(R.layout.nav_header)
        headImage = headLayout?.findViewById(R.id.civ_head_image)
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
                    GetContactsActivity.openActivity(this)
                }
                R.id.nav_telephone -> {

                }
                R.id.nav_time -> {
                    DialogTest.openActivity(this)
                }
            }
            drawer_layout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }

        headImage?.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                takePhoto()
            } else {
                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                startActivityForResult(intent, TAKE_PHOTO)
            }
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

    private fun takePhoto() {
        val outputImage = File(externalCacheDir, "output_image.jpg")
        try {
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        imageUri = FileProvider.getUriForFile(this, "com.qcloud.myapplication.FileProvider", outputImage)
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, TAKE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TAKE_PHOTO -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri!!))
                        headImage?.setImageBitmap(bitmap)
                    } else {
                        val bitmap = data?.extras?.get("data") as Bitmap
                        headImage?.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    /**
     * 检查发送短信权限
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

    /**
     * 去设置弹窗
     * */
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
