package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayRecordStatusEnum {

    /**
     * 未知状态
     */
    OTHER(0, "未知状态"),

    /**
     * 支付成功
     */
    SUCCESS(1, "支付成功"),

    /**
     * 支付失败
     */
    FAIL(2, "支付失败")
    ;

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;
}
