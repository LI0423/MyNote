package com.video.payment.domain.dto.third;

import lombok.Data;

/**
 * @author lh
 * @date 2021/11/26 10:48 上午
 */
@Data
public class AliPaymentResultDTO {
    /**
     * 业务订单
     */
    private String outBizNo;
    /**
     * 支付订单
     */
    private String orderId;
    /**
     * 支付订单流水
     */
    private String payFundOrderId;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 支付时间
     */
    private String transDate;

    private String subCode;

    private String subMsg;



}
