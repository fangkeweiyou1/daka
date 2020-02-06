package com.zhang.daka.config;

import android.app.Application;

import com.wushiyi.mvp.MvpInit;

import cn.bmob.v3.Bmob;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class DakaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MvpInit.INSTANCE.init(this);
        Bmob.initialize(this, Constans.ApplicationId);
    }

}
