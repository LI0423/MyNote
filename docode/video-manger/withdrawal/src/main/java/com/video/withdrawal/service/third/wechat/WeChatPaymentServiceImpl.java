package com.video.withdrawal.service.third.wechat;

import com.video.withdrawal.config.prod.WeChatPaymentProperties;
import com.video.withdrawal.domain.dto.app.AppDTO;
import com.video.withdrawal.domain.dto.third.PaymentDTO;
import com.video.withdrawal.domain.dto.third.PaymentResultDTO;
import com.video.withdrawal.domain.dto.third.PaymentReturnCodeEnum;
import com.video.withdrawal.domain.dto.third.WeChatPaymentCheckNameEnum;
import com.video.withdrawal.exception.BusinessException;
import com.video.withdrawal.exception.ErrorCodeEnum;
import com.video.withdrawal.service.AppService;
import com.video.withdrawal.service.third.ThirdPaymentService;
import com.video.withdrawal.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @program: mango
 * @description: 微信支付实现类
 * @author: laojiang
 * @create: 2020-09-11 11:55
 **/
@Slf4j
@Component("WE_CHART_PAYMENT")
public class WeChatPaymentServiceImpl implements ThirdPaymentService {

    @Autowired
    private HttpClientBuilder httpClientBuilder;

    @Autowired
    private WeChatPaymentProperties weChatPaymentProperties;

    @Autowired
    private AppService appService;

