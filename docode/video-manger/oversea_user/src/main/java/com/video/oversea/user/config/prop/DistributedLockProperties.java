package com.video.oversea.user.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties("video.task.config.distributed-lock")
public class DistributedLockProperties {

    private Duration expire;

}
