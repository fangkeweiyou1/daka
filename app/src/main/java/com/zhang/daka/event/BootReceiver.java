package com.zhang.daka.event;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.wushiyi.util.AppUtil;
import com.zhang.daka.R;
import com.zhang.daka.ui.MainActivity;
import com.zhang.daka.ui.SplashActivity;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.e("boot", "进来了！");
//        Toast.makeText(context,"启动了",Toast.LENGTH_SHORT).show();
//        // 在这个方法里面开启Activity
//        Intent intent1 = new Intent(context, SplashActivity.class);
//        // 注意不能在广播接收者里面直接开启Activity，需要添加一个任务栈的标记，
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        // 开启Activity
//        context.startActivity(intent1);
        showNotification(context);
    }

    private void showNotification(Context context) {

        Intent dakaIntent = new Intent(context, SplashActivity.class);
        PendingIntent dakaPendingIntent = PendingIntent.getActivity(context, 0, dakaIntent, PendingIntent.FLAG_ONE_SHOT);


        RemoteViews remoteViews = new RemoteViews(AppUtil.INSTANCE.getPackageName(), R.layout.view_notify);
        remoteViews.setOnClickPendingIntent(R.id.ll_notify_vessel, dakaPendingIntent);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, MainActivity.channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(remoteViews)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(null);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(MainActivity.channelId,
                    "Asia5B message",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notificationManager.notify(MainActivity.notifyId /* ID of notification */, notification);
    }

}
