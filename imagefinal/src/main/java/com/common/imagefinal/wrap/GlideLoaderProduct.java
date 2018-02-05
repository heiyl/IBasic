package com.common.imagefinal.wrap;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.common.imagefinal.ImageManager;


public class GlideLoaderProduct implements ImageLoaderProduct {
    @Override
    public void display(String imageUri, ImageView imageView) {
        GlideWrapper.getDefalt()
                .getGlide()
                .load(imageUri)
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
    }

    @Override
    public void display(String imageUrl, ImageView imageView, ImageConfigProduct config) {
        GlideConfigProduct mConfig = (GlideConfigProduct) config.get();
        RequestOptions requestOptions;
        if(mConfig.shape == ImageManager.DISPLAY_SHAPE_CIRCLE){
            requestOptions = RequestOptions.circleCropTransform();
        }else{
            requestOptions = new RequestOptions();
        }
        GlideWrapper.getDefalt()
                .getGlide()
                .load(imageUrl)
                .apply(requestOptions.placeholder(mConfig.loadingRes).error(mConfig.failRes))
                .transition(DrawableTransitionOptions.withCrossFade()) // 动画渐变加载
                .into(imageView);
    }

    @Override
    public void cleanImageCache() {
        GlideWrapper.getDefalt().cleanCache();
    }
}
