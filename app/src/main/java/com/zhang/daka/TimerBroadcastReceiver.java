package com.zhang.daka;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class TimerBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, TimerService.class));

        if ("testalarm0".equals(intent.getAction())) {
            System.out.println("上午闹钟");
        }

        if ("testalarm1".equals(intent.getAction())) {
            System.out.println("下午闹钟");
        }

    }
}
