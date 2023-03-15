package com.video.payment.domain.dto.refund;

import com.video.entity.RefundRecordStatusEnum;
import lombok.Data;

@Data
public class RefundNotifyMessageDTO {

    /**
     * 退款订单id
     */
    private Long refundOrderId;

    /**
     * 退款订单id
     */
    private Long payOrderId;

    /**
     * 支付状态
     */
    private RefundRecordStatusEnum status;

    /**
     * 现金数
     */
    private Long amount;
}
