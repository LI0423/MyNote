package com.video.payment.service.third.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultSigner;
import com.alipay.api.Signer;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.AntCertificationUtil;
import com.alipay.api.internal.util.RequestParametersHolder;
import com.alipay.api.internal.util.codec.Base64;
import com.alipay.api.internal.util.json.JSONWriter;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.video.entity.*;
import com.video.payment.constant.BusinessConstants;
import com.video.payment.constant.SimpleDateFormatConstants;
import com.video.payment.domain.dto.app.AppDTO;
import com.video.payment.domain.dto.payment.PayRecordDTO;
import com.video.payment.domain.dto.payment.PaymentQuerySignDTO;
import com.video.payment.domain.dto.user.UserAuthDTO;
import com.video.payment.domain.dto.withdrawal.WithdrawalQueryDTO;
import com.video.payment.exception.BusinessException;
import com.video.payment.exception.ErrorCodeEnum;
import com.video.payment.service.third.ThirdPaymentService;
import com.video.payment.util.SimpleDateUtils;
import com.video.payment.util.URLCodecUtils;
import com.video.payment.domain.dto.payment.NotifyResultDTO;
import com.video.payment.domain.dto.payment.PayOrderDTO;
import com.video.payment.domain.dto.third.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.*;

@Slf4j
@Service("ALI_PAY_PAYMENT")
public class AliPayPaymentServiceImpl extends AliPayBasePaymentService implements ThirdPaymentService<AliPayNotifyMessageDTO, Map<String, String>> {

    @Qualifier("ALI_PAY_PAYMENT")
    @Autowired
    private AliPayPaymentServiceImpl aliPayPaymentService;

    @Override
    public PayOrderDTO prepay(Long userId, String pkg, Long amount, PayOrderPayTypeEnum payType, Integer businessType, String orderDesc, String notifyUrl) {

        if (StringUtils.isBlank(notifyUrl)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR, "必要请求参数notifyUrl没找到");
        }

        try {
            URL notifyURL = new URL(notifyUrl);
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR,
                    String.format("请求参数notifyUrl格式错误, notifyUrl:[%s]", notifyUrl));
        }

        AppDTO appDTO = appService.findRand(pkg, PayOrderChannelEnum.ALI_PAY, 0);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("pkg[%s]数据查询不到", pkg));
        }

//        AppPaymentConfigDTO appPaymentConfigDTO = appService.findAppPaymentConfig(appId);
//        if (appPaymentConfigDTO == null) {
//            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
//                    String.format("app payment config[%s]数据查询不到", appId));
//        }

        UserAuthDTO userAuthDTO = userAuthService.findByUserId(userId);
        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
            throw new BusinessException(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND,
                    String.format("user_auth[%s]数据查询不到", userId));
        }

        PayOrderDTO payOrderDTO = save(StringUtils.EMPTY, appDTO.getAliPayAppId(), payType, appDTO.getId(),
                PayOrderChannelEnum.ALI_PAY, userId, businessType, amount, SimpleDateUtils.addMinute(new Date(),
                        aliPayPaymentProperties.getDefaultOrderTimeExpireMinute()), orderDesc, notifyUrl);

