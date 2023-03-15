package com.video.payment.domain.dto.third;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeChatPaymentReturnCodeEnum {
    SUCCESS(0),
    FAIL(1);

    int code;
}
