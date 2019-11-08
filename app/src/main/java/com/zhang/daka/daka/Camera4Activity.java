package com.zhang.daka.daka;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.qcloud.tim.uikit.component.video.Camera5Activity;
import com.tencent.qcloud.tim.uikit.component.video.CameraInterface;
import com.tencent.qcloud.tim.uikit.component.video.JCameraView;
import com.tencent.qcloud.tim.uikit.component.video.listener.ClickListener;
import com.tencent.qcloud.tim.uikit.component.video.listener.ErrorListener;
import com.tencent.qcloud.tim.uikit.component.video.listener.JCameraListener;
import com.tencent.qcloud.tim.uikit.component.video.util.DeviceUtil;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wushiyi.mvp.utils.JsonUtil;
import com.wushiyi.util.ToastUtilKt;
import com.zhang.daka.R;
import com.zhang.daka.model.OcrModel;
import com.zhang.daka.model.WordsResultBean;
import com.zhang.daka.utils.WFileUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static timber.log.Timber.d;

/**
 * Created by zhangyuncai on 2019/10/14.
 */
public class Camera4Activity extends AppCompatActivity {
    private static final String TAG = Camera5Activity.class.getSimpleName();

    private JCameraView jCameraView;
    boolean isDataSuccess = false;//是否打卡成功
    Activity context;
    private int snapInx = 0;//拍照照片自增角标,循环使用这个角标
    private Disposable subscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_camera4);
        jCameraView = findViewById(R.id.jcameraview);
        jCameraView.setFeatures(0x101);//只拍照
        jCameraView.setTip("点击拍照");
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);

        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
            }

            @Override
            public void AudioPermissionError() {
                ToastUtil.toastShortMessage("给点录音权限可以?");
            }
        });
        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame, long duration) {
            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Camera4Activity.this.finish();
            }
        });
        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                ToastUtil.toastShortMessage("Right");
            }
        });
        //jCameraView.setVisibility(View.GONE);
        TUIKitLog.i(TAG, DeviceUtil.getDeviceModel());

        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long l) throws Exception {
                        if (isDataSuccess) {
                            return;
                        }
                        Bitmap bitmap = CameraInterface.getInstance().getPreviewBitmap();
                        if (bitmap != null) {
                            imageCaptured(bitmap);
                        }
                    }
                });


    }

    void autoBitmap() {
    }

    public void imageCaptured(final Bitmap bitmap) {
        if (isDataSuccess) {
            return;
        }
        if (bitmap == null) {
            return;
        }

        final String imagePath = WFileUtil.getCacheImagePath(this, "snap" + (snapInx++));
        try {
            final File cacheFile = new File(imagePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(cacheFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

            if (isDataSuccess) {
                return;
            }
            RecognizeService.recAccurateBasic(this, imagePath,
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            if (isDataSuccess) {
                                return;
                            }
                            Log.d(TAG, "result:" + result);
                            try {
                                OcrModel ocrModel = JsonUtil.INSTANCE.jsonToAny(result, OcrModel.class);
                                isDakaSuccess(ocrModel);
                                if (isDataSuccess) {//如果打卡成功保存图片
                                    Calendar calendar = Calendar.getInstance();
                                    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                                    String imagePath2 = "";
                                    if (hourOfDay < 12) {//上午图片
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-");
                                        String date = simpleDateFormat.format(calendar.getTime());
                                        imagePath2 = WFileUtil.getCacheImagePath(context, date + "am");
                                    } else {//下午图片
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-");
                                        String date = simpleDateFormat.format(calendar.getTime());
                                        imagePath2 = WFileUtil.getCacheImagePath(context, date + "pm");
                                    }
                                    FileInputStream fileInputStream = new FileInputStream(imagePath);
                                    byte[] output = new byte[1024];
                                    int read = 0;
                                    FileOutputStream outputStream = new FileOutputStream(imagePath2);
                                    while ((read = fileInputStream.read(output)) != -1) {
                                        outputStream.write(output, 0, read);
                                    }
                                    fileInputStream.close();
                                    outputStream.close();
                                    ToastUtilKt.showToast("打卡成功");
                                    //播放铃声
                                    initPlayer();
                                    finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 播放打卡成功铃声
     */
    void initPlayer() {
        try {
            autoSetAudioVolumn();
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
                    resetAudioVolumn();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //当前音量大小
    int currentVolume = 0;

    /**
     * 设置音量
     */
    private void autoSetAudioVolumn() {
        AudioManager mAudioManager = (AudioManager) (context.getSystemService(Context.AUDIO_SERVICE));
        //获取手机最大音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //设置最大音量
        int setVolume = (int) (maxVolume * 1f);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
    }

    /**
     * 重置音量
     */
    private void resetAudioVolumn() {
        if (currentVolume > 0) {
            AudioManager mAudioManager = (AudioManager) (context.getSystemService(Context.AUDIO_SERVICE));
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        }
    }

    /**
     * 是否打卡成功
     *
     * @return
     */
    void isDakaSuccess(OcrModel ocrModel) {
        if (ocrModel.words_result == null || ocrModel.words_result.size() == 0) {
            return;
        }
        for (WordsResultBean wordsResultBean : ocrModel.words_result) {
            String words = wordsResultBean.words;
            d("----------->>>>>>>>-----------words:" + words);
            if (!TextUtils.isEmpty(words)) {
                if (words.contains("张运才")) {
                    isDataSuccess = true;
                } else if (words.contains("张运败")) {
                    isDataSuccess = true;
                } else if (words.contains("张运")) {
                    isDataSuccess = true;
                } else if (words.contains("已签到")) {
                    isDataSuccess = true;
                } else if (words.contains("it部")) {
                    isDataSuccess = true;
                } else if (words.contains("00000019")) {
                    isDataSuccess = true;
                } else if (words.contains("000019")) {
                    isDataSuccess = true;
                } else if (words.contains("0000")) {
                    isDataSuccess = true;
                }
            }
            if (isDataSuccess) {
                break;
            }
        }
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
