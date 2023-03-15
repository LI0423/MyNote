package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @program: video-manger
 * @description: 金币来源
 * @author: laojiang
 * @create: 2020-08-26 18:15
 **/
public enum ScoreSourceEnum {

    DO_TASK(0,"做任务"),
    EXCHANGE_GOLD_COIN(1,"兑换金币"),
    ;

    ScoreSourceEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    private final int code;
    private final String description;
}
