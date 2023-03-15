package com.video.manager.domain.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryHiddenEnum {
    NOT_HIDDEN(0,"不屏蔽"),
    HIDDEN(1,"屏蔽"),
            ;


    CategoryHiddenEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
