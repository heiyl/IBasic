package com.heiyl.basic.constants;

import com.heiyl.basic.BuildConfig;

public class API {
    public static Boolean isDebug = Boolean.TRUE;//是否debug版本
    private static final String HOST;

    static {
        switch (BuildConfig.API_ENV) {
            case "TEST":
                isDebug = Boolean.TRUE;
                HOST = "http://123.57.17.29:9090/openapi/";// 测试地
                break;
            default:
                isDebug = Boolean.FALSE;
                HOST = "http://service.66xue.com/openapi/"; // 正式地址
                break;

        }
    }
    /**
     * 获取首页数据
     */
    public static String getIndexMessages = HOST + "main/index.shtml";
}
