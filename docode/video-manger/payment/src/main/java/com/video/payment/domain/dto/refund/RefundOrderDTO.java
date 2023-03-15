package com.video.payment.domain.dto.refund;

import com.baomidou.mybatisplus.annotation.TableId;
import com.video.entity.PayOrderChannelEnum;
import com.video.entity.PayOrderPayTypeEnum;
import com.video.entity.RefundOrderStatusEnum;
import lombok.Data;

@Data
public class RefundOrderDTO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 订单id
     */
    private Long payOrderId;

    /**
     * 支付渠道, 0:微信, 1:支付宝
     */
    private PayOrderChannelEnum channel;

    /**
     * 第三方订单id
     */
    private String thirdTransactionId;

    /**
     * 第三方商户id
     */
    private String thirdMchid;

    /**
     * 第三方appid
     */
    private String thirdAppId;

    /**
     * 第三方支付订单id，由我们内部生成
     */
    private String thirdOutTradeNo;

    /**
     * 第三方退款订单id，由我们内部生成
     */
    private String thirdOutRefundNo;

    /**
     * 第三方支付类型, 0:JSAPI支付, 1:APP支付
     */
    private PayOrderPayTypeEnum thirdPayType;

    /**
     * 第三方退款入账账户, 三方返回的信息
     */
    private String thirdUserReceivedAccount;

    /**
     * 我们平台的appid
     */
    private Long appId;

    /**
     * 我们平台的用户id
     */
    private Long userId;

    /**
     * 支付类型，业务字段，不同的app自己定义自己的支付类型，如果不需要区分类型可以不填
     */
    private Integer type;

    /**
     * 支付金额(单位：分)
     */
    private Long amount;

    /**
     * 订单状态，0-未完成退款，1-退款成功，2-退款失败
     */
    private RefundOrderStatusEnum status;

    /**
     * 第三方退款错误码
     */
    private String thirdErrorCode;

    /**
     * 第三方退款错误信息
     */
    private String thirdErrorMsg;
}
