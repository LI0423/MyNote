package com.video.withdrawal.domain.dto.third;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author admin
 */
@Data
public class PaymentDTO implements Serializable {
    private static final long serialVersionUID = 2757231808766309751L;

    /**
     * 商户appid
     */
    @NotNull
    private String mchAppId;

    /**
     * 商户号
     */
    @NotNull
    private String mchId;

    private String deviceInfo;

    @NotNull
    private String nonceStr;

    /**
     * 签名
     */
    @NotNull
    private String sign;

    /**
     * 商户订单号
     */
    @NotNull
    private String partnerTradeNo;

    @NotNull
    private String openId;

    @NotNull
    private PaymentCheckNameEnum checkName;

    private String reUserName;

    @NotNull
    private Long amount;

    @NotNull
    private String desc;

    private String spbillCreateIp;
}
