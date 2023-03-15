package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WithdrawalTypeEnum {
    UNKNOW(0, "未知项"),
    NEWCOMER_EXCLUSIVE(1, "新人专享提现"),
    TASK(2, "任务提提现"),
    DAILY(3, "天天提提现"),
    COMMON(4, "通用提现"),
    COMMON_ONE_TIME(5, "通用一次性提现"),

    /**
     * 用金币提现, 这种类型的提现不受全局提现的控制, 每一项每个用户都能提现多次(可单独配置), 每天次数会重置
     */
    COMMON_DAILY_MANY(6, "通用每天多次提现"),

    /**
     * 用金币提现, 这种类型的提现不受全局提现的控制, 每一项每个用户都能提现多次(可单独配置), 但是次数不会重置
     */
    COMMON_MANY(7, "通用全局多次提现"),

    /**
     * 用金豆提现, 这种类型的提现不受全局提现的控制, 每一项每个用户都能提现多次(可单独配置), 每天次数会重置
     */
    COMMON_GOLD_BEAN_DAILY_MANY(8, "通用金豆每天多次提现"),

    /**
     * 用金豆提现, 这种类型的提现不受全局提现的控制, 每一项每个用户都能提现多次(可单独配置), 但是次数不会重置
     */
    COMMON_GOLD_BEAN_MANY(9, "通用金豆全局多次提现"),
    ;

    @EnumValue
    @JsonValue
    private int code;

    private String description;
}
