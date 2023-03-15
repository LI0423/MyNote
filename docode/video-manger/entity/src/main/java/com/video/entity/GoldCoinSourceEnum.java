package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @program: video-manger
 * @description: 金币来源
 * @author: laojiang
 * @create: 2020-08-26 18:15
 **/
public enum GoldCoinSourceEnum {

    SCORE(0,"积分兑换"),
    DO_TASK(1,"做任务"),
    WITHDRAWAL(2,"提现"),
    SIGNIN(3,"签到"),
    WITHDRAWAL_FAIL(4,"提现失败"),
    INVITATION(5, "邀请好友领奖")
    ;

    GoldCoinSourceEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    private final int code;
    private final String description;
}
