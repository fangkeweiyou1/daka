package com.zhang.daka.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhang.daka.ui.MainActivity;

public class MusicBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().contains("next")) {
            MainActivity mainActivity = MainActivity.mainActivity;
            mainActivity.playMusic(mainActivity.currentPosition + 1);
        } else if (intent.getAction().contains("pre")) {
            MainActivity mainActivity = MainActivity.mainActivity;
            mainActivity.playMusic(mainActivity.currentPosition - 1);
        }else if (intent.getAction().contains("paly")) {
            MainActivity mainActivity = MainActivity.mainActivity;
            mainActivity.playAndPause();
        }
    }
}
