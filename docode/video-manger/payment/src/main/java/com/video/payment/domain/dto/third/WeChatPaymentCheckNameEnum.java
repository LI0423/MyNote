package com.video.payment.domain.dto.third;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeChatPaymentCheckNameEnum {
    NO_CHECK(0, "不校验真实姓名"),
    FORCE_CHECK(1, "强校验真实姓名");

    int code;

    String desc;
}
