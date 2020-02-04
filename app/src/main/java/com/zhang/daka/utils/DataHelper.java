package com.zhang.daka.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.zhang.daka.R;

import java.io.IOException;
import java.util.Random;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class DataHelper {
    private static final String TAG = "DataHelper";


    /**
     * 播放打卡成功铃声
     */
    static void initPlayer(Context context) {
        try {
            AssetFileDescriptor openFd = context.getAssets().openFd("message.wav");
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

    public static int getMonthOfDay(int year, int month) {
        int day = 0;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            day = 29;
        } else {
            day = 28;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return day;

        }

        return 0;
    }

    public static String getDayText(int position) {
        switch (position) {
            case 0:
                return "01";
            case 1:
                return "02";
            case 2:
                return "03";
            case 3:
                return "04";
            case 4:
                return "05";
            case 5:
                return "06";
            case 6:
                return "07";
            case 7:
                return "08";
            case 8:
                return "09";
            case 9:
                return "10";
            case 10:
                return "11";
            case 11:
                return "12";
            case 12:
                return "13";
            case 13:
                return "14";
            case 14:
                return "15";
            case 15:
                return "16";
            case 16:
                return "17";
            case 17:
                return "18";
            case 18:
                return "19";
            case 19:
                return "20";
            case 20:
                return "21";
            case 21:
                return "22";
            case 22:
                return "23";
            case 23:
                return "24";
            case 24:
                return "25";
            case 25:
                return "26";
            case 26:
                return "27";
            case 27:
                return "28";
            case 28:
                return "29";
            case 29:
                return "30";
            case 30:
                return "31";
        }
        return "01";
    }

    public static int getMeinvImage() {
        Random random = new Random();
        int i = random.nextInt(15);
        switch (i) {
            case 0:
                return R.drawable.linyuner_1;
            case 1:
                return R.drawable.linyuner_2;
            case 2:
                return R.drawable.linyuner_3;
            case 3:
                return R.drawable.linyuner_4;
            case 4:
                return R.drawable.linyuner_5;
            case 5:
                return R.drawable.linyuner_6;
            case 6:
                return R.drawable.linyuner_7;
            case 7:
                return R.drawable.linyuner_8;
            case 8:
                return R.drawable.linyuner_9;
            case 9:
                return R.drawable.linyuner_10;
            case 10:
                return R.drawable.linyuner_11;
            case 11:
                return R.drawable.linyuner_12;
            case 12:
                return R.drawable.linyuner_13;
            case 13:
                return R.drawable.linyuner_14;
            case 14:
                return R.drawable.linyuner_15;
        }
        return R.drawable.linyuner_1;
    }
}
