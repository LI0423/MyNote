package com.video.payment.domain.dto.third.refund;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WeChatNotifyRefundOrderInfoDTO {

    /**
     * 直连商户号
     */
    @JsonProperty("mchid")
    private String mchId;

    /**
     * 商户订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 微信支付订单号
     */
    @JsonProperty("transaction_id")
    private String transactionId;

    /**
     * 商户退款单号
     */
    @JsonProperty("out_refund_no")
    private String outRefundNo;

    /**
     * 微信支付退款单号
     */
    @JsonProperty("refund_id")
    private String refundId;

    /**
     * 退款状态
     */
    @JsonProperty("refund_status")
    private RefundStatusEnum refundStatus;

    /**
     * 退款成功时间
     */
    @JsonProperty("success_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private Date successTime;

    /**
     * 退款入账账户
     */
    @JsonProperty("user_received_account")
    private String userReceivedAccount;

    /**
     * 金额信息
     */
    private Amount amount;

    /**
     * 金额信息类
     */
    @Data
    static class Amount {

        /**
         * 订单金额
         */
        private Integer total;

        /**
         * 退款金额
         */
        private Integer refund;

        /**
         * 用户支付金额
         */
        @JsonProperty("payer_total")
        private Integer payerTotal;

        /**
         * 用户退款金额
         */
        @JsonProperty("payer_refund")
        private Integer payerRefund;
    }
}
