package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * pay_order
 * @author wangwei
 */
@Data
@TableName("pay_order")
public class PayOrderDO implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

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
     * 第三方预支付订单id
     */
    private String thirdPrepayId;

    /**
     * 第三方支付订单id，由我们内部生成
     */
    private String thirdOutTradeNo;

    /**
     * 订单描述
     */
    private String shortDesc;

    /**
     * 第三方支付类型, 0:JSAPI支付, 1:APP支付
     */
    private PayOrderPayTypeEnum thirdPayType;

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
     * 订单状态，0-未支付，1-支付成功，2-支付失败
     */
    private PayOrderStatusEnum status;

    /**
     * 第三方支付错误码
     */
    private String thirdErrorCode;

    /**
     * 第三方支付错误信息
     */
    private String thirdErrorMsg;

    /**
     * 预支付订单关闭时间
     */
    private Date prepayExpireTime;

    /**
     * 订单完成时间
     */
    private Date completionTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    private static final long serialVersionUID = 1L;
}