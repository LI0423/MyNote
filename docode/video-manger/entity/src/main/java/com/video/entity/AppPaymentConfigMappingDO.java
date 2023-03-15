package com.video.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("app_payment_config_mapping")
public class AppPaymentConfigMappingDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long appId;

    /**
     * 商户类型
     * 0：微信
     * 1：支付宝
     */
    private Integer merchantType;

    /**
     * 商户id，对应微信和支付宝商户信息表中的id
     */
    private Long merchantId;

    /**
     * 用途
     * 0：支付
     * 1：提现
     */
    private Integer mappingType;

    /**
     * 上下线状态
     * 0：上线
     * 1：下线
     */
    private Integer status;

    /**
     * 添加时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 添加人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 最近修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifyTime;

    /**
     * 最近修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastModifyBy;
}
