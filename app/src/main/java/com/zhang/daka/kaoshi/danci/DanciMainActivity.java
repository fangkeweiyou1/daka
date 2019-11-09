package com.zhang.daka.kaoshi.danci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
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

    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private DanciHorizAdapter mAdapter;
    private final List<String> mDatas = new ArrayList<>();

    private final List<String> menus = new ArrayList<>();
    private MenuAdapter menuAdapter = new MenuAdapter(menus);
    private RecyclerView menuRecyclerView;
    private DividerItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danci);
        mActivity = this;
        menuRecyclerView = findViewById(R.id.rv_main_menu);

        initMenu();

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
            Timber.d("----------->>>>>>>>-----------stringBuilder:" + stringBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化左边菜单栏
     */
    private void initMenu() {
        menus.clear();
        menus.add("打卡");

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (dividerItemDecoration == null) {
            dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            menuRecyclerView.addItemDecoration(dividerItemDecoration);
        }
        menuRecyclerView.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (menuAdapter.getItem(position)) {
                    case "打卡":
                        startActivity(new Intent(mActivity, DakaMainActivity.class));
                        break;

                }
            }
        });

    }
}
