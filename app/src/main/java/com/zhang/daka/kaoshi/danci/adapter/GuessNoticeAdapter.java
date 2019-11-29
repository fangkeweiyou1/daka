package com.zhang.daka.kaoshi.danci.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiyun_greendao.info.WordInfo;
import com.zhang.daka.R;

import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/14.
 */
public class GuessNoticeAdapter extends BaseQuickAdapter<WordInfo, BaseViewHolder> {
    public GuessNoticeAdapter(@Nullable List<WordInfo> data) {
        super(R.layout.item_guessnotice_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WordInfo item) {
        helper.setText(R.id.tv_guessnotice_word, item.getWordEn() + "   " + item.getWordCn());
    }
}
