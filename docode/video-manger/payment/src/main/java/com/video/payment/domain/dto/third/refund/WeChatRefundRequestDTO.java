package com.video.payment.domain.dto.third.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeChatRefundRequestDTO {

    /**
     * 订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 退款订单号
     */
    @JsonProperty("out_refund_no")
    private String outRefundNo;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 通知接口url
     */
    @JsonProperty("notify_url")
    private String notifyUrl;

    /**
     * 退款资金来源
     */
    @JsonProperty("funds_account")
    private FundsAccountEnum fundsAccount;

    /**
     * 金额信息
     */
    private AmountDTO amount;

    /**
     * 退款商品
     */
    @JsonProperty("goods_detail")
    private GoodsDetailDTO goodsDetail;
}
