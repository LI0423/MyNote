package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @program: video-manger
 * @description: 任务分组
 * @author: laojiang
 * @create: 2020-08-26 18:15
 **/
@Getter
public enum UserAdRiskTypeEnum {

    APP(0,"应用"),
    USER(1,"用户"),
    MODEL(2,"手机型号"),

    ;

    UserAdRiskTypeEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
