package com.video.payment.domain.dto.app;

import lombok.Data;

@Data
public class AppDTO {

    /**
     * 我们平台的appid
     */
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
     * 微信公众平台密钥
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
}
