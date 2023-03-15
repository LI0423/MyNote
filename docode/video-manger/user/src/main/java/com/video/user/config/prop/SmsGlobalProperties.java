package com.video.user.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("video.task.config.sms-global")
public class SmsGlobalProperties {

    private Long sendVerCodeTimeInterval;

    private Long verCodeEffectiveTime;
}
