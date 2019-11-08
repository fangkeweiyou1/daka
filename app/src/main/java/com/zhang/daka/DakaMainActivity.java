package com.zhang.daka;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhang.daka.daka.MainAdapter;
import com.zhang.daka.daka.TimerService;

import java.util.Calendar;
import java.util.Locale;

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
        setContentView(R.layout.activity_main2);
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




//        setAmClock();
//        setPmClock();


    }

    /**
     * 设置上午闹钟
     */
    void setAmClock() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Intent intent = new Intent();
        intent.setAction("testalarm0");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /**
     * 设置上午闹钟
     */
    void setPmClock() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 28);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Intent intent = new Intent();
        intent.setAction("testalarm1");
        PendingIntent pendingIntent =PendingIntent.getBroadcast(context,0, intent,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
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

    public void stopClcok(View view) {
        Intent intent = new Intent(this, TimerService.class);
        stopService(intent);
    }
}
