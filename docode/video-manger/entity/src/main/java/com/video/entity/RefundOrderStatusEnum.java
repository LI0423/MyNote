package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RefundOrderStatusEnum {

    /**
     * 未完成
     */
    UNDONE(0, "未完成"),

    /**
     * 退款成功
     */
    SUCCESS(1, "退款成功"),

    /**
     * 退款失败
     */
    FAIL(2, "退款失败")
    ;

    @JsonValue
    @EnumValue
    int code;

    String desc;
}
