package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author zhangshiyu
 * @date 2022/3/25 16:31
 */
@Getter
public enum RewardTypeEnum {
    DEFAULT(0,"占位"),
    GOLD(1,"金豆"),
    CASH(2,"现金（红包）");

    RewardTypeEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