    @Override
    public String generateSign(PaymentDTO paymentDTO, String appPkg) {
        // 获取app配置，顺便判断pkg的合法性
        AppDTO appDTO = appService.find(appPkg);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("app[%s]数据查询不到", appPkg));
        }
        // 对key进行排序
        TreeMap<String, Object> paramMap = new TreeMap();
        paramMap.put("mch_appid", paymentDTO.getMchAppId());
        paramMap.put("mchid", paymentDTO.getMchId());
        paramMap.put("nonce_str", paymentDTO.getNonceStr());
        paramMap.put("partner_trade_no", paymentDTO.getPartnerTradeNo());
        paramMap.put("openid", paymentDTO.getOpenId());
        paramMap.put("check_name", paymentDTO.getCheckName());
        paramMap.put("amount", paymentDTO.getAmount());
        paramMap.put("desc", paymentDTO.getDesc());
        if (paymentDTO.getDeviceInfo() != null) {
            paramMap.put("device_info", paymentDTO.getDeviceInfo());
        }
        if (paymentDTO.getSpbillCreateIp() != null) {
            paramMap.put("spbill_create_ip", paymentDTO.getSpbillCreateIp());
        }
        // 强校验姓名
        if (WeChatPaymentCheckNameEnum.FORCE_CHECK.equals(paymentDTO.getCheckName())) {
            paramMap.put("re_user_name", paymentDTO.getReUserName());
        }

        StringBuilder stringA = new StringBuilder();
        for (Map.Entry<String, Object> paramEntry : paramMap.entrySet()) {
            String key = paramEntry.getKey();
            Object value = paramEntry.getValue();
            stringA.append(key).append("=").append(value).append("&");
        }
        stringA.append("key=").append(appDTO.getWeChatApiKey());

        try {
            String sign = DigestUtils
                    .md5DigestAsHex(stringA.toString().getBytes("UTF-8"));
            return sign == null ? null : sign.toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String covertToXMLString(PaymentDTO paymentDTO) {
        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append("<xml>")
                .append("<mch_appid>").append(paymentDTO.getMchAppId()).append("</mch_appid>")
                .append("<mchid>").append(paymentDTO.getMchId()).append("</mchid>")
                .append("<nonce_str>").append(paymentDTO.getNonceStr()).append("</nonce_str>")
                .append("<partner_trade_no>").append(paymentDTO.getPartnerTradeNo()).append("</partner_trade_no>")
                .append("<openid>").append(paymentDTO.getOpenId()).append("</openid>")
                .append("<check_name>").append(paymentDTO.getCheckName()).append("</check_name>")
                .append("<amount>").append(paymentDTO.getAmount()).append("</amount>")
                .append("<desc>").append(paymentDTO.getDesc()).append("</desc>")
                .append("<sign>").append(paymentDTO.getSign()).append("</sign>");
        if (paymentDTO.getDeviceInfo() != null) {
            queryStringBuilder.append("<device_info>").append(paymentDTO.getDeviceInfo()).append("</device_info>");
        }
        if (paymentDTO.getSpbillCreateIp() != null) {
            queryStringBuilder.append("<spbill_create_ip>").append(paymentDTO.getDeviceInfo()).append("</spbill_create_ip>");
        }
        if (WeChatPaymentCheckNameEnum.FORCE_CHECK.equals(paymentDTO.getCheckName())) {
            queryStringBuilder.append("<re_user_name>").append(paymentDTO.getReUserName()).append("</re_user_name>");
        }

        queryStringBuilder.append("</xml>");
        return queryStringBuilder.toString();
    }

    private PaymentResultDTO covertToPaymentResult(String xmlStr) throws DocumentException {
        Document document = DocumentHelper.parseText(xmlStr);
        Element rootElement = document.getRootElement();

        String returnCodeStr = rootElement.elementText("return_code");
        PaymentReturnCodeEnum returnCode = PaymentReturnCodeEnum.valueOf(returnCodeStr);

        String resultCodeStr = rootElement.elementText("result_code");
        PaymentReturnCodeEnum resultCode = PaymentReturnCodeEnum.valueOf(resultCodeStr);

        PaymentResultDTO paymentResultDTO = new PaymentResultDTO();
        paymentResultDTO.setReturnCode(returnCode);
        paymentResultDTO.setReturnMsg(rootElement.elementText("return_msg"));
        paymentResultDTO.setResultCode(resultCode);

        if (PaymentReturnCodeEnum.SUCCESS.equals(returnCode)
                && PaymentReturnCodeEnum.SUCCESS.equals(resultCode)) {
            paymentResultDTO.setMchAppId(rootElement.elementText("mch_appid"));
            paymentResultDTO.setMchId(rootElement.elementText("mchid"));
            paymentResultDTO.setDeviceInfo(rootElement.elementText("device_info"));
            paymentResultDTO.setNonceStr(rootElement.elementText("nonce_str"));
            paymentResultDTO.setPartnerTradeNo(rootElement.elementText("partner_trade_no"));
            paymentResultDTO.setPaymentNo(rootElement.elementText("payment_no"));
            paymentResultDTO.setPaymentTime("payment_time");
        } else {
            paymentResultDTO.setErrCode(rootElement.elementText("err_code"));
            paymentResultDTO.setErrCodeDes(rootElement.elementText("err_code_des"));
        }
        return paymentResultDTO;
    }

    private String postPatmentAPI(String url, String queryStr, byte[] certContent, String password) throws IOException,
            UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String resultBody = HttpClientUtils.doPosts(url, queryStr.getBytes("UTF-8"), certContent, password);
        return resultBody;
    }

    @Override
    public PaymentResultDTO pay(PaymentDTO paymentDTO, String appPkg) {

        AppDTO appDTO = appService.find(appPkg);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("app[%s]数据查询不到", appPkg));
        }

        String queryStr = covertToXMLString(paymentDTO);

        String paymentUrl = weChatPaymentProperties.getPaymentToUserUrl();

        String resultStr = null;
        try {
            byte[] certContent = Base64.decodeBase64(appDTO.getWeChatApiCert());
            String password = appDTO.getWeChatMchid(); // 证书的密码(钥)是商户id
            resultStr = postPatmentAPI(paymentUrl, queryStr, certContent, password);
            log.info("payment to user, open id:{}, result:{}", paymentDTO.getOpenId(), resultStr);
        } catch (Exception e) {
            log.error("Post patment api error.", e);
            throw new BusinessException(ErrorCodeEnum.THIRD_RESOURCE_ERROR, e.getMessage());
        }

        // 请求第三方接口获取返回值
        PaymentResultDTO resultDTO = null;
        try {
            resultDTO = covertToPaymentResult(resultStr);
        } catch (DocumentException e) {
            log.error("Covert to payment result error.", e);
            throw new BusinessException(ErrorCodeEnum.THIRD_RESOURCE_ERROR, e.getMessage());
        }

        return resultDTO;
    }

}
