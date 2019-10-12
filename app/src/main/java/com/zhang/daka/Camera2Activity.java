package com.zhang.daka;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.wushiyi.mvp.utils.JsonUtil;
import com.wushiyi.util.ToastUtilKt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import timber.log.Timber;

import static java.lang.System.out;
import static timber.log.Timber.d;

/**
 * Created by zhangyuncai on 2019/4/6.
 */
public class Camera2Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Camera2Activity";
    CameraKitView cameraKitView;
    ImageView iv_snap_info;//提示
    LinearLayout ll_snap_picture;//上传相册
    ImageView iv_snap_ok;//拍照
    ImageView iv_snap_change;//摄像头切换
    View view_snap_cover;//封面图
    boolean isDataSuccess = false;//是否打卡成功
    Context context;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_camera);
        view_snap_cover = findViewById(R.id.view_snap_cover);
        iv_snap_info = (ImageView) findViewById(R.id.iv_snap_info);
        ll_snap_picture = (LinearLayout) findViewById(R.id.ll_snap_picture);
        iv_snap_ok = (ImageView) findViewById(R.id.iv_snap_ok);
        view_snap_cover = findViewById(R.id.view_snap_cover);
        iv_snap_change = findViewById(R.id.iv_snap_change);
        cameraKitView = (CameraKitView) findViewById(R.id.camera_snap);
        iv_snap_info.setOnClickListener(this);


        initView();

        initData();

        initEvent();

        //开始自动拍照
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoCamera();
            }
        }, 1000);
    }

    /**
     * 自动拍照,每隔500毫秒
     */
    void autoCamera() {
        d("----------->>>>>>>>-----------isDataSuccess:" + isDataSuccess);
        if (!isDataSuccess) {
            cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                @Override
                public void onImage(CameraKitView cameraKitView, final byte[] jpeg) {
                    imageCaptured(jpeg);
                    autoCamera();
                }
            });
        }
    }

    protected void initView() {
        cameraKitView.setPreviewEffect(CameraKit.PREVIEW_EFFECT_NEGATIVE);//预览效果
    }


    protected void initData() {

    }

    protected void initEvent() {

        iv_snap_change.setOnTouchListener(onTouchFacing);
        iv_snap_ok.setOnTouchListener(onTouchCaptureImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_snap_info:
                break;
            case R.id.ll_snap_picture: {
            }
            break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }


    //摄像头切换
    private View.OnTouchListener onTouchFacing = new View.OnTouchListener() {
        @Override
        public boolean onTouch(final View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP: {
                    view_snap_cover.setAlpha(0);
                    view_snap_cover.setVisibility(View.VISIBLE);
                    view_snap_cover.animate()
                            .alpha(1)
                            .setStartDelay(0)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    int facing = cameraKitView.getFacing();
                                    if (facing == CameraKit.FACING_FRONT) {//前置摄像头
                                        cameraKitView.setFacing(CameraKit.FACING_BACK);
                                    } else {
                                        cameraKitView.setFacing(CameraKit.FACING_FRONT);
                                    }
//                                    cameraKitView.toggleFacing();

                                    view_snap_cover.animate()
                                            .alpha(0)
                                            .setStartDelay(200)
                                            .setDuration(300)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    view_snap_cover.setVisibility(View.GONE);
                                                }
                                            })
                                            .start();
                                }
                            })
                            .start();
                    break;
                }
            }
            return true;
        }
    };

    //拍照
    private View.OnTouchListener onTouchCaptureImage = new View.OnTouchListener() {
        @Override
        public boolean onTouch(final View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    handleViewTouchFeedback(view, motionEvent);
//                    cameraKitView.onPause();
                    cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                        @Override
                        public void onImage(CameraKitView cameraKitView, final byte[] jpeg) {
                            imageCaptured(jpeg);
                        }
                    });
                    break;
                }

                case MotionEvent.ACTION_UP: {
                    handleViewTouchFeedback(view, motionEvent);
                    break;
                }
            }
            return true;
        }
    };

    private int snapInx = 0;//拍照照片自增角标,循环使用这个角标

    public void imageCaptured(final byte[] jpeg) {
        if (isDataSuccess) {
            return;
        }

        String imagePath = WFileUtil.getCacheImagePath(this, "snap" + (snapInx++));
        try {
            FileOutputStream outputStream = new FileOutputStream(imagePath);
            outputStream.write(jpeg);
            outputStream.close();
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
                                    FileOutputStream outputStream = new FileOutputStream(imagePath2);
                                    outputStream.write(jpeg);
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
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 是否打卡成功
     *
     * @return
     */
    void isDakaSuccess(OcrModel ocrModel) {
        for (WordsResultBean wordsResultBean : ocrModel.words_result) {
            String words = wordsResultBean.words;
            d("----------->>>>>>>>-----------words:" + words);
            if (!TextUtils.isEmpty(words)) {
                if (words.contains("张运才")) {
                    isDataSuccess = true;
                } else if (words.contains("张运败")) {
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

    boolean handleViewTouchFeedback(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                touchDownAnimation(view);
                return true;
            }

            case MotionEvent.ACTION_UP: {
                touchUpAnimation(view);
                return true;
            }

            default: {
                return true;
            }
        }
    }

    void touchDownAnimation(View view) {
        view.animate()
                .scaleX(0.88f)
                .scaleY(0.88f)
                .setDuration(300)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    void touchUpAnimation(View view) {
        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

    }

}
