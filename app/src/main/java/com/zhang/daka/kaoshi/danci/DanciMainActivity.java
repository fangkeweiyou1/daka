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
import com.zhang.daka.daka.adapter.MenuAdapter;
import com.zhang.daka.net.HttpUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class DanciMainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DanciHorizAdapter mAdapter;
    private final List<String> mDatas = new ArrayList<>();

    private final List<String> menus=new ArrayList<>();
    private MenuAdapter menuAdapter=new MenuAdapter(menus);

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

        try {
            InputStream inputStream = getAssets().open("danci.txt");
            StringBuilder stringBuilder = new StringBuilder();
            byte[] bytes = new byte[1024];
            int length = -1;
            while ((length = inputStream.read(bytes)) != -1) {
                stringBuilder.append(new String(bytes, 0, length));
                if (stringBuilder.length() > 1000) {
                    break;
                }
            }
            inputStream.close();
            Timber.d("----------->>>>>>>>-----------stringBuilder:"+stringBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void daka(View view) {
        startActivity(new Intent(this, DakaMainActivity.class));
    }
}
