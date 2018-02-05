package com.common.imagefinal.wrap;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class GlideWrapper {
    private static GlideWrapper sDefaultInstance;
    private Context mContext;

    public GlideWrapper(Context context) {
        mContext = context;
    }

    public static GlideWrapper init(Context context) {
        if (sDefaultInstance == null) {
            sDefaultInstance = new GlideWrapper(context);
        }
        return sDefaultInstance;
    }


    public static GlideWrapper getDefalt() {
        if (sDefaultInstance == null) {
            throw new RuntimeException(
                    "Must be call init(Context) befor!");
        }
        return sDefaultInstance;
    }


    public RequestManager getGlide(){
        return Glide.with(mContext);
    }

    public void cleanCache(){
        // crash: method on a background thread
        // you should put it on threadpool
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(mContext).clearMemory();
                Glide.get(mContext).clearDiskCache();
            }
        });

    }

    /**
     * 停止所有下载图片的任务
     */
    public void pause() {
        Glide.with(mContext).pauseRequests();
    }

    /**
     * 恢复被暂停的任务
     */
    public void resume() {
        Glide.with(mContext).resumeRequests();
    }

}
