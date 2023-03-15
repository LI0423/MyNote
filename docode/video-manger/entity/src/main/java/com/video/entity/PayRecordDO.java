package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * pay_record
 * @author wangwei
 */
@Data
@TableName("pay_record")
public class PayRecordDO implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 支付订单id
     */
    private Long payOrderId;

    /**
     * 支付结果, 1-支付成功，2-支付失败
     */
    private PayRecordStatusEnum resultStatus;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件摘要
     */
    private String summary;

    /**
     * 第三方支付错误码
     */
    private String thirdErrorCode;

    /**
     * 第三方支付错误信息
     */
    private String thirdErrorMsg;

    /**
     * 结果详情
     */
    private String result;

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