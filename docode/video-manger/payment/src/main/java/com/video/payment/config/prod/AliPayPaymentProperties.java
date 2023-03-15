package com.video.payment.config.prod;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("video.task.config.alipay-payment")
public class AliPayPaymentProperties {

    /**
     * ali支付服务server url
     */
    private String serverUrl;

    /**
     * 支付通知api地址
     */
    private String payNotifyApiUrl;

    /**
     * 退款通知api地址
     */
    private String refundNotifyApiUrl;

    /**
     * app支付接口名
     */
    private String tradeAppPayMethod;

    /**
     * 默认订单交易结束时间
     */
    private Integer defaultOrderTimeExpireMinute;
}
