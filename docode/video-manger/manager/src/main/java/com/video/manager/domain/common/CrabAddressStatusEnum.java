package com.video.manager.domain.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: api
 * @description: crab address status
 * @author: laojiang
 * @create: 2020-08-22 16:19
 **/
public enum CrabAddressStatusEnum {

    INPUT_COMPLETE(0,"录入完成"),
    CRAB_COMPLETE(1,"抓取完成"),
    INPUT_FAIL(2,"抓取失败"),
    ;


    CrabAddressStatusEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
