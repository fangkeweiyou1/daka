package com.zhang.daka.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wushiyi.util.ToastUtilKt;

public class MediaButtonIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtilKt.showToast("action:"+intent.getAction());
    }
}
