package com.video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: app_payment_config
 * @author: wangwei
 * @create: 2021-11-14 20:34
 **/
@Data
@TableName("app_payment_config")
public class AppPaymentConfigDO implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long appId;

    private String pkg;

    /**
     * 支付宝分配给开发者的应用ID
     */
    private String aliPayAppId;

    /**
     * 支付宝应用私钥
     */
    private String aliPayMerchantPrivateKey;

    /**
     * 支付宝公钥
     */
    private String aliPayPublicKey;

    /**
     * 应用公钥证书
     */
    private String aliPayAppCert;

    /**
     * 支付宝公钥证书
     */
    private String aliPayPublicCert;

    /**
     * 支付宝根证书
     */
    private String aliPayRootCert;

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

    /**
     * 逻辑删除字段
     * 0:已删除
     * 1:未删除
     */
    private Integer deleted;

    /**
     * 冻结状态
     * 0：未冻结
     * 1：冻结
     */
    private Integer frozenStatus;

    /**
     * 实体名称，商户名称
     */
    private String merchantName;
}
