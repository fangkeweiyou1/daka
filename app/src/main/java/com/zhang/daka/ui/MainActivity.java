package com.zhang.daka.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.zhang.daka.model.MenuModel;
import com.zhang.daka.mvp.BaseActivity;
import com.zhang.daka.ui.adapter.MenuAdapter;
import com.zhang.daka.ui.fragment.MusicHomeFragment;
import com.zhang.daka.ui.fragment.MusicListFragment;

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
    //音乐菜单
    private ImageView iv_music_menu;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void showNotification() {

        Intent dakaIntent = new Intent(this, MainActivity.class);
        dakaIntent.putExtra("isNowPhoto", true);
        PendingIntent dakaPendingIntent = PendingIntent.getActivity(this, 0, dakaIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(AppUtil.INSTANCE.getPackageName(), R.layout.view_notify);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_pre, null);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_play, null);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_next, null);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_daka, dakaPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.tv_notify_name, mainPendingIntent);
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
        iv_music_menu = findViewById(R.id.iv_music_menu);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 789) {
            System.out.println("---<<<>>>---789");
        }
    }
}
