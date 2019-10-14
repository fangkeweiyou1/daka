package com.zhang.daka;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by zhangyuncai on 2019/1/16.
 * 查看文件是否存在不需要权限,但是读取,写入必须要权限
 */
public class WFileUtil {

    public static String getCacheImagePath(Context context, String imageName) {
        String imageFolder = createCacheImageFolder(context);
        return imageFolder + "/" + imageName + ".png";
    }

    /**
     * 创建缓存中的打卡文件夹
     *
     * @return
     */
    public static String createCacheImageFolder(Context context) {
        String dir = getDiskPath(context);
        String path = dir + "/aa_daka_img";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }


    /**
     * 获取缓存路径
     */
    public static String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    /**
     * 获取内存卡地址
     *
     * @param context
     * @return
     */
    public static String getDiskPath(Context context) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED .equals(state) ) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return context.getFilesDir().getAbsolutePath();
        }
    }

}
