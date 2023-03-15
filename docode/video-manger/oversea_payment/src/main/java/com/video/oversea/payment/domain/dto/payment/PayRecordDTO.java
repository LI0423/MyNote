package com.video.oversea.payment.domain.dto.payment;

import com.video.entity.PayRecordStatusEnum;
import lombok.Data;

@Data
public class PayRecordDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 支付订单id
     */
    private Long payOrderId;

    /**
     * 支付结果, 1-支付成功，2-支付失败
     */
    private PayRecordStatusEnum resultStatus;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件摘要
     */
    private String summary;

    /**
     * 第三方支付错误码
     */
    private String thirdErrorCode;

    /**
     * 第三方支付错误信息
     */
    private String thirdErrorMsg;

    /**
     * 结果详情
     */
    private String result;
}
