package com.video.payment.domain.dto.payment;

import com.video.entity.PayOrderChannelEnum;
import com.video.entity.PayOrderPayTypeEnum;
import lombok.Data;

/**
 * 预支付接口请求体
 */
@Data
public class PrepayRequestDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 现金数
     */
    private Long amount;

    /**
     * 支付类型
     */
    private PayOrderPayTypeEnum payType;

    /**
     * 渠道
     */
    private PayOrderChannelEnum channel;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 订单描述
     */
    private String orderDesc;

    /**
     * 通知url
     */
    private String notifyUrl;
}
