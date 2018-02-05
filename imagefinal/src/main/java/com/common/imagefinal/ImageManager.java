
package com.common.imagefinal;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.common.imagefinal.wrap.GlideWrapper;
import com.common.imagefinal.wrap.ImageConfigProduct;
import com.common.imagefinal.wrap.ImageLoaderUtils;
import com.common.imagefinal.wrap.ImageLoaderWrapper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;


public class ImageManager {
    //Glidei加载图片模式
    public static final int GLIDE_TYPE = 1;
    //ImageLoader加载图片模式
    public static final int IMAGELOADER_TYPE = 2;

    /**
     * 图片加载形状：正常模式,默认都是正常模式
     */
    public static final int DISPLAY_SHAPE_NORMAL = 0;
    /**
     * 图片加载形状：圆形图片模式
     */
    public static final int DISPLAY_SHAPE_CIRCLE = 1;

    //默认使用Glidei加载图片
    private int loadFrameworkType = GLIDE_TYPE;
    private static ImageManager instance = new ImageManager();

    private ImageManager() {
    }

    public static ImageManager getInstance() {
        return instance;
    }

    /**
     * 重新执行图片加载
     */
    public void onResume() {
        GlideWrapper.getDefalt().resume();
        ImageLoaderWrapper.getDefault().resume();
    }

    /**
     * 暂停图片加载
     */
    public void onPause() {
        GlideWrapper.getDefalt().pause();
        ImageLoaderWrapper.getDefault().pause();
    }

    /**
     * 清除缓存图片数据
     */
    public void clear() {
        ImageLoaderUtils.getFramework(GLIDE_TYPE)
                .createImageLoader()
                .cleanImageCache();
        ImageLoaderUtils.getFramework(IMAGELOADER_TYPE)
                .createImageLoader()
                .cleanImageCache();
    }

    /**
     * 加载网络图片
     * @param imageView ：显示图片的视图
     * @param urlStr：图片资源路径url
     * @param defaultImageResourceID:默认加载的图片资源id
     */
    public void displayImage(ImageView imageView, String urlStr, int defaultImageResourceID) {

        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(urlStr)) {
            if (defaultImageResourceID > 0) {
                imageView.setImageResource(defaultImageResourceID);
            }
            return;
        }

        ImageConfigProduct configProduct = ImageLoaderUtils.getFramework(loadFrameworkType)
                .createImageConfig();
        configProduct.setDefaulRes(defaultImageResourceID);
        configProduct.setLoadingRes(defaultImageResourceID);
        configProduct.setFailRes(defaultImageResourceID);
        configProduct.setFadeIn(0);
        ImageLoaderUtils.getFramework(loadFrameworkType)
                .createImageLoader()
                .display(
                        urlStr, imageView, configProduct);
    }
    /**
     * 加载网络图片
     * @param imageView ：显示图片的视图
     * @param urlStr：图片资源路径url
     * @param defaultImageResourceID:默认加载的图片资源id
     */
    public void displayImage(int shape,ImageView imageView, String urlStr, int defaultImageResourceID) {

        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(urlStr)) {
            if (defaultImageResourceID > 0) {
                imageView.setImageResource(defaultImageResourceID);
            }
            return;
        }

        ImageConfigProduct configProduct = ImageLoaderUtils.getFramework(loadFrameworkType)
                .createImageConfig();
        configProduct.setDefaulRes(defaultImageResourceID);
        configProduct.setLoadingRes(defaultImageResourceID);
        configProduct.setFailRes(defaultImageResourceID);
        configProduct.setFadeIn(0);
        configProduct.setShape(shape);
        ImageLoaderUtils.getFramework(loadFrameworkType)
                .createImageLoader()
                .display(
                        urlStr, imageView, configProduct);
    }
    /**
     * 加载网络图片
     * @param imageView ：显示图片的视图
     * @param urlStr：图片资源路径url
     * @param defaultImageResourceID:默认加载的图片资源id
     * @param wh :自适应图片宽高显示
     */
    public void displayImage(final ImageView imageView, String urlStr, int defaultImageResourceID,boolean wh) {

        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(urlStr)) {
            if (defaultImageResourceID > 0) {
                imageView.setImageResource(defaultImageResourceID);
            }
            return;
        }

        Glide.with(imageView.getContext())
                .load(urlStr)
                .apply(new RequestOptions().placeholder(defaultImageResourceID).error(defaultImageResourceID).centerCrop())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;

                    }
                })
                .into(imageView);

    }

    /**
     * 加载网络图片
     * @param imageView：显示图片的视图
     * @param urlStr：图片资源路径url
     * @param defaultImageResourceID:默认加载的图片资源id
     * @param loadframeworktype:加载图片选择的框架类型 1：Glide 2:ImageLoader
     */
    public void displayImage(ImageView imageView, String urlStr, int defaultImageResourceID, int loadframeworktype) {

        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(urlStr)) {
            if (defaultImageResourceID > 0) {
                imageView.setImageResource(defaultImageResourceID);
            }
            return;
        }

        loadFrameworkType = loadframeworktype;
        ImageConfigProduct configProduct = ImageLoaderUtils.getFramework(loadFrameworkType)
                .createImageConfig();
        configProduct.setDefaulRes(defaultImageResourceID);
        configProduct.setLoadingRes(defaultImageResourceID);
        configProduct.setFailRes(defaultImageResourceID);
        configProduct.setFadeIn(0);
        ImageLoaderUtils.getFramework(loadFrameworkType)
                .createImageLoader()
                .display(
                        urlStr, imageView, configProduct);
    }

    /**
     * ImageLoader模式加载图片显示进度
     * @param imageView
     * @param url
     * @param defaultImageResourceID
     * @param listener
     */
    public void displayImage(ImageView imageView, String url, int defaultImageResourceID, final ImageListener listener) {
        if (listener != null && imageView != null) {
            if (TextUtils.isEmpty(url)) {
                if (defaultImageResourceID > 0) {
                    imageView.setImageResource(defaultImageResourceID);
                }
                return;
            }
            DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(defaultImageResourceID)
                    .showImageForEmptyUri(defaultImageResourceID).showImageOnFail(0).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true).build();
            ImageLoader.getInstance().displayImage(url, imageView, options, new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    listener.onLoadingStarted(imageUri, view);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    listener.onLoadingFailed(imageUri, view, failReason);

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    listener.onLoadingComplete(imageUri, view, loadedImage);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    listener.onLoadingCancelled(imageUri, view);

                }
            }, new ImageLoadingProgressListener() {

                @Override
                public void onProgressUpdate(String imageUri, View view,
                                             int current, int total) {
                    listener.onProgressUpdate(imageUri, view, current, total);

                }
            });
        }
    }

    public interface ImageListener {
        void onLoadingStarted(String imageUri, View view);

        void onLoadingCancelled(String imageUri, View view);

        void onLoadingFailed(String imageUri, View view, FailReason failReason);

        void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

        void onProgressUpdate(String imageUri, View view, int current, int total);
    }

}