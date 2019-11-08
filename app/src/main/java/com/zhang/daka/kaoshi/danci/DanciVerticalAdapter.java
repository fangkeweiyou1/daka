package com.zhang.daka.kaoshi.danci;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhang.daka.R;

import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class DanciVerticalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DanciVerticalAdapter(@Nullable List<String> data) {
        super(R.layout.item_danci_vertical_adapter,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
    }
}
