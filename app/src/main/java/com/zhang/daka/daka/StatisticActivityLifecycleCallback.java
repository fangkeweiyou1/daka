package com.zhang.daka.daka;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.tencent.imsdk.TIMBackgroundParam;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;

import java.util.List;

import static com.wushiyi.mvp.MvpExtendsKt.dddBug;
import static java.lang.String.format;
import static timber.log.Timber.d;

/**
 * Created by zhangyuncai on 2019/7/12.
 */
public class StatisticActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {
    private int foregroundActivities = 0;
    private boolean isChangingConfiguration;
    public static Activity currentActivity;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        dddBug("onActivityStarted:" + activity.getClass().getSimpleName());
        foregroundActivities++;
        if (foregroundActivities == 1 && !isChangingConfiguration) {
            // 应用切到前台
            d(format("----------->>>>>>>>-----------" + "application enter foreground"));
            TIMManager.getInstance().doForeground(new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    d(format("----------->>>>>>>>-----------" + "doForeground err = " + code + ", desc = " + desc));
                }

                @Override
                public void onSuccess() {
                    d(format("----------->>>>>>>>-----------" + "doForeground success"));
                }
            });
        }
        isChangingConfiguration = false;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        //当前显示的界面
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        foregroundActivities--;
        if (foregroundActivities == 0) {
            // 应用切到后台
            d(format("----------->>>>>>>>-----------" + "application enter background"));
            int unReadCount = 0;
            List<TIMConversation> conversationList = TIMManagerExt.getInstance().getConversationList();
            for (TIMConversation timConversation : conversationList) {
                TIMConversationExt timConversationExt = new TIMConversationExt(timConversation);
                unReadCount += timConversationExt.getUnreadMessageNum();
            }
            TIMBackgroundParam param = new TIMBackgroundParam();
            param.setC2cUnread(unReadCount);
            TIMManager.getInstance().doBackground(param, new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    d(format("----------->>>>>>>>-----------" + "doBackground err = " + code + ", desc = " + desc));
                }

                @Override
                public void onSuccess() {
                    d(format("----------->>>>>>>>-----------" + "doBackground success"));
                }
            });
        }
        isChangingConfiguration = activity.isChangingConfigurations();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        dddBug("onActivityDestroyed:" + activity.getClass().getSimpleName());
    }
}
