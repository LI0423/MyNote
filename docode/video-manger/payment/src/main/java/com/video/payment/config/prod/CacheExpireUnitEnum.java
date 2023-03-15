package com.video.payment.config.prod;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CacheExpireUnitEnum {
    SECONDS(0),
    MILLISECONDS(1)
    ;

    private int code;
}
