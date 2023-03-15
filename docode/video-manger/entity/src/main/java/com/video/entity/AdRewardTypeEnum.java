package com.video.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum AdRewardTypeEnum {

    /**
     * 用不上
     */
    OTHER_REWARD(0, "其他奖励"),

    /**
     * 广告点击奖励
     */
    CLICK_REWARD(1, "点击奖励"),

    /**
     * 普通广告奖励
     */
    NORMAL_REWARD(2, "普通奖励"),

    /**
     * 无奖励, 只记录
     */
    NON_REWARD(3, "无奖励"),
    ;

    @JsonValue
    @EnumValue
    private final int code;

    private final String desc;

    private final static Map<Integer, AdRewardTypeEnum> INSTANCE_MAPPER = new HashMap<Integer, AdRewardTypeEnum>();

    static {
        AdRewardTypeEnum[] adRewardTypeEnums = values();
        for (AdRewardTypeEnum adRewardTypeEnum : adRewardTypeEnums) {
            INSTANCE_MAPPER.put(adRewardTypeEnum.code, adRewardTypeEnum);
        }
    }

    public static AdRewardTypeEnum valueOf(int code) {
        return INSTANCE_MAPPER.get(code);
    }
}
