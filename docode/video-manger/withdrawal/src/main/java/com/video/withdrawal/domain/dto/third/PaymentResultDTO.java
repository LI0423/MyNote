package com.video.withdrawal.domain.dto.third;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 */
@Data
public class PaymentResultDTO implements Serializable {

    private static final long serialVersionUID = 6733110408650478108L;

    private PaymentReturnCodeEnum returnCode;

    private String returnMsg;

    private String mchAppId;

    private String mchId;

    private String deviceInfo;

    private String nonceStr;

    private PaymentReturnCodeEnum resultCode;

    private String errCode;

    private String errCodeDes;

    private String partnerTradeNo;

    private String paymentNo;

    private String paymentTime;

}
