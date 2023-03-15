package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @program: video-manger
 * @description: 任务类型
 * @author: laojiang
 * @create: 2020-08-26 18:15
 **/
@Getter
public enum TaskTypeEnum {

    SCORE(0,"积分任务"),
    GOLD_COIN(1,"金币任务"),
    NOT_REWARD(2, "无奖励任务"),
    RED(3,"红包任务"),
    GOLD_BEAN(4,"金豆任务"),
    ALL_REWARD(5, "红包+金豆共同奖励任务"),
    ;

    TaskTypeEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
