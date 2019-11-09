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

    }



}
