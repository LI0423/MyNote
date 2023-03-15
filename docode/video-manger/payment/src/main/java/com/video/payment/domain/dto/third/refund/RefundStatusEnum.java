package com.video.payment.domain.dto.third.refund;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 退款状态枚举
 */
@Getter
@AllArgsConstructor
public enum RefundStatusEnum {

    /**
     * 退款成功
     */
    SUCCESS(0, "退款成功"),

    /**
     * 退款关闭
     */
    CLOSED(1, "退款关闭"),

    /**
     * 退款处理中
     */
    PROCESSING(2, "退款处理中"),

    /**
     * 退款异常
     */
    ABNORMAL(3, "退款异常")
    ;

    int code;

    String desc;
}
