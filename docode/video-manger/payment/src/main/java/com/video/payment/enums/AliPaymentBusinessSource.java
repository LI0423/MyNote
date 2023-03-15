package com.video.payment.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author lh
 * @date 2021/11/26 5:19 下午
 */
@Getter
public enum AliPaymentBusinessSource {
    TIAN_TIAN_BAI_TAO("TTBT", "天天白淘"),
    GLOD_COIN_SYSTEM("GCS", "金币系统")

    ;

    public String businessSource;
    public String business;


    AliPaymentBusinessSource(String source, String business) {
        this.business = business;
        this.businessSource = source;
    }

    public static  AliPaymentBusinessSource getSource(String businessSource) {
        return Arrays.stream(AliPaymentBusinessSource.values()).filter(e -> e.getBusinessSource().equals(businessSource)).findFirst().orElse(null);
    }
}

