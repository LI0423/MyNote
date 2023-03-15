package com.video.manager.domain.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: api
 * @description: CrabDataOrigin enum
 * @author: laojiang
 * @create: 2020-08-22 16:27
 **/
public enum CrabDataOriginStatusEnum {

    CRAB_COMPLETE(0,"录入完成"),
    ANALYSIS_COMPLETE(1,"解析完成"),
    ANALYSIS_FAIL(2,"解析失败"),
    ;


    CrabDataOriginStatusEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
