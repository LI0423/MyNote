package com.video.withdrawal.domain.dto.third;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeChatPaymentReturnCodeEnum {
    SUCCESS(0),
    FAIL(1);

    int code;
}
