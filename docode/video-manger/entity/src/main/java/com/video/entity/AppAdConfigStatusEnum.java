package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: video-manger
 * @description: 应用广告配置状态
 * @author: laojiang
 * @create: 2020-08-26 18:15
 **/
public enum AppAdConfigStatusEnum {

    OFFLINE(0,"线下"),
    ONLINE(1,"线上"),
    ;

    AppAdConfigStatusEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
