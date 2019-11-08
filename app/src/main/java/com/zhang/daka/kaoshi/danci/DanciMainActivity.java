package com.zhang.daka.kaoshi.danci;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhang.daka.DakaMainActivity;
import com.zhang.daka.R;
import com.zhang.daka.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class DanciMainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DanciHorizAdapter mAdapter;
    private final List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danci);

        for (int i = 0; i < 30; i++) {
            mDatas.add("");
        }
        mRecyclerView = findViewById(R.id.rv_danci_horiz);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new DanciHorizAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        HttpUtils.translate("你好", true);
    }

    public void daka(View view) {
        startActivity(new Intent(this, DakaMainActivity.class));
    }
}
