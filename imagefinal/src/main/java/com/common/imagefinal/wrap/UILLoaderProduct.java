package com.common.imagefinal.wrap;

import android.widget.ImageView;

public class UILLoaderProduct implements ImageLoaderProduct {

    @Override
    public void display(String imageUri, ImageView imageView) {
        ImageLoaderWrapper.getDefault().displayImage(imageUri, imageView);
    }

    @Override
    public void display(String imageUrl, ImageView imageView, ImageConfigProduct config) {
        ImageLoaderWrapper.DisplayConfig mConfig = (ImageLoaderWrapper.DisplayConfig) config.get();
        ImageLoaderWrapper.getDefault().displayImage(imageUrl, imageView, mConfig);
    }

    @Override
    public void cleanImageCache() {
        ImageLoaderWrapper.getDefault().clearDefaultLoaderMemoryCache();
        ImageLoaderWrapper.getDefault().clearDefaultLoaderDiscCache();

    }
}
