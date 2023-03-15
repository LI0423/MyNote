package com.video.payment.domain.dto.third.refund;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 退款资金来源枚举
 */
@Getter
@AllArgsConstructor
public enum FundsAccountEnum {

    /**
     * 未结算资金
     */
    UNSETTLED(0, "未结算资金"),

    /**
     * 可用余额账户
     */
    AVAILABLE(1, "可用余额账户"),

    /**
     * 不可用余额
     */
    UNAVAILABLE(2, "不可用余额"),

    /**
     * 运营户
     */
    OPERATION(3, "运营户"),

    /**
     * 基本账户（含可用余额和不可用余额）
     */
    BASIC(4, "基本账户")
    ;

    int code;

    String desc;
}
