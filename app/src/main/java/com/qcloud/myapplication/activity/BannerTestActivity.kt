package com.qcloud.myapplication.activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.widget.Toast
import com.qcloud.myapplication.R
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_banner.*
import com.qcloud.myapplication.weight.GlideImageLoader

/**
 * Description：轮播图
 * author：Smoker
 * 2018/12/11 10:03
 */
class BannerTestActivity : AppCompatActivity() {

    private var images: MutableList<String> = ArrayList()
    private var titles: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slide = Slide()
            slide.duration = 500
            window.enterTransition = slide
        }
        initData()
        initView(0)
        initView(1)
        initView(2)
        initView(3)
        initView(4)
        initView(5)
        initView(6)
        initView(7)
        initView(8)
        initView(9)
        initView(10)
        initView(11)
        initView(12)
        initView(13)
        initView(14)
        initView(15)
        initClick()
    }

    private fun initView(no: Int) {
        var bannerView: Banner? = null
        when (no) {
            0 -> {
                bannerView = banner
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
                bannerView.setBannerAnimation(Transformer.DepthPage)
            }
            1 -> {
                //右下图片指示器
                bannerView = banner1
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.Accordion)
            }
            2 -> {
                //右下数字指示器
                bannerView = banner2
                bannerView.setBannerStyle(BannerConfig.NUM_INDICATOR)
                bannerView.setBannerAnimation(Transformer.BackgroundToForeground)
            }
            3 -> {
                // 底部有标题（左下），指示器（右下）
                bannerView = banner3
                bannerView.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                bannerView.setBannerAnimation(Transformer.CubeIn)
            }
            4 -> {//左下标题，指示器右下（标题之上）
                bannerView = banner4
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
                bannerView.setBannerAnimation(Transformer.FlipHorizontal)
            }
            5 -> {
                //底部有标题（左下），指示器（右下）
                bannerView = banner5
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                bannerView.setBannerAnimation(Transformer.RotateDown)
            }
            6 -> {
                //
                bannerView = banner6
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.ZoomIn)
            }
            7 -> {
                bannerView = banner7
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.Tablet)
            }
            8 -> {
                bannerView = banner8
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.FlipVertical)
            }
            9 -> {
                bannerView = banner9
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.ScaleInOut)
            }
            10 -> {
                bannerView = banner10
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.Stack)
            }
            11 -> {
                bannerView = banner11
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.ZoomOut)
            }
            12 -> {
                bannerView = banner12
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.ForegroundToBackground)
            }
            13 -> {
                bannerView = banner13
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.RotateUp)
            }
            14 -> {
                bannerView = banner14
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.FlipVertical)
            }
            15 -> {
                bannerView = banner15
                bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                bannerView.setBannerAnimation(Transformer.CubeOut)
            }
        }

        bannerView?.setBannerTitles(titles)
        bannerView?.setDelayTime(3000)
        bannerView?.setIndicatorGravity(BannerConfig.RIGHT)
        bannerView?.setImageLoader(GlideImageLoader())
        bannerView?.setImages(images)
        bannerView?.start()
    }

    private fun initData() {
        images.add("http://wx2.sinaimg.cn/large/93d59c38gy1fmdwno2xv0j210z0kutco.jpg")
        images.add("http://5b0988e595225.cdn.sohucs.com/images/20180311/1bdc0751ef97456b816be561dc49a798.jpeg")
        images.add("http://i0.hdslb.com/bfs/archive/f5dd1bc78239f471887612d627b3e33c33e97bfa.jpg")
        images.add("http://p0.ifengimg.com/pmop/2018/0813/653092B53608CB1A0EEA3AF27F5F8EE6CD3E5EB2_size215_w1080_h800.jpeg")
        images.add("http://08imgmini.eastday.com/mobile/20181023/20181023214330_67abf3abfd67a08ed501066300cc060c_2.jpeg")

        titles.add("超级英雄")
        titles.add("英雄联盟")
        titles.add("复仇者联盟3")
        titles.add("漫威大电影")
        titles.add("绿巨人到底有多“大”")
    }

    private fun initClick() {
        banner.setOnBannerListener{
            index -> Toast.makeText(this, titles[index], Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun openActivity(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.startActivity(Intent(context, BannerTestActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle())
            } else {
                context.startActivity(Intent(context, BannerTestActivity::class.java))
            }
        }
    }
}