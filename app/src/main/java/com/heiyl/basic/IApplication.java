/*
 * Copyright (C) 2015 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.heiyl.basic;

import android.app.Application;

import com.common.basic.BasicConstants;
import com.common.basic.okhttpfinal.business.OkHttpFinal;
import com.common.basic.okhttpfinal.business.OkHttpFinalConfiguration;
import com.heiyl.basic.constants.API;
import com.squareup.leakcanary.LeakCanary;

public class IApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initOkHttpFinal();
            }
        }).start();
    }

    private void initOkHttpFinal() {
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder()
                .setTimeout(BasicConstants.REQ_TIMEOUT)
                .setDebug(API.isDebug);
        OkHttpFinal.getInstance().init(builder.build());
    }


}
