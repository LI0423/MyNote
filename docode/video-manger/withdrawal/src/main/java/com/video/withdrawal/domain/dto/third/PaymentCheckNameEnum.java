package com.video.withdrawal.domain.dto.third;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentCheckNameEnum {
    NO_CHECK(0, "不校验真实姓名"),
    FORCE_CHECK(1, "强校验真实姓名");

    int code;

    String desc;
}
