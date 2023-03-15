package com.video.payment.domain.dto.third;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wangwei
 */
@Getter
@AllArgsConstructor
public enum WeChatAmountCurrencyEnum {

    /**
     * 人民币枚举
     */
    CNY(0, "CNY", "人民币");

    int code;

    String value;

    String desc;
}
