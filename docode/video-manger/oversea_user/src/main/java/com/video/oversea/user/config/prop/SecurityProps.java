/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.video.oversea.user.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jason on 2019/3/3.
 */

@ConfigurationProperties(prefix = "video.task.config.security")
@Data
@Configuration
public class SecurityProps {
    private String salt;
    private String encryptKey;
}
