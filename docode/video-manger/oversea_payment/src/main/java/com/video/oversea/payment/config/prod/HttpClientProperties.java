package com.video.oversea.payment.config.prod;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("video.task.config.http-client")
public class HttpClientProperties {
    private  int connectionRequestTimeout;

    private int connectTimeout;

    private int socketTimeout;

    private int defaultMaxPerRoute;

    private int maxTotal;

    private int validateAfterInactivity;
}
