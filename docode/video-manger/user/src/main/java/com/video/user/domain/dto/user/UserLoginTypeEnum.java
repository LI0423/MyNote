package com.video.user.domain.dto.user;

public enum UserLoginTypeEnum {
    WeChat(0, "微信");

    UserLoginTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private final int code;
    private final String description;
}