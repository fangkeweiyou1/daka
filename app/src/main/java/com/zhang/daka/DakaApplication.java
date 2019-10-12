package com.zhang.daka;

import android.app.Application;

import com.wushiyi.mvp.MvpInit;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class DakaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MvpInit.INSTANCE.init(this);
    }
}
