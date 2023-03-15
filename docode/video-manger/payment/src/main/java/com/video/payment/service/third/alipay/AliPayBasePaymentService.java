package com.video.payment.service.third.alipay;

import com.alipay.api.*;
import com.video.payment.config.prod.AliPayPaymentProperties;
import com.video.payment.domain.dto.app.AppDTO;
import com.video.payment.domain.dto.app.AppPaymentConfigDTO;
import com.video.payment.exception.BusinessException;
import com.video.payment.exception.ErrorCodeEnum;
import com.video.payment.service.AppService;
import com.video.payment.service.UserAuthService;
import com.video.payment.service.third.ThirdBasePaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
public abstract class AliPayBasePaymentService extends ThirdBasePaymentService {

    @Autowired
    protected AliPayPaymentProperties aliPayPaymentProperties;

    @Autowired
    protected AppService appService;

    @Autowired
    protected UserAuthService userAuthService;

    protected <T extends AlipayResponse> T keyExecute(Long appId, AlipayRequest<T> request) {

        if (Objects.isNull(request)) {
            throw new IllegalArgumentException("Param request not found!");
        }

        AppPaymentConfigDTO appPaymentConfigDTO = appService.findAppPaymentConfig(appId);
        if (appPaymentConfigDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("app payment config[%s]数据查询不到", appId));
        }

        String aliPayAppId = appPaymentConfigDTO.getAliPayAppId();
        String aliPayMerchantPrivateKey = appPaymentConfigDTO.getAliPayMerchantPrivateKey();
        String aliPayPublicKey = appPaymentConfigDTO.getAliPayPublicKey();
        if (StringUtils.isBlank(aliPayAppId)
                || StringUtils.isBlank(aliPayMerchantPrivateKey)
                || StringUtils.isBlank(aliPayPublicKey)) {
            throw new BusinessException(ErrorCodeEnum.RESOURCE_NOT_FOUND,
                    String.format("ali_pay_appid[%s]、ali_pay_merchant_private_key[%s]、ali_pay_public_key[%s],数据查询不到",
                            aliPayAppId, aliPayMerchantPrivateKey, aliPayPublicKey));
        }

        // 1. 创建AlipayClient实例
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayPaymentProperties.getServerUrl(),
                aliPayAppId, aliPayMerchantPrivateKey, AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8,
                aliPayPublicKey, AlipayConstants.SIGN_TYPE_RSA2);

        // 2. 创建使用的Open API对应的Request请求对象
        T response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.error("Ali ay client request execute error.", e);
            return null;
        }

        return response;
    }

    private CertAlipayRequest buildCertConfig(String aliPayAppId, String aliPayMerchantPrivateKey, String aliPayAppCert,
                                              String aliPayPublicCert, String aliPayRootCert) {
        CertAlipayRequest certParams = new CertAlipayRequest();
        certParams.setServerUrl(aliPayPaymentProperties.getServerUrl());
        certParams.setAppId(aliPayAppId);
        certParams.setPrivateKey(aliPayMerchantPrivateKey);
        certParams.setCharset(AlipayConstants.CHARSET_UTF8);
        certParams.setFormat(AlipayConstants.FORMAT_JSON);
        certParams.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
        certParams.setCertContent(aliPayAppCert);
        certParams.setAlipayPublicCertContent(aliPayPublicCert);
        certParams.setRootCertContent(aliPayRootCert);
        return certParams;
    }

//    protected <T extends AlipayResponse> T certificateExecute(Long appId, AlipayRequest<T> request) {
//        if (Objects.isNull(request)) {
//            throw new IllegalArgumentException("Param request not found!");
//        }
//
//        AppPaymentConfigDTO appPaymentConfigDTO = appService.findAppPaymentConfig(appId);
//        if (appPaymentConfigDTO == null) {
//            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
//                    String.format("app payment config[%s]数据查询不到", appId));
//        }
//
//        String aliPayAppId = appPaymentConfigDTO.getAliPayAppId();
//        String aliPayMerchantPrivateKey = appPaymentConfigDTO.getAliPayMerchantPrivateKey();
//        String aliPayAppCert = appPaymentConfigDTO.getAliPayAppCert();
//        String aliPayPublicCert = appPaymentConfigDTO.getAliPayPublicCert();
//        String aliPayRootCert = appPaymentConfigDTO.getAliPayRootCert();
//
//        if (StringUtils.isBlank(aliPayAppId)
//                || StringUtils.isBlank(aliPayMerchantPrivateKey)
//                || StringUtils.isBlank(aliPayAppCert)
//                || StringUtils.isBlank(aliPayPublicCert)
//                || StringUtils.isBlank(aliPayRootCert)) {
//            throw new BusinessException(ErrorCodeEnum.RESOURCE_NOT_FOUND,
//                    String.format("ali_pay_appid[%s]、ali_pay_merchant_private_key[%s]、ali_pay_app_cert[%s]、" +
//                                    "ali_pay_public_cert[%s]、ali_pay_root_cert[%s],数据查询不到",
//                            aliPayAppId, aliPayMerchantPrivateKey, aliPayAppCert, aliPayPublicCert, aliPayRootCert));
//        }
//
//        CertAlipayRequest certConfig =
//                buildCertConfig(aliPayAppId, aliPayMerchantPrivateKey, aliPayAppCert, aliPayPublicCert, aliPayRootCert);
//
//        try {
//            AlipayClient alipayClient = new DefaultAlipayClient(certConfig);
//            return alipayClient.certificateExecute(request);
//        } catch (AlipayApiException e) {
//            log.error("Build ali pay client error.", e);
//            throw new BusinessException(ErrorCodeEnum.THIRD_PAY_ERROR);
//        }
//    }

    protected <T extends AlipayResponse> T certificateExecute(String thirdAppId, AlipayRequest<T> request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException("Param request not found!");
        }

        AppDTO appDTO = appService.findByAliAppId(thirdAppId);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("ali payment config[%s]数据查询不到", thirdAppId));
        }

        String aliPayAppId = appDTO.getAliPayAppId();
        String aliPayMerchantPrivateKey = appDTO.getAliPayMerchantPrivateKey();
        String aliPayAppCert = appDTO.getAliPayAppCert();
        String aliPayPublicCert = appDTO.getAliPayPublicCert();
        String aliPayRootCert = appDTO.getAliPayRootCert();

        if (StringUtils.isBlank(aliPayAppId)
                || StringUtils.isBlank(aliPayMerchantPrivateKey)
                || StringUtils.isBlank(aliPayAppCert)
                || StringUtils.isBlank(aliPayPublicCert)
                || StringUtils.isBlank(aliPayRootCert)) {
            throw new BusinessException(ErrorCodeEnum.RESOURCE_NOT_FOUND,
                    String.format("ali_pay_appid[%s]、ali_pay_merchant_private_key[%s]、ali_pay_app_cert[%s]、" +
                                    "ali_pay_public_cert[%s]、ali_pay_root_cert[%s],数据查询不到",
                            aliPayAppId, aliPayMerchantPrivateKey, aliPayAppCert, aliPayPublicCert, aliPayRootCert));
        }

        CertAlipayRequest certConfig =
                buildCertConfig(aliPayAppId, aliPayMerchantPrivateKey, aliPayAppCert, aliPayPublicCert, aliPayRootCert);

        try {
            AlipayClient alipayClient = new DefaultAlipayClient(certConfig);
            return alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            log.error("Build ali pay client error.", e);
            throw new BusinessException(ErrorCodeEnum.THIRD_PAY_ERROR);
        }
    }
}
