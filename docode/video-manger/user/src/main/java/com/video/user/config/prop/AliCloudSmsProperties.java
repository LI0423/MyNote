package com.video.user.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("video.task.config.ali-cloud-sms")
public class AliCloudSmsProperties {

    private String sysDomain;

    private String sysVersion;

    private AliCloudSmsWorkerProperties worker;
}
