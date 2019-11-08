package com.zhang.daka.kaoshi.danci;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhang.daka.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class DanciHorizAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DanciHorizAdapter(@Nullable List<String> data) {
        super(R.layout.item_danci_horiz_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setIsRecyclable(false);

        final List<String> mDatas = new ArrayList<>();
        RecyclerView mRecyclerView = helper.getView(R.id.rv_danci_vertical);
        for (int i = 0; i < 30; i++) {
            mDatas.add("");
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        DanciVerticalAdapter mAdapter = new DanciVerticalAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        LinearSnapHelper snapHelper=new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
    }
}
