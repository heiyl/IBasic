package com.common.basic.net;


/**
 * 网络请求回调类
 * @param <T>
 */
public interface ResponseListener<T extends ResultDto> {

    /**
     * 网络请求开始
     * @param taskId：任务ID
     */
    void onResponseStart(int taskId);

    /**
     * 下载进度
     * @param taskId ：任务ID
     * @param progress:下载进度
     * @param networkSpeed：下载速度
     * @param done：是否下载完成
     */
    void onResponseLoading(int taskId, int progress, long networkSpeed, boolean done);

    /**
     * 请求成功
     * @param taskId：任务ID
     * @param content：返回成功内容
     */
    void onResponseSuccess(int taskId, T dto, String content);

    /**
     * 请求失败
     * @param taskId：任务ID
     * @param content:返回错误内容
     */
    void onResponseFailure(int taskId, String content);

}
