package com.video.payment.domain.dto.payment;

import com.video.entity.PayRecordStatusEnum;
import lombok.Data;

/**
 * 给下游商户的支付通知
 */
@Data
public class PayNotifyMessageDTO {

    /**
     * 支付订单id
     */
    private Long payOrderId;

    /**
     * 支付状态
     */
    private PayRecordStatusEnum status;

    /**
     * 业务类型
     */
    private Integer type;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 应用包名
     *
     */
    private String pkg;

    /**
     * 现金数
     */
    private Long amount;
}
