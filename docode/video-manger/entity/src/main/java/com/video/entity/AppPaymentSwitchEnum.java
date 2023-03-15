package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppPaymentSwitchEnum {

    /**
     * 开关关闭
     */
    OFF(0, "关闭"),

    /**
     * 开关开启
     */
    ON(1, "开启");

    @JsonValue
    @EnumValue
    private final int code;

    private final String desc;
}
