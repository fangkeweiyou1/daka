package com.zhang.daka.daka.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhang.daka.R;

import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/9.
 */
public class MenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MenuAdapter(@Nullable List<String> data) {
        super(R.layout.item_menu_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setIsRecyclable(false);
        helper.setText(R.id.tv_menu_content, item);
    }
}
