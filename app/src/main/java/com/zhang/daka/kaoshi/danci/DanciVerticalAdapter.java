package com.zhang.daka.kaoshi.danci;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiyun_greendao.info.WordInfo;
import com.zhang.daka.R;

import java.util.List;

import timber.log.Timber;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class DanciVerticalAdapter extends BaseQuickAdapter<WordInfo, BaseViewHolder> {
    public DanciVerticalAdapter(@Nullable List<WordInfo> data) {
        super(R.layout.item_danci_vertical_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WordInfo item) {
        helper.setText(R.id.tv_dancivertical_position, helper.getLayoutPosition() + "");
        helper.setText(R.id.tv_dancivertical_worden, item.getWordEn());
        helper.setText(R.id.tv_dancivertical_wordcn, item.getWordCn());

        WebView webView = helper.getView(R.id.webview_dancivertical);
        setting(webView);
        setWebViewChormeClient(webView);
        webView.loadUrl(String.format("http://m.iciba.com/%s", item.getWordEn()));

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    protected WebSettings setting(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setAllowContentAccess(true);
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        settings.setSupportZoom(true);//是否可以缩放，默认true
//        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setTextZoom(100); // 就可以禁止缩放，按照百分百显示。
        settings.setAppCacheEnabled(false);//是否使用缓存
        settings.setDomStorageEnabled(true);//DOM Storage
        settings.setAllowFileAccess(true); // 允许访问文件
        // displayWebview.getSettings().setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
        settings.setPluginState(WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        return settings;
    }

    protected void setWebViewChormeClient(WebView webView) {


        webView.setWebChromeClient(new WebChromeClient() {


        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                clickYuyin(webView);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Timber.d("----------->>>>>>>>-----------url:" + url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }


}
