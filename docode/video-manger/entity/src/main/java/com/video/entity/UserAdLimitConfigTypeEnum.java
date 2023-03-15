/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum UserAdLimitConfigTypeEnum {

    SOURCE(0,"source"),
    AID_FEFAULT(1,"aidDefault"),
    AID(2,"aid"),

    ;

    UserAdLimitConfigTypeEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
