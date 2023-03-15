package com.video.payment.domain.dto.third;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易状态枚举
 */
@Getter
@AllArgsConstructor
public enum WeChatPaymentTradeStateEnum {

    /**
     * 支付成功
     */
    SUCCESS(0, "支付成功"),

    /**
     * 转入退款
     */
    REFUND(1, "转入退款"),

    /**
     * 未支付
     */
    NOTPAY(2, "未支付"),

    /**
     * 已关闭
     */
    CLOSED(3, "已关闭"),

    /**
     * 已撤销（仅付款码支付会返回）
     */
    REVOKED(4, "已撤销"),

    /**
     * 用户支付中（仅付款码支付会返回）
     */
    USERPAYING(5, "用户支付中"),

    /**
     * 支付失败（仅付款码支付会返回）
     */
    PAYERROR(6, "支付失败")
    ;

    int code;

    String desc;
}
