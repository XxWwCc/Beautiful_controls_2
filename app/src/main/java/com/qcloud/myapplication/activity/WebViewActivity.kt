package com.qcloud.myapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import com.qcloud.myapplication.R
import kotlinx.android.synthetic.main.activity_webview.*


/**
 * Description：
 * author：Smoker
 * 2018/12/11 15:45
 */
class WebViewActivity : AppCompatActivity() {

    private val content = "https://www.google.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        initWebView()
        initView()
    }

    private fun initView() {
        web_view.loadUrl(content)
//        web_view.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
        web_view.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    }

    /**
     * 初始化WebView
     * */
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        // 网页设置
        web_view.settings.javaScriptEnabled = true
        // 设置可以支持缩放
        web_view.settings.setSupportZoom(true)
        web_view.settings.blockNetworkImage = false
        // 开启 DOM storage API 功能
        web_view.settings.domStorageEnabled = true
        // 开启 database storage API 功能
        web_view.settings.databaseEnabled = true
        // 开启 Application Caches 功能
        web_view.settings.setAppCacheEnabled(true)

        web_view.webViewClient = MyWebViewClient(web_view)
    }

    /**
     * 自定义的WebViewClient
     */
    private inner class MyWebViewClient(webView: BridgeWebView) : BridgeWebViewClient(webView) {

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
//            resetImage()
        }
    }

    private fun resetImage() {
        web_view.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                " img.style.maxWidth = '100%';img.style.height='auto';" +
                "}" +
                "})()")
    }

    override fun onDestroy() {
        web_view?.let {
            val parent = it.parent as ViewGroup
            parent.removeAllViews()
            it.removeAllViews()
            it.clearCache(true)
            it.clearHistory()
            it.clearFormData()
            it.destroy()
        }
        super.onDestroy()
    }

    companion object {
        fun openActivity(context: Context) {
            context.startActivity(Intent(context, WebViewActivity::class.java))
        }
    }
}