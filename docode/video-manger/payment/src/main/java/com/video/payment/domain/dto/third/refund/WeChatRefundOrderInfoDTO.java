package com.video.payment.domain.dto.third.refund;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Data
public class WeChatRefundOrderInfoDTO {

    /**
     * 微信支付退款单号
     */
    @JsonProperty("refund_id")
    private String refundId;

    /**
     * 商户退款单号
     */
    @JsonProperty("out_refund_no")
    private String outRefundNo;

    /**
     * 微信支付订单号
     */
    @JsonProperty("transaction_id")
    private String transactionId;

    /**
     * 商户订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 退款渠道
     */
    private ChannelEnum channel;

    /**
     * 退款入账账户
     */
    @JsonProperty("user_received_account")
    private String userReceivedAccount;

    /**
     * 退款成功时间
     */
    @JsonProperty("success_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private Date successTime;

    /**
     * 退款创建时间
     */
    @JsonProperty("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private Date createTime;

    /**
     * 退款状态
     */
    private RefundStatusEnum status;

    /**
     * 资金账户
     */
    @JsonProperty("funds_account")
    private FundsAccountEnum fundsAccount;

    /**
     * 金额信息
     */
    private AmountDTO amount;

    /**
     * 优惠退款信息
     */
    @JsonProperty("promotion_detail")
    private List<PromotionDetail> promotionDetail;

    /**
     * 优惠范围
     */
    @Getter
    @AllArgsConstructor
    enum PromotionScope {

        /**
         * 全场代金券
         */
        GLOBAL(0, "全场代金券"),

        /**
         * 单品优惠
         */
        SINGLE(1, "单品优惠")
        ;

        int code;

        String desc;
    }

    /**
     * 优惠类型
     */
    @Getter
    @AllArgsConstructor
    enum PromotionType {

        /**
         * 代金券
         */
        COUPON(0, "代金券"),

        /**
         * 优惠券
         */
        DISCOUNT(1, "优惠券")
        ;

        int code;

        String desc;
    }

    /**
     * 商品列表
     */
    @Data
    static class PromotionGoodsDetail {

        /**
         * 商户侧商品编码
         */
        @JsonProperty("merchant_goods_id")
        private String merchantGoodsId;

        /**
         * 微信侧商品编码
         */
        @JsonProperty("wechatpay_goods_id")
        private String wechatpayGoodsId;

        /**
         * 商品名称
         */
        @JsonProperty("goods_name")
        private String goodsName;

        /**
         * 商品单价
         */
        @JsonProperty("unit_price")
        private Integer unitPrice;

        /**
         * 商品退款金额
         */
        @JsonProperty("refund_amount")
        private Integer refundAmount;

        /**
         * 商品退货数量
         */
        @JsonProperty("refund_quantity")
        private Integer refundQuantity;
    }

    /**
     * 优惠退款信息
     */
    @Data
    static class PromotionDetail {

        /**
         * 券ID
         */
        @JsonProperty("promotion_id")
        private String promotionId;

        /**
         * 优惠范围
         */
        private PromotionScope scope;

        /**
         * 优惠类型
         */
        private PromotionType type;

        /**
         * 优惠券面额, 单位:分
         */
        private Integer amount;

        /**
         * 优惠退款金额, 单位:分
         */
        @JsonProperty("refund_amount")
        private Integer refundAmount;

        /**
         * 商品列表
         */
        @JsonProperty("goods_detail")
        private PromotionGoodsDetail goodsDetail;
    }
}
