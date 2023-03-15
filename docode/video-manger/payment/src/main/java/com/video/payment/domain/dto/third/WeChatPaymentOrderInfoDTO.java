package com.video.payment.domain.dto.third;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * 微信交易订单
 */
@Data
public class WeChatPaymentOrderInfoDTO {

    /**
     * 应用ID
     */
    @JsonProperty("appid")
    private String appId;

    /**
     * 商户号
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
     * 交易类型
     */
    @JsonProperty("trade_type")
    private WeChatPaymentTradeTypeEnum tradeType;

    /**
     * 交易状态
     */
    @JsonProperty("trade_state")
    private WeChatPaymentTradeStateEnum tradeState;

    /**
     * 交易状态描述
     */
    @JsonProperty("trade_state_desc")
    private String tradeStateDesc;

    /**
     * 支付银行, 后续统计数据再换成枚举
     */
    @JsonProperty("bank_type")
    private String bankType;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 支付完成时间
     */
    @JsonProperty("success_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private Date successTime;

    /**
     * 支付者
     */
    private Payer payer;

    /**
     * 订单金额信息，当支付成功时返回该字段
     */
    private Amount amount;

    /**
     * 场景信息
     */
    @JsonProperty("scene_info")
    private SceneInfo sceneInfo;

    /**
     * 优惠功能，享受优惠时返回该字段
     */
    @JsonProperty("promotion_detail")
    private List<PromotionDetail> promotionDetail;

    /**
     * 支付者信息
     */
    @Data
    static class Payer {

        @JsonProperty("openid")
        private String openId;
    }

    /**
     * 订单金额
     */
    @Data
    static class Amount {

        /**
         * 总金额
         */
        private Integer total;

        /**
         * 用户支付金额
         */
        @JsonProperty("payer_total")
        private Integer payerTotal;

        /**
         * 货币类型
         */
        private WeChatAmountCurrencyEnum currency;

        /**
         * 用户支付币种
         */
        @JsonProperty("payer_currency")
        private WeChatAmountCurrencyEnum payperCurrency;
    }

    /**
     * 支付场景描述
     */
    @Data
    static class SceneInfo {

        /**
         * 商户端设备号
         */
        @JsonProperty("device_id")
        private String deviceId;
    }

    /**
     * 优惠功能，优惠范围
     */
    @Getter
    @AllArgsConstructor
    enum PromotionDetailScope {

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
     * 优惠功能，优惠类型
     */
    @Getter
    @AllArgsConstructor
    enum PromotionDetailType {

        /**
         * 充值
         */
        CASH(0, "充值"),

        /**
         * 预充值
         */
        NOCASH(1, "预充值")
        ;

        int code;

        String desc;
    }

    /**
     * 优惠功能，单品列表信息
     */
    @Data
    static class PromotionDetailGoodsDetail {

        /**
         * 商品编码
         */
        @JsonProperty("goods_id")
        private String goodsId;

        /**
         * 商品数量
         */
        private Integer quantity;

        /**
         * 商品单价
         */
        @JsonProperty("unit_price")
        private Integer unitPrice;

        /**
         * 商品优惠金额
         */
        @JsonProperty("discount_amount")
        private Integer discountAmount;

        /**
         * 商品备注
         */
        @JsonProperty("goods_remark")
        private String goodsRemark;
    }

    /**
     * 优惠功能
     */
    @Data
    static class PromotionDetail {

        /**
         * 券ID
         */
        @JsonProperty("coupon_id")
        private String couponId;

        /**
         * 优惠名称
         */
        private String name;

        /**
         * 优惠范围
         */
        private PromotionDetailScope scope;

        /**
         * 优惠类型
         */
        private PromotionDetailType type;

        /**
         * 优惠券面额
         */
        private Integer amount;

        /**
         * 活动ID
         */
        @JsonProperty("stock_id")
        private String stockId;

        /**
         * 微信出资，单位为分
         */
        @JsonProperty("wechatpay_contribute")
        private Integer wechatpayContribute;

        /**
         * 商户出资，单位为分
         */
        @JsonProperty("merchant_contribute")
        private Integer merchantContribute;

        /**
         * 其他出资，单位为分
         */
        @JsonProperty("other_contribute")
        private Integer otherContribute;

        /**
         * 优惠币种
         */
        private WeChatAmountCurrencyEnum currency;

        /**
         * 单品列表
         */
        @JsonProperty("goods_detail")
        private List<PromotionDetailGoodsDetail> goodsDetail;
    }
}
