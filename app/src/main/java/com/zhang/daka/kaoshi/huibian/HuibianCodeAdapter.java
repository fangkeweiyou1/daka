package com.zhang.daka.kaoshi.huibian;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhang.daka.R;

import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/21.
 */
public class HuibianCodeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HuibianCodeAdapter(@Nullable List<String> data) {
        super(R.layout.item_huibiancode_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_huibiancode_alphabet, item);
    }
}
