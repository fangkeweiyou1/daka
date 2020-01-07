package com.zhang.daka.kaoshi.danci.activity

import android.os.Build
import android.support.annotation.RequiresApi
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wushiyi.mvp.base.SimpleAppCompatActivity
import com.zhang.daka.R
import kotlinx.android.synthetic.main.activity_music.*

/**
 * Created by zhangyuncai on 2020/1/7.
 */

class MusicActivity:SimpleAppCompatActivity() {
    val webView :WebView by lazy { web_music }
    val musicUrl="http://music.163.com/m/song?id=1363948882&autoplay=true&market=baiduqk"
    override fun getLayoutId(): Int {
        return R.layout.activity_music
    }

    override fun initView() {
        setting(webView)
        setWebViewChormeClient(webView)
        webView.loadUrl(musicUrl)
    }
    override fun initData() {
    }

    override fun initEvent() {
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    protected fun setting(webView: WebView): WebSettings {
        val settings = webView.settings
        settings.javaScriptEnabled = true//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.allowContentAccess = true
        settings.setSupportMultipleWindows(true)
        //设置缓存模式
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.javaScriptCanOpenWindowsAutomatically = true//设置js可以直接打开窗口，如window.open()，默认为false
        settings.setSupportZoom(true)//是否可以缩放，默认true
        //        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        settings.useWideViewPort = true//设置此属性，可任意比例缩放。大视图模式
        settings.loadWithOverviewMode = true//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.textZoom = 100 // 就可以禁止缩放，按照百分百显示。
        settings.setAppCacheEnabled(false)//是否使用缓存
        settings.domStorageEnabled = true//DOM Storage
        settings.allowFileAccess = true // 允许访问文件
        // displayWebview.getSettings().setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
        settings.pluginState = WebSettings.PluginState.ON
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        return settings
    }

    protected fun setWebViewChormeClient(webView: WebView) {


        webView.webChromeClient = object : WebChromeClient() {


        }
        webView.webViewClient = object : WebViewClient() {

        }
    }
}