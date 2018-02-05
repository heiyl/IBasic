package com.common.imagefinal.wrap;

public class UILFraworkFactory implements ImageFrameworkFactory {
    @Override
    public ImageLoaderProduct createImageLoader() {
        return new UILLoaderProduct();
    }

    @Override
    public ImageConfigProduct createImageConfig(){
        return new UILConfigProduct();
    }
}
