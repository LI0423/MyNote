package com.video.payment.service.third.wechat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.video.entity.*;
import com.video.payment.config.prod.WeChatPaymentProperties;
import com.video.payment.constant.BusinessConstants;
import com.video.payment.constant.BusinessParamConstants;
import com.video.payment.domain.dto.app.AppDTO;
import com.video.payment.domain.dto.payment.PayRecordDTO;
import com.video.payment.domain.dto.payment.PaymentQuerySignDTO;
import com.video.payment.domain.dto.user.UserAuthDTO;
import com.video.payment.domain.dto.withdrawal.WithdrawalQueryDTO;
import com.video.payment.exception.BusinessException;
import com.video.payment.exception.ErrorCodeEnum;
import com.video.payment.service.AppService;
import com.video.payment.service.third.ThirdPaymentService;
import com.video.payment.util.HttpClientUtils;
import com.video.payment.util.RSAUtils;
import com.video.payment.util.SimpleDateUtils;
import com.video.payment.domain.dto.third.WeChatPaymentCheckNameEnum;
import com.video.payment.domain.dto.payment.NotifyResultDTO;
import com.video.payment.domain.dto.payment.PayOrderDTO;
import com.video.payment.domain.dto.third.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;

/**
 * @program: mango
 * @author: wangwei
 * @description: 微信支付第三方支付service
 */
@Slf4j
@Service("WE_CHART_PAYMENT")
public class WeChatPaymentServiceImpl extends WeChatBasePaymentService implements ThirdPaymentService<WeChatNotifyMessageDTO, WeChatNotifyMessageDTO> {

    @Qualifier("WE_CHART_PAYMENT")
    @Autowired
    private WeChatPaymentServiceImpl weChatPaymentService;

    @Autowired
    private HttpClientBuilder httpClientBuilder;

    @Autowired
    private WeChatPaymentProperties weChatPaymentProperties;

