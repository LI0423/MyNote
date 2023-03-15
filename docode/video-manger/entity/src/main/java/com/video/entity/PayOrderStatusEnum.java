package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayOrderStatusEnum {

    /**
     * 未完成
     */
    UNDONE(0, "未完成"),

    /**
     * 支付成功
     */
    SUCCESS(1, "支付成功"),

    /**
     * 支付失败
     */
    FAIL(2, "支付失败")
    ;

    @JsonValue
    @EnumValue
    int code;

    String desc;
}
