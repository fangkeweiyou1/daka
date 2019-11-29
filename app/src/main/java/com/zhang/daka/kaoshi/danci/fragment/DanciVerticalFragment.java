package com.zhang.daka.kaoshi.danci.fragment;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jiyun_greendao.DBOpenHelper;
import com.jiyun_greendao.info.WordInfo;
import com.jiyun_greendao.info.WordInfoDao;
import com.wushiyi.mvp.base.SimpleFragment;
import com.zhang.daka.R;
import com.zhang.daka.config.AppConfigKt;
import com.zhang.daka.event.AddDanciEvent;
import com.zhang.daka.event.FullScreenEvent;
import com.zhang.daka.event.IntervalEvent;
import com.zhang.daka.event.ShowTypeEvent;
import com.zhang.daka.kaoshi.danci.adapter.DanciVerticalAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import timber.log.Timber;

/**
 * Created by zhangyuncai on 2019/11/9.
 */
public class DanciVerticalFragment extends SimpleFragment {
    public static int intervalTime = 10;
    int position;
    RecyclerView mRecyclerView;

    private ProgressBar pb_dancivertical;
    private LinearLayout ll_dancevertical_fullscreen_vessel;
    private TextView tv_dancivertical_worden;
    private TextView tv_dancivertical_wordcn;
    private SeekBar sb_dancivertical_progress;
    private LinearLayoutManager linearLayoutManager;
    private WebView webView;
    private DanciVerticalAdapter mAdapter;
    private List<WordInfo> wordInfos;

    private String alphabet;

