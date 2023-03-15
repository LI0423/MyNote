package com.video.payment.domain.dto.payment;

import lombok.Data;

@Data
public class PayOrderDownstreamNotifyDTO {

    private Long id;

    /**
     * 支付订单id
     */
    private Long payOrderId;

    /**
     * 下游通知url
     */
    private String notifyUrl;
}
