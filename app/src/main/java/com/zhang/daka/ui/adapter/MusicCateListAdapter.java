package com.zhang.daka.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhang.daka.R;
import com.zhang.daka.model.MusicCateModel;

import java.util.List;

public class MusicCateListAdapter extends BaseQuickAdapter<MusicCateModel, BaseViewHolder> {
    public MusicCateListAdapter(@Nullable List<MusicCateModel> data) {
        super(R.layout.item_musiccatelist_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicCateModel item) {
        helper.setText(R.id.tv_popupitem_text, item.getText());
        helper.addOnClickListener(R.id.tv_musiccatelist_delete);
        helper.setGone(R.id.tv_musiccatelist_delete, !TextUtils.equals(item.getCateName(), "本地音乐"));
    }
}
