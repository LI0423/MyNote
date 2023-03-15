package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayOrderPayTypeEnum {

    /**
     * JSAPI下单
     */
    JSAPI(0, "JSAPI下单"),

    /**
     * APP下单
     */
    APP(1, "APP下单")
    ;

    @EnumValue
    @JsonValue
    int code;

    String desc;
}
