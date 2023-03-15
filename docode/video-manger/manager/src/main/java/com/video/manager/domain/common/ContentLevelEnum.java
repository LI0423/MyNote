package com.video.manager.domain.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: api
 * @description: crab address status
 * @author: laojiang
 * @create: 2020-08-22 16:19
 **/
public enum ContentLevelEnum {

    UNLIMITED(0,"不限制"),
    LIMIT_MINOR(1,"限制青少年"),
    ;


    ContentLevelEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
