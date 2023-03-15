package com.video.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("app_payment_wechat_config")
public class AppPaymentWeChatConfigDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long appId;

    private String pkg;

    /**
     * 微信appId
     */
    private String weChatAppId;
    /**
     * 微信支付商户号
     */
    private String weChatMchid;
    /**
     * 微信开放平台密钥
     */
    private String weChatAppSecret;
    /**
     * 微信支付api密钥key
     */
    private String weChatApiKey;
    /**
     * 微信支付api证书(base64编码后)
     */
    private String weChatApiCert;

    /**
     * 微信支付平台商户私钥
     */
    private String weChatMchPrivateKey;

    /**
     * 微信平台商户证书序列号
     */
    private String weChatMchCertSerialNo;

    /**
     * 微信API v3 Key
     */
    private String weChatApiV3Key;

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
