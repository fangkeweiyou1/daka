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
        return imageFolder + "/" + imageName+".png";
    }

    /**
     * 创建缓存中的打卡文件夹
     *
     * @return
     */
    public static String createCacheImageFolder(Context context) {
        String dir = getDiskCachePath(context);
        String path = dir + "/dakaimage";
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

}
