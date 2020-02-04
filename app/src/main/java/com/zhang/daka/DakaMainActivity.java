package com.zhang.daka;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.zhang.daka.daka.MainAdapter;

import java.util.Calendar;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class DakaMainActivity extends AppCompatActivity {
    private static final String TAG = "DakaMainActivity";

    private RecyclerView mRecyclerView;
    private MainAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dakamain);
        context = this;

        //申请权限
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camera < 0) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 99);
        }
        int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (write < 0) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
        }

        mRecyclerView = findViewById(R.id.rv_main);
        adapter = new MainAdapter();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);





    }


    @Override
    protected void onResume() {
        super.onResume();
        final int position = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
        if (position >= 0 && position < adapter.getItemCount()) {
            mRecyclerView.scrollToPosition(position);
            adapter.notifyItemChanged(position);
        }
    }

}
