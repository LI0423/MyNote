package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MedalStateEnum {
    UNCOMPLETED(0, "未完成"),
    CANCOMPLETED(1, "可完成"),
    COMPLETED(2, "已完成")
    ;

    @EnumValue
    @JsonValue
    private Integer code;

    private String description;
}
