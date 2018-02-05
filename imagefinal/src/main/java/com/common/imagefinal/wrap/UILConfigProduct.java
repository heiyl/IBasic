package com.common.imagefinal.wrap;

import com.common.imagefinal.ImageManager;


public class UILConfigProduct implements ImageConfigProduct {
    //default true
    public boolean isSupportMemoryCache;
    public boolean isSupportDiskCache;
    public int defaulRes;
    public int loadingRes;
    public int failRes;
    public int duration;
    public int shape;

    public UILConfigProduct() {
        this.isSupportMemoryCache = true;
        this.isSupportDiskCache = true;
        this.loadingRes = 0;
        this.failRes = 0;
        this.duration = 0;
    }

    @Override
    public void setDefaulRes(int defaulRes) {
        this.defaulRes = defaulRes;
    }

    @Override
    public void setLoadingRes(int loadingRes) {
        this.loadingRes = loadingRes;
    }

    @Override
    public void setFailRes(int failRes) {
        this.failRes = failRes;
    }

    @Override
    public void setsupportMemoryCache(boolean flag) {
        this.isSupportMemoryCache = flag;
    }

    @Override
    public void setsupportDiskCache(boolean flag) {
        this.isSupportDiskCache = flag;
    }

    @Override
    public void setFadeIn(int duration) {
        this.duration = duration;
    }

    @Override
    public void setShape(int shape) {
        this.shape = shape;
    }

    @Override
    public Object get() {
        ImageLoaderWrapper.DisplayConfig config;
        if(this.shape == ImageManager.DISPLAY_SHAPE_CIRCLE){
            config = new ImageLoaderWrapper.DisplayConfig.Builder().buildCircular();
        }else{
            config = new ImageLoaderWrapper.DisplayConfig.Builder().build();
        }
        config.loadFailImageRes = this.loadingRes;
        config.loadFailImageRes = this.failRes;
        config.stubImageRes = this.defaulRes;
        config.supportMemoryCache = this.isSupportMemoryCache;
        config.supportDiskCache = this.isSupportDiskCache;
        config.shape = this.shape;
        return config;
    }
}
