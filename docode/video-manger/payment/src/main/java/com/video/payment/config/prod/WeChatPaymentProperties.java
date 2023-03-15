package com.video.payment.config.prod;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("video.task.config.wechat-payment")
public class WeChatPaymentProperties {

    /**
     * APP下单api地址
     */
    private String appPaymentApiUrl;

    /**
     * 查询订单api地址
     */
    private String selectOrderApiUrl;

    /**
     * 支付通知api地址
     */
    private String payNotifyApiUrl;

    /**
     * 退款api地址
     */
    private String refundApiUrl;

    /**
     * 退款通知api地址
     */
    private String refundNotifyApiUrl;

    /**
     * 默认订单交易结束时间
     */
    private Integer defaultOrderTimeExpireMinute;

    // 企业支付 API Url
    private String paymentToUserUrl;
}
