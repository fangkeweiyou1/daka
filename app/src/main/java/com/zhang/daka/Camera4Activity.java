package com.zhang.daka;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.qcloud.tim.uikit.component.video.Camera5Activity;
import com.tencent.qcloud.tim.uikit.component.video.JCameraView;
import com.tencent.qcloud.tim.uikit.component.video.listener.ClickListener;
import com.tencent.qcloud.tim.uikit.component.video.listener.ErrorListener;
import com.tencent.qcloud.tim.uikit.component.video.listener.JCameraListener;
import com.tencent.qcloud.tim.uikit.component.video.util.DeviceUtil;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

/**
 * Created by zhangyuncai on 2019/10/14.
 */
public class Camera4Activity extends AppCompatActivity {
    private static final String TAG = Camera5Activity.class.getSimpleName();

    private JCameraView jCameraView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
