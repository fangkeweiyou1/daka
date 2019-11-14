package com.zhang.daka.kaoshi.danci.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhang.daka.R;

import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/13.
 */
public class AlphabetAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public AlphabetAdapter(@Nullable List<String> data) {
        super(R.layout.item_guess_alphabet, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_guess_alphabet, item);
        TextView tv_guess_alphabet = helper.getView(R.id.tv_guess_alphabet);
        if (TextUtils.equals(item, "delete")) {
            tv_guess_alphabet.setTextSize(20);
        } else {
            tv_guess_alphabet.setTextSize(40);
        }
        helper.addOnClickListener(R.id.tv_guess_alphabet);
    }
}
