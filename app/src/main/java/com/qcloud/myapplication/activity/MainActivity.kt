package com.qcloud.myapplication.activity

import com.qcloud.myapplication.adapter.FruitAdapter
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.widget.GridLayoutManager
import android.transition.Slide
import android.view.View
import android.widget.Toast
import com.qcloud.myapplication.beans.FruitBean
import com.qcloud.myapplication.R
import com.qcloud.myapplication.weight.CustomToolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val fruits: MutableList<FruitBean> = ArrayList()

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
            }
            drawer_layout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
        fab.setOnClickListener{
            view ->  Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
                            .setAction("Undo", View.OnClickListener {
                                Toast.makeText(this, "Data restored", Toast.LENGTH_SHORT).show()
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