//        payOrderDTO.setNotifyUrl(aliPayPaymentProperties.getPayNotifyApiUrl());

        return payOrderDTO;
    }

    @Override
    public PayOrderDTO find(Long id) {
        return findPayOrderById(id);
    }

    @Override
    public PayRecordDTO findLastByPayOrderId(Long payOrderId) {
        return findLastPayRecordByPayOrderId(payOrderId);
    }

    @Override
    public NotifyResultDTO payResultCallback(AliPayNotifyMessageDTO notifyMessage) {
        if (Objects.isNull(notifyMessage)) {
            return NotifyResultDTO.fail();
        }

        PayOrderDO payOrderDO =
                payOrderMapper.selectByThirdOutTradeNo(notifyMessage.getOutTradeNo());

        if (payOrderDO == null) {
            log.warn("支付订单未找到, out trade no:[{}], resource:[{}]",
                    notifyMessage.getOutTradeNo(), notifyMessage);
            return NotifyResultDTO.success();
        }

        if (PayOrderStatusEnum.SUCCESS.equals(payOrderDO.getStatus())) {
            log.info("支付订单已完成, out trade no:[{}], resource:[{}]",
                    notifyMessage.getOutTradeNo(), notifyMessage);
            return NotifyResultDTO.success();
        }

        Date now = new Date();
        PayRecordDTO payRecordDTO = null;
        try {
            // 支付成功则更新交易订单信息, 交易失败只将失败记录记录到表中
            if (AliPayTradeStatusEnum.TRADE_SUCCESS.name().equals(notifyMessage.getTradeStatus()) &&
                    !PayOrderStatusEnum.SUCCESS.equals(payOrderDO.getStatus())) {
                PayOrderDO updatePayOrderDO = new PayOrderDO();
                updatePayOrderDO.setId(payOrderDO.getId());
                updatePayOrderDO.setThirdTransactionId(notifyMessage.getTradeNo());
                updatePayOrderDO.setStatus(PayOrderStatusEnum.SUCCESS);
                payOrderMapper.updateById(updatePayOrderDO);

                PayRecordDO payRecordDO = new PayRecordDO();
                payRecordDO.setPayOrderId(payOrderDO.getId());
                payRecordDO.setResultStatus(PayRecordStatusEnum.SUCCESS);
                payRecordDO.setEventType(notifyMessage.getNotifyType());
                payRecordDO.setSummary(StringUtils.EMPTY);
                payRecordDO.setResult(objectMapper.writeValueAsString(notifyMessage));
                payRecordDO.setCreateTime(now);
                payRecordDO.setModifyTime(now);
                payRecordMapper.insert(payRecordDO);
                log.info("Alipay notify message:[{}]", notifyMessage);

                payRecordDTO = ThirdPaymentService.convertTo(payRecordDO);

                payOrderCache.delPayOrderDTOById(payOrderDO.getId());
                payOrderCache.delPayOrderDTOByUserIdAndAppIdAndPrepayId(payOrderDO.getUserId(),
                        payOrderDO.getAppId(), payOrderDO.getThirdPrepayId());
            } else {
                PayRecordDO payRecordDO = new PayRecordDO();
                payRecordDO.setPayOrderId(payOrderDO.getId());
                payRecordDO.setResultStatus(PayRecordStatusEnum.FAIL);
                payRecordDO.setEventType(notifyMessage.getNotifyType());
                payRecordDO.setSummary(StringUtils.EMPTY);
                payRecordDO.setResult(objectMapper.writeValueAsString(notifyMessage));
                payRecordDO.setCreateTime(now);
                payRecordDO.setModifyTime(now);
                payRecordMapper.insert(payRecordDO);

                payRecordDTO = ThirdPaymentService.convertTo(payRecordDO);
                log.info("Alipay notify message:[{}]", notifyMessage);
            }

            // 通知下游商户
            aliPayPaymentService.payNotifyDownstreamMerchant(payOrderDO.getAppId(), notifyMessage.getOutTradeNo(), payRecordDTO);
        } catch (JsonProcessingException e) {
            log.error(String.format("支付通知格式错误, notify resource:[%s]", notifyMessage), e);
            return NotifyResultDTO.fail();
        } catch (Exception e) {
            log.error(String.format("未知错误, notify resource:[%s]", notifyMessage), e);
            return NotifyResultDTO.fail();
        }

        return NotifyResultDTO.success();
    }

    @Override
    public PayOrderDTO tryUpdateFromThirdInfo(Long payOrderId) {
        // 如果已经处理完成或者订单关闭则不必再查询平台信息
        Date now = new Date();
        PayOrderDTO payOrderDTO = find(payOrderId);

        if (Objects.isNull(payOrderDTO)) {
            return payOrderDTO;
        }

        if (PayOrderStatusEnum.SUCCESS.equals(payOrderDTO.getStatus())
                || SimpleDateUtils.isAfter(now, payOrderDTO.getPrepayExpireTime())) {
            return payOrderDTO;
        }

        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(payOrderDTO.getThirdOutTradeNo());
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);

        AlipayTradeQueryResponse alipayTradeQueryResponse = certificateExecute(payOrderDTO.getThirdAppId(), request);
        if (Objects.isNull(alipayTradeQueryResponse)
                || !alipayTradeQueryResponse.isSuccess()
                || !AliPayTradeStatusEnum.TRADE_SUCCESS.equals(alipayTradeQueryResponse.getTradeStatus())) {
            log.info("Ali pay payment order trade state is not success. trade state:[{}]",
                    (alipayTradeQueryResponse != null && alipayTradeQueryResponse.isSuccess() ?
                            alipayTradeQueryResponse.getTradeStatus() : null));
            return payOrderDTO;
        }

        updatePayOrderStatus(payOrderId, PayOrderStatusEnum.SUCCESS);

        return aliPayPaymentService.findPayOrderById(payOrderId);
    }

    @Override
    public PaymentQuerySignDTO sign(Long payOrderId) {
        PayOrderDTO payOrderDTO = findPayOrderById(payOrderId);
        if (Objects.isNull(payOrderDTO)) {
            return null;
        }

        AppDTO appDTO = appService.findByAliAppId(payOrderDTO.getThirdAppId());
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("aliPayAppId[%s]数据查询不到", payOrderDTO.getThirdAppId()));
        }

        String timestamp = SimpleDateFormatConstants.getLongTimeFormat().format(new Date());

        // 构建支付宝的签名参数
        RequestParametersHolder requestHolder = new RequestParametersHolder();

        AlipayTradeAppPayModel alipayTradeAppPayModel = new AlipayTradeAppPayModel();
        BigDecimal amountDecimal = new BigDecimal(payOrderDTO.getAmount());
        BigDecimal yuanInCents = new BigDecimal(BusinessConstants.RMB_YUAN_IN_CENTS);

        alipayTradeAppPayModel.setOutTradeNo(payOrderDTO.getThirdOutTradeNo());
        alipayTradeAppPayModel.setTotalAmount(amountDecimal.divide(yuanInCents, 2, RoundingMode.HALF_UP).toString());
        alipayTradeAppPayModel.setSubject(StringUtils.isBlank(payOrderDTO.getShortDesc()) ? appDTO.getName() : payOrderDTO.getShortDesc());
        alipayTradeAppPayModel.setProductCode(BusinessConstants.ALI_PAY_TRADE_APP_PAY_PRODUCT_CODE);
