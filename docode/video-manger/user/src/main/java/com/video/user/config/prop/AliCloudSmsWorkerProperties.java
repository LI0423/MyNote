package com.video.user.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("video.task.config.ali-cloud-sms.worker")
public class AliCloudSmsWorkerProperties {

    /**
     * 最大工作线程数
     */
    private int maxCount;

    /**
     * 预处理事件队列大小
     */
    private int workQueueSize;
}
