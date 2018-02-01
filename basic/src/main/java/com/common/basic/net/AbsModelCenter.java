package com.common.basic.net;

import com.common.basic.okhttpfinal.business.HttpRequest;
import com.common.basic.okhttpfinal.business.RequestParams;
import com.common.basic.okhttpfinal.business.StringHttpRequestCallback;
import com.common.basic.okhttpfinal.business.Utils;


public class AbsModelCenter<T extends ResultDto> {
    private static final String TAG = AbsModelCenter.class.getSimpleName();

    public void onResponseStart(int taskId, ResponseListener<T> listener) {
        if (listener != null) {
            listener.onResponseStart(taskId);
        }
    }

    public void onResponseLoading(int taskId, int progress, long networkSpeed, boolean done,
                                  ResponseListener<T> listener) {
        if (listener != null) {
            listener.onResponseLoading(taskId, progress, networkSpeed, done);
        }
    }

    public void onResponseSuccess(int taskId, String content, ResponseListener<T> listener) {
    }

    public void onResponseFailure(int taskId, String content, ResponseListener<T> listener) {
        if (listener != null) {
            listener.onResponseFailure(taskId, content);
        }
    }

    /**
     * get请求
     * @param url
     * @param requestParams
     * @param taskId
     * @param listener
     */
    public void getRequest(String url, RequestParams requestParams, final int taskId,
                           final ResponseListener<T> listener) {
        if(requestParams == null) {
            requestParams = new RequestParams();
        }
        HttpRequest.get(url, requestParams, new StringHttpRequestCallback() {

            @Override
            public void onStart() {
                super.onStart();
                onResponseStart(taskId, listener);
            }

            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                onResponseSuccess(taskId, s, listener);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                onResponseFailure(taskId, msg, listener);
            }

            @Override
            public void onProgress(int progress, long networkSpeed, boolean done) {
                super.onProgress(progress, networkSpeed, done);
                onResponseLoading(taskId, progress, networkSpeed, done, listener);
            }
        });
    }

    /**
     * post请求
     * @param url
     * @param requestParams
     * @param taskId
     * @param listener
     */
    public void postRequest(String url, RequestParams requestParams, final int taskId,
                            final ResponseListener<T> listener) {
        if(requestParams == null) {
            requestParams = new RequestParams();
        }
        HttpRequest.post(url, requestParams, new StringHttpRequestCallback() {

            @Override
            public void onStart() {
                super.onStart();
                onResponseStart(taskId, listener);
            }

            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                onResponseSuccess(taskId, s, listener);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                onResponseFailure(taskId, msg, listener);
            }

            @Override
            public void onProgress(int progress, long networkSpeed, boolean done) {
                super.onProgress(progress, networkSpeed, done);
                onResponseLoading(taskId, progress, networkSpeed, done, listener);
            }
        });
    }

    /**
     * 取消指定的url请求
     * @param srcUrl:请求链接
     * @param requestParams：请求参数
     */
    public void cancleRequest(String srcUrl, RequestParams requestParams) {
        String url = Utils.getFullUrl(srcUrl, requestParams.getFormParams(), requestParams.isUrlEncoder());
        HttpRequest.cancel(url);
    }
}
