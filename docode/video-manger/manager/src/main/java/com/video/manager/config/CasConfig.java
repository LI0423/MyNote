package com.video.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 根据不同环境从配置文件里读取配置
 *
 * ?为啥读不到？？？
 *
 * @author zhangchao
 */
@Component
@ConfigurationProperties(prefix = "config.cas")
@Data
public class CasConfig {

    /**
     * 当前应用程序的baseUrl（注意最后面的斜线
     */
    private String serverUrl;

    /**
     * 当前应用程序首页
     */
    private String mainPage;

    /**
     * 当前应用程序登出地址
     */
    private String appLogout;

    /**
     * CAS服务器地址
     */
    private String casServer;

    /**
     * CAS登陆服务器地址
     */
    private String casServerLogin;

    /**
     * CAS登出服务器地址
     */
    private String casServerLogout;

    private String loginUrl;
}
