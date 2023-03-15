package com.video.withdrawal.domain.dto.third;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WeChatPaymentQueryDTO {
    // 商户appid
    @NotNull
    private String mchAppid;

    // 商户号
    @NotNull
    private String mchId;

    private String deviceInfo;

    @NotNull
    private String nonceStr;

    @NotNull
    // 签名
    private String sign;

    @NotNull
    private String partnerTradeNo;

    @NotNull
    private String openId;

    @NotNull
    private WeChatPaymentCheckNameEnum checkName;

    private String reUserName;

    @NotNull
    private Long amount;

    @NotNull
    private String desc;

    private String spbillCreateIp;
}
