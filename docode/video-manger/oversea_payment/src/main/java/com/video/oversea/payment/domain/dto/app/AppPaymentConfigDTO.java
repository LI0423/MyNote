package com.video.oversea.payment.domain.dto.app;

import lombok.Data;

@Data
public class AppPaymentConfigDTO {

    private Long appId;

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
