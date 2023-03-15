package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayOrderChannelEnum {

    /**
     * 微信渠道
     */
    WE_CHAT(0, "微信"),

    /**
     * 支付宝渠道
     */
    ALI_PAY(1, "支付宝"),

    /**
     * google渠道
     */
    GOOGLE_PAY(2, "google")
    ;

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;

    public static PayOrderChannelEnum valueOf(int code) {
        if (code < 0) {
            return null;
        }

        PayOrderChannelEnum[] payOrderChannelEnums = values();
        if (payOrderChannelEnums.length <= code) {
            return null;
        }

        return payOrderChannelEnums[code];
    }
}
