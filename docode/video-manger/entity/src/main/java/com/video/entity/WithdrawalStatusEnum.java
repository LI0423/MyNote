package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @program: video-manger
 * @description: 提现
 * @author: laojiang
 * @create: 2020-08-26 18:15
 **/
@Getter
public enum WithdrawalStatusEnum {

    ALREADY_PAYMENT(0,"已经支付"),
    AUDIT(1,"审核中"),
    FAIL(2,"支付失败")
    ;

    WithdrawalStatusEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;


}
