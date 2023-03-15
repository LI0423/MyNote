package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("google_subscription")
public class GoogleSubscriptionDO implements Serializable {

    private static final long serialVersionUID = -4690814359476073831L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单id(google的)
     */
    private String orderId;

    /**
     * 三方订单id（自己生成的）
     */
    private String thirdOutTradeNo;

    /**
     * 订单交易状态
     */
    private Integer paymentStatus;

    /**
     * 包名
     */
    private String pkg;

    /**
     * 订阅id
     */
    private String subscriptionId;

    /**
     * 交易token
     */
    private String purchaseToken;

    /**
     * 过期时间
     */
    private Long expiryTimeMillis;

    /**
     * 取消原因
     */
    private Integer cancelReason;

    /**
     * 订单取消时间
     */
    private Long userCancellationTimeMillis;

    /**
     * 详情
     */
    private String extra;

    private String currency;

    /**
     * 国家码
     */
    private String country;

    private Long amount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}
