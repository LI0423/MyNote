package com.video.oversea.payment.domain.dto.third;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GooglePayTradeStatusEnum {

    NOTPAY(0, "待付款"),

    SUCCESS(1, "支付成功"),

    FREEUSE(2, "免费试用"),

    ;

    int code;

    String desc;
}
