package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 支付订单相关的下游通知信息
 * pay_order_downstream_merchant
 * @author wangwei
 */
@Data
@TableName("pay_order_downstream_notify")
public class PayOrderDownstreamNotifyDO {

    @TableId
    private Long id;

    /**
     * 支付订单id
     */
    private Long payOrderId;

    /**
     * 下游通知url
     */
    private String notifyUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}
