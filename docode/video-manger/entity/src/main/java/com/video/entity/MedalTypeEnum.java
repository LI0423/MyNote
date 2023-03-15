package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MedalTypeEnum {
    UNKNOWN(0, "未知"),
    CONTN_SIGNIN(1, "连续活跃勋章"),
    VIEW_VIDEO(2, "观看视频个数勋章"),
    VIEW_DURATION(3, "观看视频时长勋章")
    ;

    @EnumValue
    @JsonValue
    private Integer code;

    private String description;
}