//        alipayTradeAppPayModel.setTimeoutExpress(aliPayPaymentProperties.getDefaultOrderTimeExpireMinute() + "m");

        String bizContent = new JSONWriter().write(alipayTradeAppPayModel, true);
        AlipayHashMap appParams = new AlipayHashMap();
        appParams.put(AlipayConstants.BIZ_CONTENT_KEY, bizContent);
        requestHolder.setApplicationParams(appParams);

        String certSn;
        String rootCertSn;
        try {
            certSn = AntCertificationUtil.getCertSN(
                    AntCertificationUtil.getCertFromContent(appDTO.getAliPayAppCert()));
            rootCertSn = AntCertificationUtil.getRootCertSN(appDTO.getAliPayRootCert());
        } catch (AlipayApiException e) {
            log.error(String.format("证书序列号计算错误. aliPayAppId:[%s]", appDTO.getAliPayAppId()), e);
            throw new BusinessException(ErrorCodeEnum.RESOURCE_ERROR);
        }

        AlipayHashMap protocalMustParams = new AlipayHashMap();
        protocalMustParams.put(AlipayConstants.APP_CERT_SN, certSn);
        protocalMustParams.put(AlipayConstants.ALIPAY_ROOT_CERT_SN, rootCertSn);
        protocalMustParams.put(AlipayConstants.APP_ID, payOrderDTO.getThirdAppId());
        protocalMustParams.put(AlipayConstants.CHARSET, AlipayConstants.CHARSET_UTF8);
        protocalMustParams.put(AlipayConstants.FORMAT, AlipayConstants.FORMAT_JSON);
        protocalMustParams.put(AlipayConstants.METHOD, aliPayPaymentProperties.getTradeAppPayMethod());
        protocalMustParams.put(AlipayConstants.NOTIFY_URL, aliPayPaymentProperties.getPayNotifyApiUrl());
        protocalMustParams.put(AlipayConstants.SIGN_TYPE, AlipayConstants.SIGN_TYPE_RSA2);
        protocalMustParams.put(AlipayConstants.TIMESTAMP, timestamp);
        protocalMustParams.put(AlipayConstants.VERSION, "1.0");
        requestHolder.setProtocalMustParams(protocalMustParams);

        String signContent = AlipaySignature.getSignatureContent(requestHolder);
        Signer signer = new DefaultSigner(appDTO.getAliPayMerchantPrivateKey());
        String sign = signer.sign(signContent, AlipayConstants.SIGN_TYPE_RSA2, AlipayConstants.CHARSET_UTF8);

        PaymentQuerySignDTO paymentQuerySignDTO = new PaymentQuerySignDTO();
        try {
            // 拼接我们的签名参数
            String orderInfo = AlipayConstants.APP_CERT_SN + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.APP_CERT_SN), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.ALIPAY_ROOT_CERT_SN + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.ALIPAY_ROOT_CERT_SN), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.APP_ID + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.APP_ID), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.BIZ_CONTENT_KEY + "=" + URLCodecUtils.encode(appParams.get(AlipayConstants.BIZ_CONTENT_KEY), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.CHARSET + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.CHARSET), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.FORMAT + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.FORMAT), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.METHOD + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.METHOD), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.NOTIFY_URL + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.NOTIFY_URL), AlipayConstants.CHARSET_UTF8)+ "&"
                            + AlipayConstants.SIGN_TYPE + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.SIGN_TYPE), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.TIMESTAMP + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.TIMESTAMP), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.VERSION + "=" + URLCodecUtils.encode(protocalMustParams.get(AlipayConstants.VERSION), AlipayConstants.CHARSET_UTF8) + "&"
                            + AlipayConstants.SIGN + "=" + URLCodecUtils.encode(sign, AlipayConstants.CHARSET_UTF8);
            paymentQuerySignDTO.setSign(orderInfo);
        } catch (UnsupportedEncodingException e) {
            // TODO: 没想好该做啥, 基本不会出这个问题
            log.error("Ali pay sign error.", e);
            return null;
        }

        return paymentQuerySignDTO;
    }

    @Override
    public String generateSign(PaymentDTO paymentDTO, String thirdAppId, String thirdMchid) {
        return null;
    }

    @Override
    public PaymentResultDTO pay(PaymentDTO paymentDTO, String thirdAppId, String thirdMchid) {
        return null;
    }

    @Override
    public AliPaymentResultDTO alPay(WithdrawalQueryDTO withdrawalQueryDTO, String thirdAppId) {
        AppDTO appDTO = appService.findByAliAppId(thirdAppId);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("aliPayAppId[%s]数据查询不到", thirdAppId));
        }

        AlipayFundTransUniTransferRequest alipayFundTransUniTransferRequest = new AlipayFundTransUniTransferRequest();
        //拼接参数
        AlipayFundTransUniTransferModel alipayFundTransUniTransferModel = new AlipayFundTransUniTransferModel();
        String amount = getAmount(withdrawalQueryDTO.getAmount());
        alipayFundTransUniTransferModel.setTransAmount(amount);
        alipayFundTransUniTransferModel.setOutBizNo(withdrawalQueryDTO.getPayBusinessId());
        alipayFundTransUniTransferModel.setOrderTitle(withdrawalQueryDTO.getTitle());
        Participant participant = new Participant();
        participant.setIdentity(withdrawalQueryDTO.getIdentity());
        participant.setName(withdrawalQueryDTO.getIdentityName());
        participant.setIdentityType("ALIPAY_LOGON_ID");
        alipayFundTransUniTransferModel.setPayeeInfo(participant);
        alipayFundTransUniTransferModel.setProductCode("TRANS_ACCOUNT_NO_PWD");
        alipayFundTransUniTransferModel.setBizScene("DIRECT_TRANSFER");
        alipayFundTransUniTransferModel.setRemark(withdrawalQueryDTO.getRemark());
        PayerShowNameDTO payerShowNameDTO = new PayerShowNameDTO();
        payerShowNameDTO.setPayer_show_name(withdrawalQueryDTO.getPayerShowName());
        alipayFundTransUniTransferModel.setBusinessParams(JSONObject.toJSONString(payerShowNameDTO));
        //设置参数
        alipayFundTransUniTransferRequest.setBizModel(alipayFundTransUniTransferModel);
        AlipayFundTransUniTransferResponse response = certificateExecute(thirdAppId, alipayFundTransUniTransferRequest);

        if (ObjectUtils.isEmpty(response)) {
            throw new BusinessException(ErrorCodeEnum.THIRD_PAY_ERROR);
        }
        AliPaymentResultDTO aliPaymentResultDTO = new AliPaymentResultDTO();
        BeanUtils.copyProperties(response, aliPaymentResultDTO);
        //判断结果
        if (response.isSuccess()) {
            aliPaymentResultDTO.setPayStatus(PaymentReturnCodeEnum.SUCCESS.getCode());
            return aliPaymentResultDTO;
        } else {
            log.error("alipay AlipayFundTransUniTransfer err , info:{}", response);
            aliPaymentResultDTO.setPayStatus(PaymentReturnCodeEnum.FAIL.getCode());
            return aliPaymentResultDTO;
        }
    }

    private String getAmount(Long amount) {
        double f =  ((double)amount) / 100;
        BigDecimal b = new BigDecimal(f);
        double data = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(data);
    }

    @Override
    public boolean signCheck(Map<String, String> checkObj) {

        String thirdAppId = checkObj.get(AlipayConstants.APP_ID);

        AppDTO appDTO = appService.findByAliAppId(thirdAppId);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("ali payment app id config[%s]数据查询不到", thirdAppId));
        }

        try {
            X509Certificate cert = AntCertificationUtil.getCertFromContent(appDTO.getAliPayPublicCert());
            PublicKey publicKey = cert.getPublicKey();
            String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());

            //切记alipayPublicCertPath是应用公钥证书路径，请去open.alipay.com对应应用下载。
            //boolean AlipaySignature.rsaCertCheckV1(Map<String, String> params, String publicKeyCertPath, String charset,String signType)
            return AlipaySignature.rsaCheckV1(checkObj, publicKeyStr,
                    AlipayConstants.CHARSET_UTF8, checkObj.get(AlipayConstants.SIGN_TYPE));
        } catch (AlipayApiException e) {
            log.error(String.format("Ali pay rsa check sign error. params:[%s]", checkObj), e);
        }
        return false;
    }
}
