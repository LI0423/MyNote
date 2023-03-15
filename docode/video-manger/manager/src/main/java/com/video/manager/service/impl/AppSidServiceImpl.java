package com.video.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.video.manager.config.AppConfig;
import com.video.manager.service.AppSidService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @program: mango
 * @description:
 * @author: laojiang
 * @create: 2020-10-22 14:54
 **/
@Service
@Slf4j
public class AppSidServiceImpl implements AppSidService {

    private OkHttpClient okHttpClient;
    private AppConfig appConfig;

    @Autowired
    public AppSidServiceImpl(OkHttpClient okHttpClient, AppConfig appConfig) {
        this.okHttpClient = okHttpClient;
        this.appConfig = appConfig;
    }

    /**
     *
     *{
     *     "code":200,
     *     "msg":"success",
     *     "data":[
     *         "6071000308-176125864",
     *         "6011000309-2034997114",
     *         "6051000310-1466574352",
     *         "6051000310-1466574352",
     *         "6051000310-1466574352"
     *     ]
     * }
     *
     * @param pkg
     * @return
     */
    @Override
    public List<String> findSid(String pkg) {

        HttpUrl httpUrl=new HttpUrl.Builder()
                .scheme(appConfig.getScheme())
                .host(appConfig.getHost())
                .port(appConfig.getPort())
                .addPathSegment("open")
                .addPathSegment("sid")
                .addQueryParameter("pkg",pkg)
                .build();

        log.info("juwin={}",httpUrl);
        Request request=new Request.Builder().url(httpUrl)
                .method("GET",null)
                .build();

        Response response= null;

        try {
            response = okHttpClient.newCall(request).execute();
            String res=response.body().string();

            List<String> sidList=(List<String>) JSON.parseObject(res).get("data");
            return sidList;
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

}
