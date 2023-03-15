package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataConfigStatusEnum {
    DELETED(0, "删除"),
    VALID(1, "有效");

    @EnumValue
    private int code;

    private String description;
}
