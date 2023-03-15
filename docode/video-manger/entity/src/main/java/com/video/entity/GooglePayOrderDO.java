package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("google_pay_order")
public class GooglePayOrderDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 我们平台的订单号
     */
    private String orderId;

    /**
     * 第三方平台的订单号
     */
    private String thirdOrderId;

    /**
     * 订单支付状态
     */
    private PayOrderStatusEnum status;

    /**
     * 交易金额
     */
    private Long amount;

    /**
     *
     */
    private String currency;

    /**
     * 支付渠道
     */
    private PayOrderChannelEnum channel;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 扩充
     */
    private String extra;

    /**
     * pkg
     */
    private String pkg;

    /**
     * 国家码
     */
    private String country;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}