    @Override
    public int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_dancivertical;
    }


    @Override
    public void initView() {

        pb_dancivertical = getView().findViewById(R.id.pb_dancivertical);
        ll_dancevertical_fullscreen_vessel = getView().findViewById(R.id.ll_dancevertical_fullscreen_vessel);
        tv_dancivertical_worden = getView().findViewById(R.id.tv_dancivertical_worden);
        tv_dancivertical_wordcn = getView().findViewById(R.id.tv_dancivertical_wordcn);
        pb_dancivertical.setMax(10);
        sb_dancivertical_progress = getView().findViewById(R.id.sb_dancivertical_progress);

        webView = getView().findViewById(R.id.webview_dancivertical);
        setting(webView);
        setWebViewChormeClient(webView);

        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
            alphabet = getArguments().getString("alphabet");
            WordInfoDao dao = DBOpenHelper.getWordInfoDao();
            wordInfos = dao.queryBuilder().where(WordInfoDao.Properties.Alpha.eq(alphabet)).build().list();
            sb_dancivertical_progress.setMax(wordInfos.size());
            mRecyclerView = getView().findViewById(R.id.rv_dancivertical);
            mAdapter = new DanciVerticalAdapter(wordInfos);
            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(mRecyclerView);

            int index = 0;
            switch (alphabet) {
                case "a":
                    index = AppConfigKt.getAIndex();
                    break;
                case "b":
                    index = AppConfigKt.getBIndex();
                    break;
                case "c":
                    index = AppConfigKt.getCIndex();
                    break;
                case "d":
                    index = AppConfigKt.getDIndex();
                    break;
                case "e":
                    index = AppConfigKt.getEIndex();
                    break;
                case "f":
                    index = AppConfigKt.getFIndex();
                    break;
                case "g":
                    index = AppConfigKt.getGIndex();
                    break;
                case "h":
                    index = AppConfigKt.getHIndex();
                    break;
                case "i":
                    index = AppConfigKt.getIIndex();
                    break;
                case "j":
                    index = AppConfigKt.getJIndex();
                    break;
                case "k":
                    index = AppConfigKt.getKIndex();
                    break;
                case "l":
                    index = AppConfigKt.getLIndex();
                    break;
                case "m":
                    index = AppConfigKt.getMIndex();
                    break;
                case "n":
                    index = AppConfigKt.getNIndex();
                    break;
                case "o":
                    index = AppConfigKt.getOIndex();
                    break;
                case "p":
                    index = AppConfigKt.getPIndex();
                    break;
                case "q":
                    index = AppConfigKt.getQIndex();
                    break;
                case "r":
                    index = AppConfigKt.getRIndex();
                    break;
                case "s":
                    index = AppConfigKt.getSIndex();
                    break;
                case "t":
                    index = AppConfigKt.getTIndex();
                    break;
                case "u":
                    index = AppConfigKt.getUIndex();
                    break;
                case "v":
                    index = AppConfigKt.getVIndex();
                    break;
                case "w":
                    index = AppConfigKt.getWIndex();
                    break;
                case "x":
                    index = AppConfigKt.getXIndex();
                    break;
                case "y":
                    index = AppConfigKt.getYIndex();
                    break;
                case "z":
                    index = AppConfigKt.getZIndex();
                    break;


            }
            if (index >= 0 && wordInfos.size() > index) {
                mRecyclerView.scrollToPosition(index);
            }
        }

        findWordInfo(0);

    }

    @Override
    public void initEvent() {
        ll_dancevertical_fullscreen_vessel.setOnClickListener(v -> {
        });
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


            switch (alphabet) {
                case "a":
                    AppConfigKt.setAIndex(index);
                    break;
                case "b":
                    AppConfigKt.setBIndex(index);
                    break;
                case "c":
                    AppConfigKt.setCIndex(index);
                    break;
                case "d":
                    AppConfigKt.setDIndex(index);
                    break;
                case "e":
                    AppConfigKt.setEIndex(index);
                    break;
                case "f":
                    AppConfigKt.setFIndex(index);
                    break;
                case "g":
                    AppConfigKt.setGIndex(index);
                    break;
                case "h":
                    AppConfigKt.setHIndex(index);
                    break;
                case "i":
                    AppConfigKt.setIIndex(index);
                    break;
                case "j":
                    AppConfigKt.setJIndex(index);
                    break;
                case "k":
                    AppConfigKt.setKIndex(index);
                    break;
                case "l":
                    AppConfigKt.setLIndex(index);
                    break;
                case "m":
                    AppConfigKt.setMIndex(index);
                    break;
                case "n":
                    AppConfigKt.setNIndex(index);
                    break;
                case "o":
                    AppConfigKt.setOIndex(index);
                    break;
                case "p":
                    AppConfigKt.setPIndex(index);
                    break;
                case "q":
                    AppConfigKt.setQIndex(index);
                    break;
                case "r":
                    AppConfigKt.setRIndex(index);
                    break;
                case "s":
                    AppConfigKt.setSIndex(index);
                    break;
                case "t":
                    AppConfigKt.setTIndex(index);
                    break;
                case "u":
                    AppConfigKt.setUIndex(index);
                    break;
                case "v":
                    AppConfigKt.setVIndex(index);
                    break;
                case "w":
                    AppConfigKt.setWIndex(index);
                    break;
                case "x":
                    AppConfigKt.setXIndex(index);
                    break;
                case "y":
                    AppConfigKt.setYIndex(index);
                    break;
                case "z":
                    AppConfigKt.setZIndex(index);
                    break;


            }
        }
    }

    @Override
    public void lazyFetchData() {

    }

    public void inverval(IntervalEvent event) {
        long aLong = event.aLong;
        if (aLong % intervalTime == 0 && aLong != 0) {
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            firstVisibleItemPosition++;
            if (firstVisibleItemPosition == wordInfos.size()) {
                mRecyclerView.scrollToPosition(0);
            } else {
                mRecyclerView.smoothScrollToPosition(firstVisibleItemPosition);
            }

        }
        if (aLong % 3 == 0) {
            clickYuyin();
        }
        pb_dancivertical.setProgress(Math.toIntExact(aLong % intervalTime), true);

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFullScreenEvent(FullScreenEvent event) {
        if (event != null) {
            ll_dancevertical_fullscreen_vessel.setVisibility(event.isFullScreen ? View.VISIBLE : View.GONE);
        }
    }

    public int showType;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowTypeEvent(ShowTypeEvent event) {
        if (event != null) {
            this.showType = event.showType;
            mAdapter.setShowType(showType);
            setShowTypeView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddDanciEvent(AddDanciEvent event) {
        if (event != null) {
            WordInfoDao dao = DBOpenHelper.getWordInfoDao();
            wordInfos.clear();
            wordInfos.addAll(dao.queryBuilder().where(WordInfoDao.Properties.Alpha.eq(alphabet)).build().list());
            mAdapter.notifyDataSetChanged();
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

        tv_dancivertical_wordcn.setText(item.getWordCn());
        tv_dancivertical_worden.setText(item.getWordEn());

       setShowTypeView();
    }

    private void setShowTypeView()
    {
        if (showType == 1) {//中文
            tv_dancivertical_wordcn.setVisibility(View.VISIBLE);
            tv_dancivertical_worden.setVisibility(View.INVISIBLE);
        } else if (showType == 2) {
            tv_dancivertical_wordcn.setVisibility(View.INVISIBLE);
            tv_dancivertical_worden.setVisibility(View.VISIBLE);
        } else {
            tv_dancivertical_wordcn.setVisibility(View.VISIBLE);
            tv_dancivertical_worden.setVisibility(View.VISIBLE);
        }
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
