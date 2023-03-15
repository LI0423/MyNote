package com.video.user.config;

import com.video.user.config.prop.HttpClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HttpClientConfig {

    @Bean
    @Autowired
    public RequestConfig requestConfig(HttpClientProperties httpClientProperties) {
        log.info("初始化 RequestConfig, connectionRequestTimeout:{}, connectTimeout:{}, socketTimeout:{}",
                httpClientProperties.getConnectionRequestTimeout(),
                httpClientProperties.getConnectTimeout(),
                httpClientProperties.getSocketTimeout()
        );
        return RequestConfig.custom()
                .setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout()) // 从连接池获取连接超时时间
                .setConnectTimeout(httpClientProperties.getConnectTimeout())
                .setSocketTimeout(httpClientProperties.getSocketTimeout())
                .build();
    }

    @Bean
    @Autowired
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(HttpClientProperties httpClientProperties) {
        log.info("初始化 PoolingHttpClientConnectionManager, defaultMaxPerRoute:{}, maxTotal:{}, validateAfterInactivity:{}",
                httpClientProperties.getDefaultMaxPerRoute(),
                httpClientProperties.getMaxTotal(),
                httpClientProperties.getValidateAfterInactivity()
        );
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setDefaultMaxPerRoute(httpClientProperties.getDefaultMaxPerRoute());
        httpClientConnectionManager.setMaxTotal(httpClientProperties.getMaxTotal());
        httpClientConnectionManager.setValidateAfterInactivity(httpClientProperties.getValidateAfterInactivity());
        return httpClientConnectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder(RequestConfig requestConfig,
                                               PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置连接池
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
        //设置超时时间
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        //定义连接管理器将由多个客户端实例共享。如果连接管理器是共享的，则其生命周期应由调用者管理，如果客户端关闭则不会关闭。
        httpClientBuilder.setConnectionManagerShared(true);

        return httpClientBuilder;
    }
}
