package com.video.payment.domain.dto.third.refund;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资金账户枚举
 */
@Getter
@AllArgsConstructor
public enum AccountEnum {

    /**
     * 可用余额
     */
    AVAILABLE(0, "可用余额"),

    /**
     * 不可用余额
     */
    UNAVAILABLE(1, "不可用余额")
    ;

    int code;

    String desc;
}
