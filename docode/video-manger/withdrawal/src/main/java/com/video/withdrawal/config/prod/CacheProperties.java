package com.video.withdrawal.config.prod;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("video.task.config.cache")
public class CacheProperties {

    private int minExpire;

    private int maxExpire;

    private CacheExpireUnitEnum unit;

}
