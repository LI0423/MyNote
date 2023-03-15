package com.video.payment.domain.dto.third.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.video.payment.domain.dto.third.WeChatAmountCurrencyEnum;
import lombok.Data;

import java.util.List;

/**
 * 订单金额信息
 */
@Data
public class AmountDTO {

    /**
     * 订单金额, 单位:分
     */
    private Integer total;

    /**
     * 退款金额, 单位:分
     */
    private Integer refund;

    /**
     * 退款出资账户及金额
     */
    private List<AccountAndAmountDTO> from;

    /**
     * 用户支付金额, 单位:分
     */
    @JsonProperty("payer_total")
    private Integer payerTotal;

    /**
     * 用户退款金额
     */
    @JsonProperty("payer_refund")
    private Integer payerRefund;

    /**
     * 应结退款金额
     */
    @JsonProperty("settlement_refund")
    private Integer settlementRefund;

    /**
     * 应结订单金额
     */
    @JsonProperty("settlement_total")
    private Integer settlementTotal;

    /**
     * 优惠退款金额
     */
    @JsonProperty("discount_refund")
    private Integer discountRefund;

    /**
     * 退款币种
     */
    private WeChatAmountCurrencyEnum currency;

}
