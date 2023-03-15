package com.video.withdrawal.domain.dto.app;

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
}
