package com.zhang.daka.kaoshi.danci.activity;

import android.widget.TextView;

import com.wushiyi.mvp.base.SimpleAppCompatActivity;
import com.zhang.daka.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhangyuncai on 2019/11/11.
 */
public class DanciTxtActivity extends SimpleAppCompatActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_dancitxt;
    }

    @Override
    public void initView() {
        TextView tv_dancitxt_content = findViewById(R.id.tv_dancitxt_content);


        new Thread() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = getAssets().open("danci.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String lineContent = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((lineContent = reader.readLine()) != null) {
                        stringBuilder.append(lineContent);
                        stringBuilder.append("\t\n");
                    }
                    inputStream.close();

                    runOnUiThread(() -> {
                        tv_dancitxt_content.setText(stringBuilder);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }
}
