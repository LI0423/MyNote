package com.video.oversea.payment.domain.dto.third;

import lombok.Data;

@Data
public class GooglePayNotifyMessageDTO {

    private String orderId;

    private String thirdOutTradeNo;

    private Integer tradeState;

    private Long expiryTimeMillis;

    private Long startTimeMillis;
}
