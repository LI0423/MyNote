package com.video.payment.domain.dto.refund;

import lombok.Data;

/**
 * 申请退款请求体
 */
@Data
public class RefundRequestDTO {

    /**
     * 交易单id
     */
    private Long payOrderId;

    /**
     * 现金数(得少于pay order id对应的订单金额), 不退订单全款的时候传递此字段, 不传此字段则全款退回
     */
    private Integer amount;

    /**
     * 记录
     */
    private String reason;

    /**
     * 通知url
     */
    private String notifyUrl;
}
