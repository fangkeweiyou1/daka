package com.zhang.daka.daka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.zhang.daka.R;
import com.zhang.daka.base.BaseDialog;

import java.io.File;

/**
 * Created by zhangyuncai on 2019/10/14.
 */
public class PreviewImageDialog extends BaseDialog {
    private final File imageFile;

    public PreviewImageDialog(@NonNull Context context, File imageFile) {
        super(context);
        this.imageFile = imageFile;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_previewimage;
    }

    @Override
    protected void initView() {
        PhotoView iv_preview = findViewById(R.id.iv_preview);
        iv_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Glide.with(getContext())
                .load(imageFile)
                .skipMemoryCache(true)
                .into(iv_preview);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getWidthStyle() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getHeightStyle() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

}
