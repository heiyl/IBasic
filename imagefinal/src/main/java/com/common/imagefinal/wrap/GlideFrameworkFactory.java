package com.common.imagefinal.wrap;

public class GlideFrameworkFactory implements ImageFrameworkFactory {

    @Override
    public ImageLoaderProduct createImageLoader() {
        return new GlideLoaderProduct();
    }

    @Override
    public ImageConfigProduct createImageConfig() {
        return new GlideConfigProduct();
    }
}
