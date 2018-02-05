package com.common.imagefinal.wrap;

import android.content.Context;

import java.io.File;

public class ImageLoaderUtils {
    public static void init(Context context, File file, boolean debug) {
        ImageLoaderWrapper.initDefault(context, file,
                debug);
        GlideWrapper.init(context);
    }

    public static ImageFrameworkFactory getFramework(int frameworkType) {
        if (frameworkType == 1) {
            return new GlideFrameworkFactory();
        }else {
            return new UILFraworkFactory();
        }
    }

}
