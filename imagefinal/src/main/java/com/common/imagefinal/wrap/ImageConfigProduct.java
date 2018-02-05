package com.common.imagefinal.wrap;

public interface ImageConfigProduct {
    void setDefaulRes(int defaulRes);
    void setLoadingRes(int loadingRes);
    void setFailRes(int failRes);
    void setsupportMemoryCache(boolean flag);
    void setsupportDiskCache(boolean flag);
    void setFadeIn(int duration);
    void setShape(int shape);
    Object get();
}
