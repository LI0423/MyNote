package com.video.payment.domain.dto.third;

import lombok.Builder;
import lombok.Data;

/**
 * @author wangwei
 */
@Builder
@Data
public class WeChatPaymentAmountDTO {

    /**
     * 总金额
     */
    private Integer total;

    /**
     * 人民币枚举
     */
    private WeChatAmountCurrencyEnum currency;
}
