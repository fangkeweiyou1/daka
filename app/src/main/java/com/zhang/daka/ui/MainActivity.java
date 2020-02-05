package com.zhang.daka.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wushiyi.mvp.MvpExtendsKt;
import com.wushiyi.mvp.base.BaseFragmentPagerAdapter;
import com.wushiyi.util.AppUtil;
import com.zhang.daka.R;
import com.zhang.daka.event.MusicBroadCast;
import com.zhang.daka.model.MenuModel;
import com.zhang.daka.model.MusicModel;
import com.zhang.daka.mvp.BaseActivity;
import com.zhang.daka.ui.adapter.MenuAdapter;
import com.zhang.daka.ui.fragment.MusicHomeFragment;
import com.zhang.daka.ui.fragment.MusicListFragment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 音乐播放器主界面
 */
public class MainActivity extends BaseActivity {
    public static MainActivity mainActivity;
    private static final String channelId = "daka_default_channel";

    public static final int notifyId = 569;
    DrawerLayout drawerlayout;
    RecyclerView menuRecyclerView;
    MenuAdapter menuAdapter;
    final ArrayList<MenuModel> menuModels = new ArrayList<>();
    ViewPager mViewPager;
    TabLayout mTabLayout;
    //播放器容器
    private LinearLayout ll_music_vessel;
    //音乐封面
    private ImageView iv_music_thum;
    //音乐名称
    private TextView tv_music_name;
    //音乐作者
    private TextView tv_music_author;
    //上一首
    private ImageView iv_music_pre;
    //播放
    private ImageView iv_music_play;
    //下一首
    private ImageView iv_music_next;
    private MediaPlayer mediaPlayer;
    public final ArrayList<MusicModel> musicList = new ArrayList<>();
    public int currentPosition = 0;
    public boolean isPlayed = false;

    @Override
    public int getLayoutId() {
        mainActivity = this;
        return R.layout.activity_main;
    }

    private void showNotification() {

        Intent dakaIntent = new Intent(this, MainActivity.class);
        dakaIntent.putExtra("isNowPhoto", true);
        PendingIntent dakaPendingIntent = PendingIntent.getActivity(this, 0, dakaIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, MusicBroadCast.class);
        nextIntent.setAction("next");
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent preIntent = new Intent(this, MusicBroadCast.class);
        preIntent.setAction("pre");
        PendingIntent prePendingIntent = PendingIntent.getBroadcast(this, 0, preIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent palyIntent = new Intent(this, MusicBroadCast.class);
        palyIntent.setAction("paly");
        PendingIntent palyPendingIntent = PendingIntent.getBroadcast(this, 0, palyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(AppUtil.INSTANCE.getPackageName(), R.layout.view_notify);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_pre, prePendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_play, palyPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_next, nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_daka, dakaPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.tv_notify_name, mainPendingIntent);
        if (musicList.size() > currentPosition) {
            MusicModel musicModel = musicList.get(currentPosition);
            remoteViews.setTextViewText(R.id.tv_notify_name, musicModel.musicName);
        }
        try {
            if (mediaPlayer.isPlaying()) {
                remoteViews.setImageViewResource(R.id.iv_notify_play, R.drawable.lock_btn_pause);
            } else {
                remoteViews.setImageViewResource(R.id.iv_notify_play, R.drawable.lock_btn_play);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notificationManager.notify(notifyId /* ID of notification */, notification);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("isNowPhoto")) {
            clickPhoto();
        }
    }

    @Override
    public void initView() {
        showNotification();
        drawerlayout = findViewById(R.id.drawerlayout);
        menuRecyclerView = findViewById(R.id.rv_main_menu);
        mViewPager = findViewById(R.id.vp_main);
        mTabLayout = findViewById(R.id.tab_main);
        ll_music_vessel = findViewById(R.id.ll_music_vessel);
        iv_music_thum = findViewById(R.id.iv_music_thum);
        tv_music_name = findViewById(R.id.tv_music_name);
        tv_music_author = findViewById(R.id.tv_music_author);
        iv_music_pre = findViewById(R.id.iv_music_pre);
        iv_music_play = findViewById(R.id.iv_music_play);
        iv_music_next = findViewById(R.id.iv_music_next);

        menuModels.add(new MenuModel(R.drawable.ic_daka, "打卡"));
        View headView = View.inflate(this, R.layout.view_menu_head, null);
        menuAdapter = new MenuAdapter(menuModels);
        menuAdapter.addHeaderView(headView);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerView.setAdapter(menuAdapter);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(MvpExtendsKt.sNewStanceFragment(this, MusicListFragment.class));
        fragments.add(MvpExtendsKt.sNewStanceFragment(this, MusicHomeFragment.class));
        ArrayList<String> tabTexts = new ArrayList<>();
        tabTexts.add("本地音乐");
        tabTexts.add("歌词");
        BaseFragmentPagerAdapter<Fragment> pagerAdapter = new BaseFragmentPagerAdapter<>(getSupportFragmentManager(), fragments, tabTexts);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        if (getIntent().hasExtra("isNowPhoto")) {
            clickPhoto();
        }

        initPlayer();
    }

    private void initPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();

    }

    private void setPlayIcon() {
        try {
            if (mediaPlayer.isPlaying()) {
                iv_music_play.setImageResource(R.drawable.playbar_btn_pause);
            } else {
                iv_music_play.setImageResource(R.drawable.playbar_btn_play);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showNotification();
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
        iv_music_play.setOnClickListener(v -> {
            playAndPause();
        });
        iv_music_pre.setOnClickListener(v -> {
            playMusic(currentPosition - 1);
        });
        iv_music_next.setOnClickListener(v -> {
            playMusic(currentPosition + 1);
        });
    }

    public void clickPhoto() {
        Intent intent = new Intent(MainActivity.this, DakaActivity.class);
        intent.putExtra("isNowPhoto", true);
        startActivity(intent);
    }


    public void playMusic(int position) {
        if (position < 0) {
            currentPosition = 0;
        } else if (position >= musicList.size()) {
            currentPosition = 0;
        } else {
            this.currentPosition = position;
        }
        MusicModel musicModel = musicList.get(currentPosition);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicModel.data);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPlayIcon();
        tv_music_name.setText(musicModel.musicName);
        tv_music_author.setText(musicModel.artist);
        isPlayed = true;
    }

    public void playAndPause() {
        if (!isPlayed) {
            if (musicList.size() > 0) {
                playMusic(0);
            }
        } else {
            try {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setPlayIcon();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 789) {
            System.out.println("---<<<>>>---789");
        }
    }
}
