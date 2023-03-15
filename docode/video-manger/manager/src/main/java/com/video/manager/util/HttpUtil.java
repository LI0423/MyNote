package com.video.manager.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class HttpUtil {
    /**
     * 给定特定的url，参数封装成一个map,get请求
     *
     * @param url
     * @param params
     * @return 响应字符串
     */
    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String postResponseWithParamsInMap(String url,Map<String,String> params){
        FormBody.Builder builder = new FormBody.Builder();
        if(params!=null){
            for(String key:params.keySet()){
                builder.add(key,params.get(key));
            }
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder().url(url).post(body).build();

        try {
            String res = client.newCall(request).execute().body().string();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}