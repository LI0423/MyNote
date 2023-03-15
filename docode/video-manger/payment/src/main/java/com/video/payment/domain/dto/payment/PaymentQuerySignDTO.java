package com.video.payment.domain.dto.payment;

import lombok.Data;

/**
 * 给客户掉起app支付时提供的请求参数
 */
@Data
public class PaymentQuerySignDTO {

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 32位随机字符串
     */
    private String nonceStr;

    /**
     * 预支付会话id
     */
    private String prepayId;

    /**
     * 扩展字段
     */
    private String extPackage;

    /**
     * 商户id
     */
    private String mchId;

    /**
     * 签名
     */
    private String sign;
}
