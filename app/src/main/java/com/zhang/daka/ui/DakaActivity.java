package com.zhang.daka.ui;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import com.wushiyi.util.AppUtil;
import com.wushiyi.util.ToastUtilKt;
import com.wushiyi.util.UriUtil;
import com.zhang.daka.R;
import com.zhang.daka.daka.MainAdapter;
import com.zhang.daka.utils.DataHelper;
import com.zhang.daka.utils.WFileUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class DakaActivity extends AppCompatActivity {
    private static final String TAG = "DakaActivity";
    private static final String channelId = "daka_default_channel";
    public static final int Cut_PHOTO = 1;
    public static final int notifyId = 567;

    private RecyclerView mRecyclerView;
    private MainAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Context context;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daka);
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
        adapter = new MainAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);

        showNotification();


    }

    public void clickPhoto() {
        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (camera >= 0) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-");
            String date = simpleDateFormat.format(calendar.getTime());
            String cacheImagePath = WFileUtil.getCacheImagePath(context, date + (DataHelper.isAm() ? "am" : "pm"));
            System.out.println("---<<<>>>---cacheImagePath:" + cacheImagePath);
            //创建File对象,用于存储选择的照片
            File outputImage = new File(cacheImagePath);
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageUri = UriUtil.INSTANCE.getUri(outputImage);

            //隐式意图启动相机
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            // 启动相机程序
            startActivityForResult(intent, DakaActivity.Cut_PHOTO);
        } else {
            ToastUtilKt.showToast("请赋予拍照权限");
        }

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
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(remoteViews)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(null);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

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
    protected void onResume() {
        super.onResume();
        final int position = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
        if (position >= 0 && position < adapter.getItemCount()) {
            mRecyclerView.scrollToPosition(position);
            adapter.notifyItemChanged(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Cut_PHOTO:
                break;

        }
    }
}
