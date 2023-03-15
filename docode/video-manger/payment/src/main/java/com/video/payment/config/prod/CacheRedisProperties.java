package com.video.payment.config.prod;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("video.task.config.cache-redis")
public class CacheRedisProperties {
    private String host;

    private int port;

    private long time;

    private long maxWait;

    private int maxActive;

    private int maxIdle;

    private int minIdle;
}
