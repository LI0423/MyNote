package com.video.payment.domain.dto.third.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 退款商品信息
 */
@Data
public class GoodsDetailDTO {

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
