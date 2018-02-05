package com.common.imagefinal.GlideConfig;

import android.content.Context;
import android.os.Environment;

import java.io.File;



/**
 * 2016/9/19.
 * yulong
 */
public class GlideUtils {

    public static final String BASIC_DIR_NAME = File.separator + "IMAGEFINAL";
    public static final String IMAGELOADER_CATCH_DIR_NAME = "IMAGEFINAL_CATCH";

    /**
     * 获取图片临时目录(网络图片缓存)
     *
     * @return
     */
    public static File getImageTmpDir(Context context) {
        File dir = new File(getTmpDir(context), "image_cache");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取临时目录
     *
     * @return
     */
    public static File getTmpDir(Context context) {
        return getTmpDir(context, false);
    }

    /**
     * 获取临时目录
     *
     * @param isSdcard 是否只取sd卡上的目录
     * @return
     */
    public static File getTmpDir(Context context, boolean isSdcard) {
        File tmpDir = null;
        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (isSdcard && !sdCardExist) {
            if (!sdCardExist) {
                return null;
            }
        }

        if (sdCardExist || isSdcard) {
            tmpDir = new File(Environment.getExternalStorageDirectory() + BASIC_DIR_NAME,
                    getTmpDirName());
        } else {
            tmpDir = new File(context.getCacheDir() + BASIC_DIR_NAME, getTmpDirName());
        }

        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        return tmpDir;
    }

    /**
     * 获取缓存目录名
     *
     * @return
     */
    public static String getTmpDirName() {
        return IMAGELOADER_CATCH_DIR_NAME;
    }
}
