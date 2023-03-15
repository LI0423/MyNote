package com.video.withdrawal.domain.dto.third;

import lombok.Data;

@Data
public class WeChatPaymentResultDTO {

    private WeChatPaymentReturnCodeEnum returnCode;

    private String returnMsg;

    private String mchAppId;

    private String mchId;

    private String deviceInfo;

    private String nonceStr;

    private WeChatPaymentReturnCodeEnum resultCode;

    private String errCode;

    private String errCodeDes;

    private String partnerTradeNo;

    private String paymentNo;

    private String paymentTime;

}
