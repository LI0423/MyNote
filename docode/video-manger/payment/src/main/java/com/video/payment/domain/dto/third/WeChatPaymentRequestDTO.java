package com.video.payment.domain.dto.third;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author wangwei
 */
@Builder
@Data
public class WeChatPaymentRequestDTO {

    /**
     * 微信appid
     */
    @JsonProperty("appid")
    private String appId;

    /**
     * 微信商户号
     */
    @JsonProperty("mchid")
    private String mchId;

    /**
     * 订单描述
     */
    private String description;

    /**
     * 订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 订单失效时间
     */
    @JsonProperty("time_expire")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private Date timeExpire;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 通知接口url
     */
    @JsonProperty("notify_url")
    private String notifyUrl;

    /**
     * 订单优惠标记
     * TODO: 暂时用不上
     */
    @JsonProperty("goods_tag")
    private String goodsTag;

    /**
     * 订单金额信息
     */
    private WeChatPaymentAmountDTO amount;
}
