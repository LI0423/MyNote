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
public enum TaskGroupEnum {

    NEW_HAND(0,"新手任务"),
    DAILY(1,"日常任务"),
    SIGN_IN(2,"签到任务"),
    VIDEO(3,"视频时长任务"),
    COMMON(4,"公共任务"),
    LOG_IN(5, "登录任务"),
    HIDE(6,"隐藏任务"),
    CIRCLE(7,"首页转圈任务"),
    invitation(8, "邀请事件"),

    ;

    TaskGroupEnum(int code, String description) {
        this.code=code;
        this.description=description;
    }

    @EnumValue
    @JsonValue
    private final int code;
    private final String description;
}
