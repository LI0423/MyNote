package com.video.payment.domain.dto.third;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易类型枚举
 */
@Getter
@AllArgsConstructor
public enum WeChatPaymentTradeTypeEnum {

    /**
     * 公众号支付
     */
    JSAPI(0, "公众号支付"),

    /**
     * 扫码支付
     */
    NATIVE(1, "扫码支付"),

    /**
     * APP支付
     */
    APP(2, "APP支付"),

    /**
     * 付款码支付
     */
    MICROPAY(3, "付款码支付"),

    /**
     * H5支付
     */
    MWEB(4, "H5支付"),

    /**
     * 刷脸支付
     */
    FACEPAY(5, "刷脸支付")
    ;

    int code;

    String desc;
}
