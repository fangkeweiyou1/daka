package com.zhang.daka.kaoshi.danci;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.jiyun_greendao.info.WordInfo;
import com.wushiyi.mvp.base.SimpleFragment;
import com.zhang.daka.R;
import com.zhang.daka.event.IntervalEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by zhangyuncai on 2019/11/9.
 */
public class DanciVerticalFragment extends SimpleFragment {
    int position;
    RecyclerView mRecyclerView;
    private Disposable subscribe;
    private ProgressBar pb_dancivertical;
    private LinearLayoutManager linearLayoutManager;
    private RadioGroup rg_dancivertical;
    private WebView webView;
    private DanciVerticalAdapter mAdapter;
    private List<WordInfo> wordInfos;


    @Override
    public int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_dancivertical;
    }


    @Override
    public void initView() {

        pb_dancivertical = getView().findViewById(R.id.pb_dancivertical);
        pb_dancivertical.setMax(10);
        rg_dancivertical = getView().findViewById(R.id.rg_dancivertical);
        webView = getView().findViewById(R.id.webview_dancivertical);
        setting(webView);
        setWebViewChormeClient(webView);

        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
            wordInfos = (List<WordInfo>) getArguments().getSerializable("datas");
            mRecyclerView = getView().findViewById(R.id.rv_dancivertical);
            mAdapter = new DanciVerticalAdapter(wordInfos);
            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            LinearSnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(mRecyclerView);
        }

    }

    @Override
    public void initEvent() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition >= 0 && wordInfos.size() > firstVisibleItemPosition) {
                    WordInfo wordInfo = wordInfos.get(firstVisibleItemPosition);
                    loadUrl(wordInfo);
                }
            }
        });
    }

    @Override
    public void lazyFetchData() {

    }

    public void inverval() {
        subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Timber.d("----------->>>>>>>>-----------aLong:" + aLong);
                    if (aLong % 10 == 0 && aLong != 0) {
                        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                        firstVisibleItemPosition++;
                        mRecyclerView.smoothScrollToPosition(firstVisibleItemPosition);

                    }
                    if (aLong % 3 == 0) {
                        clickYuyin();
                    }
                    pb_dancivertical.setProgress(Math.toIntExact(aLong % 10), true);
                }, throwable -> throwable.printStackTrace());

    }


    private void clickYuyin() {

        if (rg_dancivertical.getCheckedRadioButtonId() == R.id.rb_dancivertical_english) {
            webView.loadUrl("javascript:document.getElementsByClassName(\"dic-sound\")[0].click();");
        } else if (rg_dancivertical.getCheckedRadioButtonId() == R.id.rb_dancivertical_america) {
            webView.loadUrl("javascript:document.getElementsByClassName(\"dic-sound\")[1].click();");
        } else {

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        endInterval();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && isViewPrepared()) {
            endInterval();
        }
    }

    private void endInterval() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIntervalEvent(IntervalEvent event) {
        if (event != null && event.position == position) {
            inverval();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
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

    private void loadUrl(WordInfo item) {

        webView.loadUrl(String.format("http://m.iciba.com/%s", item.getWordEn()));
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
