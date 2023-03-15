package com.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lh
 * @date 2021/11/26 2:10 下午
 */
@Data
@TableName("withdrawal_fund")
public class WithdrawalFundDo implements Serializable {

    private static final long serialVersionUID = 5101798909091179422L;

    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    private Long appId;

    private Long userId;
    /**
     * 业务来源， 支付服务分配给接入方的
     */
    private String businessSource;
    /**
     * 业务id
     */
    private String businessId;
    /**
     * 支付订单id
     */
    private String payOrderId;
    /**
     * 支付流水id
     */
    private String payOrderFundId;
    /**
     * 状态 成功1 失败0
     */
    private Integer payStatus;
    /**
     * 支付失败code
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 支付渠道 0 微信 1支付宝
     */
    private Integer channel;
    /**
     * 钱数 分
     */
    private Long amount;

    private String payTime;

    private Date  createdAt;

    private Date updatedAt;
    /**
     * 支付业务id
     */
    private String payBusinessId;




}
