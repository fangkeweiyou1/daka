package com.zhang.daka;

import android.app.Application;

import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.wushiyi.mvp.MvpInit;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class DakaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MvpInit.INSTANCE.init(this);

        //判断是否是在主线程
        if (SessionWrapper.isMainProcess(this)) {
            /**
             * TUIKit的初始化函数
             *
             * @param context  应用的上下文，一般为对应应用的ApplicationContext
             * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
             * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
             */
            TUIKit.init(this, 1,new  ConfigHelper().getConfigs());
            registerActivityLifecycleCallbacks(new StatisticActivityLifecycleCallback());
        }
    }
}
