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

    private int selection = 0;

    public String getSelectAlphabet() {
        if (mData.size() > selection) {
            return mData.get(selection);
        }
        return "";
    }

    public AlphabetAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_guess_alphabet, item);

        TextView tv_guess_alphabet = helper.getView(R.id.tv_guess_alphabet);
        tv_guess_alphabet.setSelected(selection == helper.getLayoutPosition());

        helper.itemView.setOnClickListener(v -> {
            selection = helper.getLayoutPosition();
            notifyDataSetChanged();
        });

    }
}
