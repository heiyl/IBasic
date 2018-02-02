package com.heiyl.basic.base;

import android.support.v7.app.AppCompatActivity;

import com.common.basic.okhttpfinal.business.HttpCycleContext;
import com.common.basic.okhttpfinal.business.HttpTaskHandler;

/**
 * IBasic
 * 2018/2/1.
 * yulong
 */

public class BaseActivity extends AppCompatActivity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    protected void onDestroy() {
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
        super.onDestroy();
    }
}
