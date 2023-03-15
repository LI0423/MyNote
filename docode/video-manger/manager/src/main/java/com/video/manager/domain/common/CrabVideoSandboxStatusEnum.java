package com.video.manager.domain.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: api
 * @description: CrabDataOrigin enum
 * @author: laojiang
 * @create: 2020-08-22 16:27
 **/
public enum CrabVideoSandboxStatusEnum {

    NOT_PUBLISH(0,"没有发布"),
    PUBLISH_COMPLETE(1,"发布完成"),
    DUPLICATE(2,"重复"),
    PUBLISH_FAIL(3,"发布失败"),
    DELETE(4,"删除"),
    ;


    CrabVideoSandboxStatusEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