    @Autowired
    private AppService appService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayOrderDTO prepay(Long userId, String pkg, Long amount, PayOrderPayTypeEnum payType,
                              Integer businessType, String orderDesc, String notifyUrl) {
        if (StringUtils.isBlank(notifyUrl)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR, "必要请求参数notifyUrl没找到");
        }
        try {
            URL notifyURL = new URL(notifyUrl);
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR,
                    String.format("请求参数notifyUrl格式错误, notifyUrl:[%s]", notifyUrl));
        }
        AppDTO appDTO = appService.findRand(pkg, PayOrderChannelEnum.WE_CHAT, 0);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("pkg[%s]数据查询不到", pkg));
        }

        UserAuthDTO userAuthDTO = userAuthService.findByUserId(userId);
        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
            throw new BusinessException(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND,
                    String.format("user_auth[%s]数据查询不到", userId));
        }

        Date now = new Date();
        // 预支付订单关闭时间
        Date prepayExpireTime = SimpleDateUtils.addMinute(now,
                weChatPaymentProperties.getDefaultOrderTimeExpireMinute());

        PayOrderDTO payOrderDTO = save(appDTO.getWeChatMchid(), appDTO.getWeChatAppId(), payType, appDTO.getId(),
                PayOrderChannelEnum.WE_CHAT, userId, businessType, amount, prepayExpireTime,
                Objects.isNull(appDTO.getName()) ? BusinessConstants.DEFAULT_PAYMENT_DESC : appDTO.getName(), notifyUrl);

        //调用第三方下单接口
        WeChatPaymentRequestDTO paymentRequestDTO =
                WeChatPaymentRequestDTO
                        .builder().appId(appDTO.getWeChatAppId())
                        .mchId(appDTO.getWeChatMchid())
                        .outTradeNo(payOrderDTO.getId().toString())
                        .description(Objects.isNull(appDTO.getName()) ?
                                BusinessConstants.DEFAULT_PAYMENT_DESC : appDTO.getName())
                        .timeExpire(prepayExpireTime)
                        .amount(WeChatPaymentAmountDTO
                                .builder()
                                .total((int) amount.longValue())
                                .currency(WeChatAmountCurrencyEnum.CNY)
                                .build())
                        .notifyUrl(weChatPaymentProperties.getPayNotifyApiUrl())
                        .build();

        long weChatPostRequestStartTime = System.currentTimeMillis();
        WeChatResponseResultDTO<WeChatPaymentResponseDTO> weChatResponseResult =
                weChatPostRequest(appDTO.getWeChatAppId(), appDTO.getWeChatMchid(), weChatPaymentProperties.getAppPaymentApiUrl(),
                        paymentRequestDTO, WeChatPaymentResponseDTO.class);
        long weChatPostRequestEndTime = System.currentTimeMillis();

        log.info("Create prepay order time consuming [{}], user_id:[{}], app_id:[{}], " +
                "amount:[{}], pay_type:[{}], business_type:[{}], order_desc:[{}]",
                (weChatPostRequestEndTime - weChatPostRequestStartTime), userId, appDTO.getId(),
                amount, payType, businessType, orderDesc);

        try {
            // 调用错误,记录一下
            if (weChatResponseResult.getMessage() != null) {
                PayRecordDO payRecordDO = new PayRecordDO();
                payRecordDO.setPayOrderId(payOrderDTO.getId());
                payRecordDO.setResultStatus(PayRecordStatusEnum.OTHER);
                payRecordDO.setResult(objectMapper.writeValueAsString(
                        weChatResponseResult.getMessage().getDetail()));
                payRecordDO.setThirdErrorCode(weChatResponseResult.getMessage().getCode());
                payRecordDO.setThirdErrorMsg(weChatResponseResult.getMessage().getMessage());
                payRecordDO.setCreateTime(now);
                payRecordDO.setModifyTime(now);
                payRecordMapper.insert(payRecordDO);
            } else {
                // 调用成功则更新prepay id(预支付订单id)
                PayOrderDO updatePayOrderDO = new PayOrderDO();
                updatePayOrderDO.setId(payOrderDTO.getId());
                updatePayOrderDO.setThirdPrepayId(weChatResponseResult.getResult().getPrepayId());
                payOrderMapper.updateById(updatePayOrderDO);

                PayOrderDO prepayOrderDO = payOrderMapper.selectById(payOrderDTO.getId());
                return ThirdPaymentService.convertTo(prepayOrderDO);
            }
        } catch (JsonProcessingException e) {
            log.error("We chat error response detail object to json string process fail.", e);
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayOrderDTO find(Long id) {

        // 尝试从缓存中获取PayOrderDTO信息
        PayOrderDTO payOrderDTO =
                payOrderCache.getPayOrderDTOById(id);

        if (payOrderDTO != null) {
            return payOrderDTO.getId() == null ? null : payOrderDTO;
        }

        PayOrderDO payOrderDO =
                payOrderMapper.selectById(id);

        payOrderDTO = payOrderDO == null ? null : ThirdPaymentService.convertTo(payOrderDO);

        // 将PayOrderDTO信息设置到缓存中
        payOrderCache.setPayOrderDTOByIdExpire(id, payOrderDTO);

        return payOrderDTO;
    }

    @Override
    public PayRecordDTO findLastByPayOrderId(Long payOrderId) {

        // 尝试从缓存中获取PayRecordDTO信息
        PayRecordDTO payRecordDTO =
                payRecordCache.getLastPayRecordDTOByPayOrderId(payOrderId);

        if (payRecordDTO != null) {
            return payRecordDTO.getId() == null ? null : payRecordDTO;
        }

        PayRecordDO payRecordDO =
                payRecordMapper.selectLastByPayOrderId(payOrderId);

        payRecordDTO = payRecordDO == null ? null : ThirdPaymentService.convertTo(payRecordDO);

        // 将PayRecordDTO信息设置到缓存中
        payRecordCache.setLastPayRecordDTOByPayOrderIdExpire(payOrderId, payRecordDTO);

        return payRecordDTO;
    }

    @Override
    public NotifyResultDTO payResultCallback(WeChatNotifyMessageDTO notifyMessage) {

        List<String> apiV3Keys = appService.allWeChatApiV3Key();

        String decryptStr = null;
        for (String apiV3Key : apiV3Keys) {
            try {
                decryptStr = aesDecryptToString(apiV3Key,
                        notifyMessage.getResource().getAssociatedData(),
                        notifyMessage.getResource().getNonce(),
                        notifyMessage.getResource().getCiphertext());
            } catch (Exception e) {
                log.debug("", e);
            }

        }

        if (decryptStr == null) {
            log.warn("Decrypt error, notify message [{}]", notifyMessage);
            return NotifyResultDTO.fail();
        }

        try {
            WeChatPaymentOrderInfoDTO weChatPaymentOrderInfoDTO =
                    objectMapper.readValue(decryptStr, WeChatPaymentOrderInfoDTO.class);

            PayOrderDO payOrderDO =
                    payOrderMapper.selectByThirdOutTradeNo(weChatPaymentOrderInfoDTO.getOutTradeNo());

            if (payOrderDO == null) {
                log.warn("支付订单未找到, out trade no:[{}], resource:[{}]",
                        weChatPaymentOrderInfoDTO.getOutTradeNo(), weChatPaymentOrderInfoDTO);
                return NotifyResultDTO.success();
            }

            if (PayOrderStatusEnum.SUCCESS.equals(payOrderDO.getStatus())) {
                log.info("支付订单已完成, out trade no:[{}], resource:[{}]",
                        weChatPaymentOrderInfoDTO.getOutTradeNo(), weChatPaymentOrderInfoDTO);
                return NotifyResultDTO.success();
            }

            Date now = new Date();
            PayRecordDTO payRecordDTO = new PayRecordDTO();
            // 支付成功则更新交易订单信息, 交易失败只将失败记录记录到表中
            if (WeChatPaymentTradeStateEnum.SUCCESS.equals(weChatPaymentOrderInfoDTO.getTradeState()) &&
                    !PayOrderStatusEnum.SUCCESS.equals(payOrderDO.getStatus())) {
                PayOrderDO updatePayOrderDO = new PayOrderDO();
                updatePayOrderDO.setId(payOrderDO.getId());
                updatePayOrderDO.setThirdTransactionId(weChatPaymentOrderInfoDTO.getTransactionId());
                updatePayOrderDO.setStatus(PayOrderStatusEnum.SUCCESS);
                payOrderMapper.updateById(updatePayOrderDO);

                PayRecordDO payRecordDO = new PayRecordDO();
                payRecordDO.setPayOrderId(payOrderDO.getId());
                payRecordDO.setResultStatus(PayRecordStatusEnum.SUCCESS);
                payRecordDO.setEventType(notifyMessage.getEventType());
                payRecordDO.setSummary(notifyMessage.getSummary());
                payRecordDO.setResult(decryptStr);
                payRecordDO.setCreateTime(now);
                payRecordDO.setModifyTime(now);
                payRecordMapper.insert(payRecordDO);
                log.info("We chat notify message:[{}]", notifyMessage);

                payRecordDTO = ThirdPaymentService.convertTo(payRecordDO);

                payOrderCache.delPayOrderDTOById(payOrderDO.getId());
                payOrderCache.delPayOrderDTOByUserIdAndAppIdAndPrepayId(payOrderDO.getUserId(),
                        payOrderDO.getAppId(), payOrderDO.getThirdPrepayId());
            } else {
                PayRecordDO payRecordDO = new PayRecordDO();
                payRecordDO.setPayOrderId(payOrderDO.getId());
                payRecordDO.setResultStatus(PayRecordStatusEnum.FAIL);
                payRecordDO.setEventType(notifyMessage.getEventType());
                payRecordDO.setSummary(notifyMessage.getSummary());
                payRecordDO.setResult(decryptStr);
                payRecordDO.setCreateTime(now);
                payRecordDO.setModifyTime(now);
                payRecordMapper.insert(payRecordDO);

                payRecordDTO = ThirdPaymentService.convertTo(payRecordDO);
                log.info("We chat notify message:[{}]", notifyMessage);
            }

            // 通知下游商户
            weChatPaymentService.payNotifyDownstreamMerchant(payOrderDO.getAppId(),
                    weChatPaymentOrderInfoDTO.getOutTradeNo(), payRecordDTO);
        } catch (JsonProcessingException e) {
            log.error(String.format("支付通知格式错误, notify resource:[%s]", decryptStr), e);
            return NotifyResultDTO.fail();
        } catch (Exception e) {
            log.error(String.format("未知错误, notify resource:[%s]", decryptStr), e);
            return NotifyResultDTO.fail();
        }

        return NotifyResultDTO.success();
    }

    /**
     * 查询微信平台订单
     * @param thirdAppId 三方的appId
     * @param mchId 直连商户号
     * @param outTradeNo 商户订单号
     * @return 微信平台订单信息
     */
    private WeChatPaymentOrderInfoDTO queryPaymentOrderInfo(String thirdAppId, String mchId, String outTradeNo) {

        String url = StringUtils
                .replace(weChatPaymentProperties.getSelectOrderApiUrl(),
                        "{out_trade_no}", outTradeNo);

        Map<String, Object> params = new HashMap<>();
        params.put(BusinessParamConstants.MCHID, mchId);

        // 获取返回内容
        WeChatResponseResultDTO<WeChatPaymentOrderInfoDTO> responseResultDTO =
                weChatGetRequest(thirdAppId, mchId, url, params, WeChatPaymentOrderInfoDTO.class);

        // 请求错误处理
        if (responseResultDTO.getMessage() != null) {
            log.error("Query payment order info error. error message:[{}]", responseResultDTO.getMessage().toString());
            return null;
        }

        return responseResultDTO.getResult();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayOrderDTO tryUpdateFromThirdInfo(Long payOrderId) {
        // 如果已经处理完成或者订单关闭则不必再查询平台信息
        Date now = new Date();
        PayOrderDTO payOrderDTO = weChatPaymentService.find(payOrderId);

        if (Objects.isNull(payOrderDTO)) {
            return payOrderDTO;
        }

        if (PayOrderStatusEnum.SUCCESS.equals(payOrderDTO.getStatus())
                || SimpleDateUtils.isAfter(now, payOrderDTO.getPrepayExpireTime())) {
            return payOrderDTO;
        }

        // 获取微信平台订单信息
        WeChatPaymentOrderInfoDTO weChatPaymentOrderInfoDTO =
                queryPaymentOrderInfo(payOrderDTO.getThirdAppId(),
                        payOrderDTO.getThirdMchid(), payOrderDTO.getThirdOutTradeNo());

        // 若微信平台订单信息也未完成则直接返回即可(这里不需要记录支付失败记录, 回调接口记录即可)
        if (weChatPaymentOrderInfoDTO == null
                || !WeChatPaymentTradeStateEnum.SUCCESS.equals(weChatPaymentOrderInfoDTO.getTradeState())) {
            log.info("We chat payment order trade state is not success. trade state:[{}]",
                    (weChatPaymentOrderInfoDTO == null ? null : weChatPaymentOrderInfoDTO.getTradeState()));
            return payOrderDTO;
        }

        PayOrderDO updatePayOrderDO = new PayOrderDO();
        updatePayOrderDO.setId(payOrderDTO.getId());
        updatePayOrderDO.setThirdTransactionId(weChatPaymentOrderInfoDTO.getTransactionId());
        updatePayOrderDO.setStatus(PayOrderStatusEnum.SUCCESS);
        updatePayOrderDO.setModifyTime(now);
        payOrderMapper.updateById(updatePayOrderDO);

        payOrderCache.delPayOrderDTOById(payOrderDTO.getId());
        payOrderCache.delPayOrderDTOByUserIdAndAppIdAndPrepayId(payOrderDTO.getUserId(),
                payOrderDTO.getAppId(), payOrderDTO.getThirdPrepayId());

        return weChatPaymentService.find(payOrderDTO.getId());
    }

    @Override
    public PaymentQuerySignDTO sign(Long payOrderId) {

        PayOrderDTO payOrderDTO = weChatPaymentService.find(payOrderId);
        if (payOrderDTO == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR,
                    String.format("Pay order[%s]数据查询不到", payOrderId));
        }

        AppDTO appDTO = appService.findByWeChatMchid(payOrderDTO.getThirdAppId(), payOrderDTO.getThirdMchid());
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("weChatAppId[%s]数据查询不到", payOrderDTO.getThirdAppId()));
        }

        try {
            String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
            String nonceStr = RandomStringUtils.randomAlphanumeric(BusinessConstants.WX_PREPAY_NONCESTR_LEN);
            String prepayId = payOrderDTO.getThirdPrepayId();
            String text = appDTO.getWeChatAppId() + "\n" + timeStamp + "\n" + nonceStr + "\n" + prepayId + "\n";
            String sign = RSAUtils.sign(appDTO.getWeChatMchPrivateKey(), text);

            PaymentQuerySignDTO paymentQuerySignDTO = new PaymentQuerySignDTO();
            paymentQuerySignDTO.setAppId(appDTO.getWeChatAppId());
            paymentQuerySignDTO.setTimeStamp(timeStamp);
            paymentQuerySignDTO.setNonceStr(nonceStr);
            paymentQuerySignDTO.setPrepayId(prepayId);
            paymentQuerySignDTO.setMchId(payOrderDTO.getThirdMchid());
            paymentQuerySignDTO.setExtPackage(BusinessConstants.WX_PACKAGE);
            paymentQuerySignDTO.setSign(sign);
            return paymentQuerySignDTO;
        } catch (Exception e) {
            log.error("签名错误.", e);
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public String generateSign(PaymentDTO paymentDTO, String thirdAppId, String thirdMchid) {
        // 获取app配置，顺便判断pkg的合法性
        AppDTO appDTO = appService.findByWeChatMchid(thirdAppId, thirdMchid);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("thirdAppId[%s]数据查询不到", thirdAppId));
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
    public PaymentResultDTO pay(PaymentDTO paymentDTO, String thirdAppId, String thirdMchid) {

        AppDTO appDTO = appService.findByWeChatMchid(thirdAppId, thirdMchid);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("thirdAppId[%s]数据查询不到", thirdAppId));
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

    @Override
    public AliPaymentResultDTO alPay(WithdrawalQueryDTO withdrawalQueryDTO, String thirdAppId) {
        return null;
    }

    @Override
    public boolean signCheck(WeChatNotifyMessageDTO checkObj) {
        // TODO: 后面补上校验逻辑
        return true;
    }
}
