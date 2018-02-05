package com.common.imagefinal.wrap;

import android.widget.ImageView;

public interface ImageLoaderProduct {
    void display(String imageUri, ImageView imageView);
    void display(String imageUrl, ImageView imageView, ImageConfigProduct config);
    void cleanImageCache();
}
