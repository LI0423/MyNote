package com.video.manager.config;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @program: video manager
 * @description:
 * @author: laojiang
 * @create: 2020-10-22 15:02
 **/
@Configuration
public class OkHttpConfig {

    /**
     * 超时时间，单位（毫秒）
     */
    @Value("${okhttp.timeout:7000}")
    private Long timeout;

    /**
     * 最大连接数量
     */
    @Value("${okhttp.maxConnection:200}")
    private Integer maxConnection;


    /**
     * 核心连接数量
     */
    @Value("${okhttp.coreConnection:10}")
    private Integer coreConnection;

    /**
     * 连接失败是否重试
     */
    @Value("${okhttp.resetConnection:false}")
    private boolean resetConnection;


    /**
     * 单域名最大请求线程
     */

    @Value("${okhttp.maxHostConnection:30}")
    private Integer maxHostConnection;


    @Bean
    public OkHttpClient okHttpClient(){

        // 创建Dispatcher，对请求线程进行控制
        Dispatcher dispatcher = new Dispatcher();
        // 设置单域名最大连接
        dispatcher.setMaxRequestsPerHost(maxHostConnection);
        // 设置最大连接
        dispatcher.setMaxRequests(maxConnection);
        // 创建OkHttpClient连接
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .dispatcher(dispatcher)
                .retryOnConnectionFailure(resetConnection)
                .callTimeout(timeout, TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(maxConnection,coreConnection,TimeUnit.MILLISECONDS))
                .build();
        return okHttpClient;
    }
}
