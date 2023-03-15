package com.video.withdrawal.config.prod;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("video.task.config.wechat-payment")
public class WeChatPaymentProperties {

    // 企业支付 API Url
    private String paymentToUserUrl;

}
