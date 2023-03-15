package com.video.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: video manager
 * @description:
 * @author: laojiang
 * @create: 2020-10-22 15:13
 **/
@Configuration
@ConfigurationProperties(prefix = "config.juwin")
@Data
public class AppConfig {

    private String host;
    private String scheme;
    private Integer port;
}
