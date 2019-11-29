package com.zhang.daka.kaoshi.huibian;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhang.daka.R;

import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/21.
 */
public class HuibianAdapter  extends BaseQuickAdapter<HuibianModel, BaseViewHolder> {
    public HuibianAdapter(@Nullable List<HuibianModel> data) {
        super(R.layout.item_huibian_adapter,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuibianModel item) {
        helper.setText(R.id.tv_huibian_qustion,item.question);
        helper.setText(R.id.tv_huibian_anwser,item.anwser);
    }
}
