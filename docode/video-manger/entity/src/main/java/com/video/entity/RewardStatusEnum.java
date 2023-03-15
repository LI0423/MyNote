package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yifan 2021/12/23
 */
@Getter
@AllArgsConstructor
public enum RewardStatusEnum {

    CAN_GET(0, "可以领取"),

    CANT_GET(1, "不可以领取（任务未做或未做完）"),

    ALREADY_GET(2, "已经领取过")
    ;

    @JsonValue
    @EnumValue
    int code;

    String desc;
}
