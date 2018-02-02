package com.heiyl.basic.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.common.basic.net.ResponseListener;
import com.common.basic.net.ResultDto;
import com.common.basic.okhttpfinal.business.RequestParams;
import com.common.basic.tools.utils.JsonFormatUtils;
import com.heiyl.basic.R;
import com.heiyl.basic.base.BaseActivity;
import com.heiyl.basic.constants.API;
import com.heiyl.basic.constants.Task;
import com.heiyl.basic.models.IndexMessagesDto;
import com.heiyl.basic.service.IndexModelCenter;

public class MainActivity extends BaseActivity implements ResponseListener<ResultDto>,View.OnClickListener{

    RequestParams requestParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_post = (TextView)this.findViewById(R.id.tv_post);
        tv_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

//        authToken=c1974ae97e8d4f65b0be8cb57faa9c9b&channeltype=5
//        http://123.57.17.29:9090/openapi/main/index.shtml?pageSize=20&pageNo=1
        request();

    }

    /**
     * 请求获取热门话题列表
     */
    private void request() {
        try {
            IndexModelCenter modelCenter = new IndexModelCenter();
            requestParams = new RequestParams(this);
            requestParams.addHeader("authToken","c1974ae97e8d4f65b0be8cb57faa9c9b");
            requestParams.addHeader("channeltype","2");
            requestParams.addFormDataPart("pageSize", "10");
            requestParams.addFormDataPart("pageNo", "1");
            modelCenter.getRequest(API.getIndexMessages, requestParams, Task.FLAG_GET_INDEXMESSAGES, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            IndexModelCenter modelCenter = new IndexModelCenter();
            requestParams = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("authToken","c1974ae97e8d4f65b0be8cb57faa9c9b");
            jsonObject.put("channeltype","2");
            jsonObject.put("pageSize", "10");
            jsonObject.put("pageNo", "1");
            requestParams.setRequestBody(MediaType.parse("application/json;charset=utf-8"),jsonObject.toString());
            modelCenter.getRequest(API.getIndexMessages, requestParams, Task.FLAG_GET_INDEXMESSAGES, this);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onResponseSuccess(int taskId, ResultDto dto, String content) {
        Log.i("heiyulong", JsonFormatUtils.formatJson(content));
        try {
            switch (taskId) {
                case Task.FLAG_GET_INDEXMESSAGES:
                    IndexMessagesDto resultDto = (IndexMessagesDto) dto;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onResponseFailure(int taskId, String content) {
        Log.i("heiyulong", JsonFormatUtils.formatJson(content));

    }

    @Override
    public void onResponseLoading(int taskId, int progress, long networkSpeed, boolean done) {

    }

    @Override
    public void onResponseStart(int taskId) {

    }
}
