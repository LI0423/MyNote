package com.video.user.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("video.task.config.wechat-auth")
public class WeChatAuthProperties {

    private String getAccessTokenUrl;

    private String getUserInfoUrl;
}
