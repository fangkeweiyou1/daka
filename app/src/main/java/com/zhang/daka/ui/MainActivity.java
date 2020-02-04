package com.zhang.daka.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.RemoteViews;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wushiyi.util.AppUtil;
import com.zhang.daka.R;
import com.zhang.daka.model.MenuModel;
import com.zhang.daka.mvp.BaseActivity;
import com.zhang.daka.ui.adapter.MenuAdapter;

import java.util.ArrayList;

/**
 * 音乐播放器主界面
 */
public class MainActivity extends BaseActivity {
    private static final String channelId = "daka_default_channel";

    public static final int notifyId = 567;
    DrawerLayout drawerlayout;
    RecyclerView menuRecyclerView;
    MenuAdapter menuAdapter;
    final ArrayList<MenuModel> menuModels = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void showNotification() {

        Intent dakaIntent = new Intent(this, DakaActivity.class);
        PendingIntent dakaPendingIntent = PendingIntent.getActivity(this, 0, dakaIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(AppUtil.INSTANCE.getPackageName(), R.layout.view_notify);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_pre, null);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_play, null);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_next, null);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_daka, dakaPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.tv_notify_name, null);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(remoteViews)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(null);
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Asia5B message",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = notificationBuilder.build();
        notificationManager.notify(notifyId /* ID of notification */, notification);
    }


    @Override
    public void initView() {
        showNotification();
        drawerlayout = findViewById(R.id.drawerlayout);
        menuRecyclerView = findViewById(R.id.rv_main_menu);

        menuModels.add(new MenuModel(R.drawable.ic_daka, "打卡"));
        View headView = View.inflate(this, R.layout.view_menu_head, null);
        menuAdapter = new MenuAdapter(menuModels);
        menuAdapter.addHeaderView(headView);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerView.setAdapter(menuAdapter);


    }


    @Override
    public void initEvent() {
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MenuModel menuModel = menuAdapter.getItem(position);
                switch (menuModel.getMenuName()) {
                    case "打卡":
                        clickPhoto();
                        break;
                }
            }
        });
    }

    public void clickPhoto() {
        Intent intent = new Intent(MainActivity.this, DakaActivity.class);
        intent.putExtra("isNowPhoto", true);
        startActivity(intent);
    }

    @Override
    public void initData() {

    }


    /**
     * 点击返回按钮时
     */
    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
            drawerlayout.closeDrawer(Gravity.LEFT);
        } else {
            //按返回键返回桌面
            moveTaskToBack(true);
        }
    }


}
