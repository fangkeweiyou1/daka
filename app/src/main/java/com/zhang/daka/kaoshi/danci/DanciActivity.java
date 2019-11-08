package com.zhang.daka.kaoshi.danci;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.zhang.daka.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class DanciActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DanciHorizAdapter mAdapter;
    private final List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danci);

        mRecyclerView = findViewById(R.id.rv_danci_horiz);
        mAdapter = new DanciHorizAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }
}
