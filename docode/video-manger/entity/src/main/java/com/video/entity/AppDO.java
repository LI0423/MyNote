package com.video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: app
 * @author: laojiang
 * @create: 2020-09-01 15:22
 **/
@Data
@TableName("app")
public class AppDO implements Serializable {

    private static final long serialVersionUID = -6854103728951220437L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用名字
     */
    private String name;
    /**
     * 应用包名
     */
    private String pkg;
    /**
     * 微信支付商户号
     */
    private String weChatMchid;
    /**
     * 微信appId
     */
    private String weChatAppId;
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
     * 金币兑换比例
     */
    private Long goldCoinRatio;
    /**
     * 是否可以提现
     */
    private Boolean hasWechatWithdrawal;

    /**
     * 是否开启debug模式
     */
    private Boolean isDebugModel;

    /**
     * 是否开启青少年模式
     */
    private Boolean hasContentLevel;

    /**
     * app下是否需要匿名用户
     */
    private Boolean needAnonymousUser;

    /**
     * 应用的版本号，多个用逗号隔开
     */
    private String appVns;

    /**
     * 应用的广告位，多个用逗号隔开
     */
    private String sids;

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
     * 短信服务-区域id
     */
    private String smsRegionId;

    /**
     * 短信服务-sdk身份验证用key
     */
    private String smsAccessKeyId;

    /**
     * 短信服务-sdk身份验证用key对应密码
     */
    private String smsAccessSecret;

    /**
     * 短信服务-短信上的app签名
     *
     * 例: 消息 "【芒果视频】短信验证码: xxx" 中的【芒果视频】
     */
    private String smsSignName;

    /**
     * 短信服务-短信模板代码
     */
    private String smsTemplateCode;
}
