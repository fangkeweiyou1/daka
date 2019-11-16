package com.zhang.daka.kaoshi.danci.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiyun_greendao.info.WordInfo;
import com.zhang.daka.R;

import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class DanciVerticalAdapter extends BaseQuickAdapter<WordInfo, BaseViewHolder> {
    public int showType;

    public DanciVerticalAdapter(@Nullable List<WordInfo> data) {
        super(R.layout.item_danci_vertical_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WordInfo item) {
        helper.setText(R.id.tv_dancivertical_position, helper.getLayoutPosition() + "");
        helper.setText(R.id.tv_dancivertical_worden, item.getWordEn());
        helper.setText(R.id.tv_dancivertical_wordcn, item.getWordCn());
        if (showType == 1) {//中文
            helper.setVisible(R.id.tv_dancivertical_wordcn, true);
            helper.setVisible(R.id.tv_dancivertical_worden, false);
        } else if (showType == 2) {
            helper.setVisible(R.id.tv_dancivertical_wordcn, false);
            helper.setVisible(R.id.tv_dancivertical_worden, true);
        } else {
            helper.setVisible(R.id.tv_dancivertical_wordcn, true);
            helper.setVisible(R.id.tv_dancivertical_worden, true);
        }

    }


    public void setShowType(int showType) {
        this.showType = showType;
        notifyDataSetChanged();
    }
}
