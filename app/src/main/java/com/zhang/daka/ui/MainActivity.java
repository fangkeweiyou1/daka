package com.zhang.daka.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.wushiyi.util.AppUtil;
import com.zhang.daka.R;
import com.zhang.daka.mvp.BaseActivity;

/**
 * 音乐播放器主界面
 */
public class MainActivity extends BaseActivity {
    private static final String channelId = "daka_default_channel";

    public static final int notifyId = 567;
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
    }


    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }


}
