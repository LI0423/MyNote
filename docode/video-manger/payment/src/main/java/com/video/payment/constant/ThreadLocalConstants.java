package com.video.payment.constant;

import java.util.HashMap;
import java.util.Map;

public abstract class ThreadLocalConstants {

    private static final ThreadLocal<Map<String, Object>> RUNTIME_PARAMS = new ThreadLocal<>();

    private static final String LOG_ID_KEY = "LOG_ID";

    private static Map<String, Object> getNotNullRuntimeParamsOrCreate() {
        Map<String, Object> runtimeParams = RUNTIME_PARAMS.get();
        // 同一个map都是同一个线程在操作, 故没必要使用线程安全的map
        if (runtimeParams == null) {
            runtimeParams = new HashMap<>();
            RUNTIME_PARAMS.set(runtimeParams);
        }

        return runtimeParams;
    }

    public static void setLogId(String logId) {
        Map<String, Object> runtimeParams = getNotNullRuntimeParamsOrCreate();
        runtimeParams.put(LOG_ID_KEY, logId);
    }

    public static String getLogId() {
        Map<String, Object> runtimeParams = getNotNullRuntimeParamsOrCreate();
        return (String) runtimeParams.get(LOG_ID_KEY);
    }

    public static void clean() {
        RUNTIME_PARAMS.remove();
    }

    private ThreadLocalConstants() {

    }
}
