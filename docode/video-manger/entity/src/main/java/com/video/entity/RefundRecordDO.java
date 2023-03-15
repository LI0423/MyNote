package com.video.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * refund_record
 * @author wangwei
 */
@Data
@TableName("refund_record")
public class RefundRecordDO implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 支付订单id
     */
    private Long refundOrderId;

    /**
     * 支付结果, 1-退款成功，2-退款失败
     */
    private RefundRecordStatusEnum resultStatus;

    /**
     * 第三方支付错误码
     */
    private String thirdErrorCode;

    /**
     * 第三方支付错误信息
     */
    private String thirdErrorMsg;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件摘要
     */
    private String summary;

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