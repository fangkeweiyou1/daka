package com.zhang.daka.kaoshi.danci.fragment;

import android.view.View;

import com.wushiyi.mvp.base.SimpleFragment;
import com.zhang.daka.R;
import com.zhang.daka.view.BesselView;

import org.jetbrains.annotations.NotNull;

/**
 * Created by zhangyuncai on 2019/11/16.
 */
public class CavasFragment extends SimpleFragment {

    private BesselView bv_cavas;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cavas;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView() {
        bv_cavas = rootView.findViewById(R.id.bv_cavas);
        rootView.findViewById(R.id.tv_cavas_clear).setOnClickListener(this::onClick);
    }

    @Override
    public void lazyFetchData() {

    }

    @Override
    public void onClick(@NotNull View v) {
        if (v.getId() == R.id.tv_cavas_clear) {
            bv_cavas.clear();
        }
    }
}
