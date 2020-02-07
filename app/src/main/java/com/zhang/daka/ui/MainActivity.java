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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wushiyi.mvp.MvpExtendsKt;
import com.wushiyi.mvp.base.BaseFragmentPagerAdapter;
import com.wushiyi.util.AppUtil;
import com.zhang.daka.R;
import com.zhang.daka.config.Constans;
import com.zhang.daka.event.MusicBroadCast;
import com.zhang.daka.kugou.KuGouApiService;
import com.zhang.daka.model.MenuModel;
import com.zhang.daka.model.MusicCateModel;
import com.zhang.daka.model.MusicModel;
import com.zhang.daka.mvp.BaseActivity;
import com.zhang.daka.net.NetworkModule;
import com.zhang.daka.popup.ListPopupWindowHelper;
import com.zhang.daka.popup.PopupListAdapter;
import com.zhang.daka.ui.adapter.MenuAdapter;
import com.zhang.daka.ui.dialog.MusicCateListDialog;
import com.zhang.daka.ui.fragment.MusicListFragment;
import com.zhang.daka.ui.fragment.MusicLrcFragment;
import com.zhang.daka.utils.WTimeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 音乐播放器主界面
 */
public class MainActivity extends BaseActivity {
    public static MainActivity mainActivity;
    public static final String channelId = "daka_default_channel";

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
    private SeekBar seekbar_main;
    private TextView tv_main_starttime;
    private TextView tv_main_endtime;
    private View view_main_tabline;
    private MediaPlayer mediaPlayer;
    public final ArrayList<MusicModel> musicList = new ArrayList<>();
    public int currentPosition = 0;
    public boolean isPlayed = false;
    private MusicListFragment musicListFragment;
    private Disposable subscribe;
    private KuGouApiService kuGouApiService;
    private MusicLrcFragment musicLrcFragment;

    /**
     * popup
     */
    public final ArrayList<MusicCateModel> musicCateModels = new ArrayList<>();
    private PopupListAdapter<MusicCateModel> musicCateAdapter;
    private ListPopupWindow musicCateWindow;

    @Override
    public int getLayoutId() {
        mainActivity = this;
        kuGouApiService = NetworkModule.getRetrofit(Constans.BASE_KUGOU_URL).create(KuGouApiService.class);
//        PersonModel personModel = new PersonModel();
//        personModel.setName("china2");
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("1");
//        strings.add("2");
//        strings.add("3");
//        personModel.setTexts(strings);
//        personModel.saveObservable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s->{
//
//                },throwable -> {});
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
        seekbar_main = findViewById(R.id.seekbar_main);
        tv_main_starttime = findViewById(R.id.tv_main_starttime);
        tv_main_endtime = findViewById(R.id.tv_main_endtime);
        view_main_tabline = findViewById(R.id.view_main_tabline);

        menuModels.add(new MenuModel(R.drawable.ic_daka, "打卡"));
        menuModels.add(new MenuModel(R.drawable.ic_daka, "新增音乐分类"));
        View headView = View.inflate(this, R.layout.view_menu_head, null);
        menuAdapter = new MenuAdapter(menuModels);
        menuAdapter.addHeaderView(headView);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerView.setAdapter(menuAdapter);

        ArrayList<Fragment> fragments = new ArrayList<>();
        musicListFragment = MvpExtendsKt.sNewStanceFragment(this, MusicListFragment.class);
        fragments.add(musicListFragment);
        musicLrcFragment = MvpExtendsKt.sNewStanceFragment(this, MusicLrcFragment.class);
        fragments.add(musicLrcFragment);
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
        showNotification();


        updateMusicCateModels();
    }

    public void loadMusicCates() {
        BmobQuery<MusicCateModel> query = new BmobQuery<>();
        query.findObjectsObservable(MusicCateModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    musicCateModels.clear();
                    musicCateModels.addAll(s);
                    updateMusicCateModels();
                    if (musicCateListDialog != null && musicCateListDialog.isShowing()) {
                        musicCateListDialog.notifyList();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    private void updateMusicCateModels() {
        MusicCateModel musicCateModel = new MusicCateModel();
        musicCateModel.setCateName("本地音乐");
        musicCateModel.setCateId(-1);
        musicCateModels.add(0, musicCateModel);
        initMusicCateWindow();
    }

    private void initPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playMusic(currentPosition + 1);
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });
    }

    private void setPlayIcon() {
        try {
            if (mediaPlayer.isPlaying()) {
                iv_music_play.setImageResource(R.drawable.playbar_btn_pause);
                startTimer();
            } else {
                iv_music_play.setImageResource(R.drawable.playbar_btn_play);
                endTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showNotification();
        musicListFragment.notifyCurrent();
    }

    private void startTimer() {
        if (mediaPlayer.isPlaying()) {
            subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        seekbar_main.setProgress(mediaPlayer.getCurrentPosition());
                        musicLrcFragment.setCurrentTimeMillis(mediaPlayer.getCurrentPosition());
                        String startTime = WTimeUtils.getDateFormatter(new Date(mediaPlayer.getCurrentPosition()), WTimeUtils.text_mm_ss);
                        tv_main_starttime.setText(startTime);
                        Timber.d(String.format("--->>>>>>>>---currentPosition:" + mediaPlayer.getCurrentPosition()));
                        if (!mediaPlayer.isPlaying()) {
                            endTime();
                        }
                    }, throwable -> {
                    });
        } else {
            endTime();
        }
    }

    private void endTime() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }

    private void initMusicCateWindow() {
        musicCateAdapter = new PopupListAdapter(mActivity, R.layout.item_listpopupwindow_adapter, musicCateModels);
        musicCateWindow = ListPopupWindowHelper.getListPopupWindow(mActivity, musicCateAdapter, view_main_tabline, ListPopupWindow.WRAP_CONTENT);
        musicCateWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                musicCateWindow.dismiss();
            }
        });
    }


    private MusicCateListDialog musicCateListDialog;

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
                    case "新增音乐分类":
                        musicCateListDialog = new MusicCateListDialog(MainActivity.this);
                        musicCateListDialog.show();
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
        iv_music_thum.setOnClickListener(v -> {
            musicListFragment.notifyCurrent();
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ListPopupWindowHelper.showPopupWindow(musicCateWindow);
                }
            }
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
        tv_main_starttime.setText("00:00");
        String endTime = WTimeUtils.getDateFormatter(new Date(mediaPlayer.getDuration()), WTimeUtils.text_mm_ss);
        tv_main_endtime.setText(endTime);
        isPlayed = true;
        seekbar_main.setMax(musicModel.duration);
        seekbar_main.setProgress(0);
        seekbar_main.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        musicLrcFragment.searchLyric();

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
        loadMusicCates();
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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        endTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        endTime();
    }
}
