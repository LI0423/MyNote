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
public enum RedTimingRewardStatusEnum {

    CAN_GET(0, "可以领取（进行中）"),

    CANT_GET(1, "不可以领取（任务未开始）"),

    ALREADY_GET(2, "已经领取过"),

    TIMEOUT(6, "不可以领取（任务已结束）")
    ;

    @JsonValue
    @EnumValue
    int code;

    String desc;
}
