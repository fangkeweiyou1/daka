package com.zhang.daka.daka;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhang.daka.R;

import java.io.IOException;

import static timber.log.Timber.d;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class MusicActivity extends AppCompatActivity {
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        music();
    }

    void music() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                d("----------->>>>>>>>-----------哈哈");
                music();
                initPlayer();
            }
        }, 2000);
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
                    mp.setLooping(false);
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
