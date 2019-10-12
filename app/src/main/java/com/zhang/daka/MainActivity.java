package com.zhang.daka;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.wushiyi.mvp.utils.JsonUtil;
import com.wushiyi.util.ToastUtilKt;

import java.io.FileOutputStream;
import java.util.Calendar;

import timber.log.Timber;

import static timber.log.Timber.d;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";

    private RecyclerView mRecyclerView;
    private MainAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context=this;

        //申请权限
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camera < 0) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 99);
        }

        mRecyclerView = findViewById(R.id.rv_main);
        adapter = new MainAdapter();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);

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

    @Override
    protected void onResume() {
        super.onResume();
        int position = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
        if (position >= 0 && position < adapter.getItemCount()) {
            mRecyclerView.scrollToPosition(position);
            adapter.notifyItemChanged(position);
        }
    }
}
