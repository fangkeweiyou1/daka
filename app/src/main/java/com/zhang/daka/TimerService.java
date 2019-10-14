package com.zhang.daka;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import static timber.log.Timber.d;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class TimerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        music();
    }

    void music() {
        initPlayer();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 播放打卡成功铃声
     */
    void initPlayer() {
        try {
            AssetFileDescriptor openFd = getAssets().openFd("message.wav");
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(openFd);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
