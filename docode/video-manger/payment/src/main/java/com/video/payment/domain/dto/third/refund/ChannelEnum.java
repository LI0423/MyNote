package com.video.payment.domain.dto.third.refund;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 退款渠道
 */
@Getter
@AllArgsConstructor
public enum ChannelEnum {

    /**
     * 原路退款
     */
    ORIGINAL(0, "原路退款"),

    /**
     * 退回到余额
     */
    BALANCE(1, "退回到余额"),

    /**
     * 原账户异常退到其他余额账户
     */
    OTHER_BALANCE(2, "原账户异常退到其他余额账户"),

    /**
     * 原银行卡异常退到其他银行卡
     */
    OTHER_BANKCARD(3, "原银行卡异常退到其他银行卡")
    ;

    int code;

    String desc;
}
