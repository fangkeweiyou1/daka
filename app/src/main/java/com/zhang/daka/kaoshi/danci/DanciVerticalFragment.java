package com.zhang.daka.kaoshi.danci;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.jiyun_greendao.info.WordInfo;
import com.wushiyi.mvp.base.SimpleFragment;
import com.zhang.daka.R;
import com.zhang.daka.event.IntervalEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import timber.log.Timber;

/**
 * Created by zhangyuncai on 2019/11/9.
 */
public class DanciVerticalFragment extends SimpleFragment {
    int position;
    RecyclerView mRecyclerView;

    private ProgressBar pb_dancivertical;
    private SeekBar sb_dancivertical_progress;
    private LinearLayoutManager linearLayoutManager;
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
        sb_dancivertical_progress = getView().findViewById(R.id.sb_dancivertical_progress);

        webView = getView().findViewById(R.id.webview_dancivertical);
        setting(webView);
        setWebViewChormeClient(webView);

        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
            wordInfos = (List<WordInfo>) getArguments().getSerializable("datas");
            sb_dancivertical_progress.setMax(wordInfos.size());
            mRecyclerView = getView().findViewById(R.id.rv_dancivertical);
            mAdapter = new DanciVerticalAdapter(wordInfos);
            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(mRecyclerView);
        }

        findWordInfo(0);

    }

    @Override
    public void initEvent() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    findWordInfo(firstVisibleItemPosition);
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                sb_dancivertical_progress.setProgress(firstVisibleItemPosition);
            }
        });
        sb_dancivertical_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (progress >= 0 && wordInfos.size() > progress) {
                        mRecyclerView.scrollToPosition(progress);
                        findWordInfo(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void findWordInfo(int index) {
        if (index >= 0 && wordInfos.size() > index) {
            WordInfo wordInfo = wordInfos.get(index);
            loadUrl(wordInfo);
        }
    }

    @Override
    public void lazyFetchData() {

    }

    public void inverval(IntervalEvent event) {
        long aLong = event.aLong;
        if (aLong % 10 == 0 && aLong != 0) {
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            firstVisibleItemPosition++;
            mRecyclerView.smoothScrollToPosition(firstVisibleItemPosition);

        }
        if (aLong % 3 == 0) {
            clickYuyin();
        }
        pb_dancivertical.setProgress(Math.toIntExact(aLong % 10), true);

    }


    private void clickYuyin() {
        RadioGroup rg_danci = getActivity().findViewById(R.id.rg_danci);
        if (rg_danci.getCheckedRadioButtonId() == R.id.rb_danci_english) {
            webView.loadUrl("javascript:document.getElementsByClassName(\"dic-sound\")[0].click();");
        } else if (rg_danci.getCheckedRadioButtonId() == R.id.rb_danci_america) {
            webView.loadUrl("javascript:document.getElementsByClassName(\"dic-sound\")[1].click();");
        } else {

        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIntervalEvent(IntervalEvent event) {
        if (event != null && event.position == position) {
            inverval(event);
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

    private String loadWord = null;

    private void loadUrl(WordInfo item) {

        //加载过的单词不在加载
        if (TextUtils.equals(item.getWordEn(), loadWord)) {
            return;
        }

        loadWord = item.getWordEn();

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
