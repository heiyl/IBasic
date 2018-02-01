package com.heiyl.basic.service;


import com.common.basic.net.AbsModelCenter;
import com.common.basic.net.ResponseListener;
import com.common.basic.net.ResultDto;
import com.heiyl.basic.constants.Task;
import com.heiyl.basic.models.IndexMessagesDto;

/**
 * 首页网络请求业务类
 * llxue2.0
 * 2016/1/21.
 * yulong
 */
public class IndexModelCenter extends AbsModelCenter<ResultDto> {

    @Override
    public void onResponseSuccess(int taskId, String content,
                                  ResponseListener<ResultDto> listener) {
        if (listener != null) {
            switch (taskId) {
                case Task.FLAG_GET_INDEXMESSAGES:
                    try{
                        IndexMessagesDto indexMessagesDto = ResultDto.get(IndexMessagesDto.class, content);
                        listener.onResponseSuccess(taskId, indexMessagesDto,content);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    ResultDto dto1 = ResultDto.get(ResultDto.class, content);
                    listener.onResponseSuccess(taskId, dto1,content);
                    break;
            }
        }
    }

    @Override
    public void onResponseFailure(int taskId, String content, ResponseListener<ResultDto> listener){
        super.onResponseFailure(taskId,content,listener);
    }
}
