package com.zhang.daka.config;

import android.app.Application;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.wushiyi.mvp.MvpInit;
import com.zhang.daka.daka.StatisticActivityLifecycleCallback;

import static timber.log.Timber.d;

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
            TUIKit.init(this, 1,new ConfigHelper().getConfigs());
            registerActivityLifecycleCallbacks(new StatisticActivityLifecycleCallback());
        }

        //百度OCR初始化
        initAccessTokenWithAkSk();
    }


    /**
     * 用明文ak，sk初始化
     */
    private void initAccessTokenWithAkSk() {

        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {

                String token = result.getAccessToken();
//                String imagePath = WFileUtil.getCacheImagePath(context, "aaa");
//                try {
//                    RecognizeService.recAccurateBasic(context, imagePath,
//                            new RecognizeService.ServiceListener() {
//                                @Override
//                                public void onResult(String result) {
//                                    Log.d(TAG,"result:"+result);
//                                    try {
//                                        OcrModel ocrModel = JsonUtil.INSTANCE.jsonToAny(result, OcrModel.class);
//                                        for (WordsResultBean wordsResultBean : ocrModel.words_result) {
//                                            Log.d(TAG, "words:" + wordsResultBean.words);
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                d("----------->>>>>>>>-----------onError");
            }
        }, getApplicationContext(), "53pTDnpGGcWtmx9iqKfjj45n", "jfycO2ayG0rNzPubGUPC8Nme3ohltK6v");
    }
}
