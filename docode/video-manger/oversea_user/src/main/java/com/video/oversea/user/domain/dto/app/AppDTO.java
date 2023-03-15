package com.video.oversea.user.domain.dto.app;

import lombok.Data;

@Data
public class AppDTO {

    /**
     * 我们平台的appid
     */
    private Long id;
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
     * 金币兑换比例
     */
    private Long goldCoinRatio;
    /**
     * 是否可以提现
     */
    private Boolean hasWechatWithdrawal;

    /**
     * app下是否需要匿名用户
     */
    private Boolean needAnonymousUser;

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
