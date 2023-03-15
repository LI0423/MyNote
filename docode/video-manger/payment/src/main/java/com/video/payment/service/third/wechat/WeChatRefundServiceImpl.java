package com.video.payment.service.third.wechat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.video.entity.*;
import com.video.payment.domain.dto.app.AppDTO;
import com.video.payment.domain.dto.payment.NotifyResultDTO;
import com.video.payment.domain.dto.payment.PayOrderDTO;
import com.video.payment.domain.dto.refund.RefundNotifyMessageDTO;
import com.video.payment.domain.dto.refund.RefundOrderDTO;
import com.video.payment.domain.dto.refund.RefundRecordDTO;
import com.video.payment.exception.BusinessException;
import com.video.payment.exception.ErrorCodeEnum;
import com.video.payment.mapper.RefundOrderDownstreamNotifyMapper;
import com.video.payment.mapper.RefundOrderMapper;
import com.video.payment.mapper.RefundRecordMapper;
import com.video.payment.service.third.ThirdRefundService;
import com.video.payment.domain.dto.third.WeChatAmountCurrencyEnum;
import com.video.payment.domain.dto.third.WeChatNotifyMessageDTO;
import com.video.payment.domain.dto.third.WeChatResponseResultDTO;
import com.video.payment.domain.dto.third.refund.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 微信退款service实现
 * @author wangwei
 */
@Slf4j
@Service("WE_CHART_REFUND")
public class WeChatRefundServiceImpl extends WeChatBasePaymentService implements ThirdRefundService<WeChatNotifyMessageDTO> {

    @Qualifier("WE_CHART_PAYMENT")
    @Autowired
    private WeChatPaymentServiceImpl weChatPaymentService;

    @Autowired
    private RefundOrderMapper refundOrderMapper;

    @Autowired
    private RefundRecordMapper refundRecordMapper;

    @Autowired
    private RefundOrderDownstreamNotifyMapper refundOrderDownstreamNotifyMapper;

    @Qualifier("WE_CHART_REFUND")
    @Autowired
    private WeChatRefundServiceImpl weChatRefundService;

    /**
     * 构建请求体
     * @param payOrderDTO 支付订单信息
     * @param amount 金额
     * @param reason 退款原因
     * @param notifyUrl
     * @return
     */
    private static WeChatRefundRequestDTO buildWeChatRefundRequestDTO(PayOrderDTO payOrderDTO, String outRefundNo,
                                                                      Integer amount, String reason, String notifyUrl) {

        WeChatRefundRequestDTO weChatRefundRequestDTO = new WeChatRefundRequestDTO();
        weChatRefundRequestDTO.setOutTradeNo(payOrderDTO.getThirdOutTradeNo());
        weChatRefundRequestDTO.setOutRefundNo(outRefundNo);
        weChatRefundRequestDTO.setReason(reason);
        weChatRefundRequestDTO.setNotifyUrl(notifyUrl);
        weChatRefundRequestDTO.setFundsAccount(FundsAccountEnum.AVAILABLE);

        AmountDTO amountDTO = new AmountDTO();
        amountDTO.setRefund(amount);
        amountDTO.setTotal(payOrderDTO.getAmount().intValue());
        amountDTO.setCurrency(WeChatAmountCurrencyEnum.CNY);

//        AccountAndAmountDTO accountAndAmountDTO = new AccountAndAmountDTO();
//        accountAndAmountDTO.setAccount(AccountEnum.AVAILABLE);
//        accountAndAmountDTO.setAmount(amount);
//        amountDTO.setFrom(Collections.singletonList(accountAndAmountDTO));

        weChatRefundRequestDTO.setAmount(amountDTO);
        return weChatRefundRequestDTO;
    }

    @Override
    public RefundOrderDTO refund(String pkg, Long payOrderId, Integer amount, String reason, String notifyUrl) {

        if (StringUtils.isBlank(notifyUrl)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR, "必要请求参数notifyUrl没找到");
        }

        try {
            URL notifyURL = new URL(notifyUrl);
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR,
                    String.format("请求参数notifyUrl格式错误, notifyUrl:[%s]", notifyUrl));
        }

        PayOrderDTO payOrderDTO = weChatPaymentService.find(payOrderId);

        if(Objects.isNull(payOrderDTO)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR,
                    String.format("支付订单信息未找到, pay order id:[%s]", payOrderId));
        }

        AppDTO appDTO = appService.findByWeChatMchid(payOrderDTO.getThirdAppId(), payOrderDTO.getThirdMchid());
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("thirdAppId[%s]数据查询不到", payOrderDTO.getThirdAppId()));
        }

        Date now = new Date();
        RefundOrderDO refundOrderDO = new RefundOrderDO();
        BeanUtils.copyProperties(payOrderDTO, refundOrderDO);
        refundOrderDO.setId(null);
        refundOrderDO.setAmount(amount.longValue());
        refundOrderDO.setPayOrderId(payOrderDTO.getId());
        refundOrderDO.setThirdErrorCode(null);
        refundOrderDO.setThirdErrorMsg(null);
        refundOrderDO.setCreateTime(now);
        refundOrderDO.setModifyTime(now);
        refundOrderMapper.insert(refundOrderDO);

        RefundOrderDownstreamNotifyDO refundOrderDownstreamNotifyDO = new RefundOrderDownstreamNotifyDO();
        refundOrderDownstreamNotifyDO.setRefundOrderId(refundOrderDO.getId());
        refundOrderDownstreamNotifyDO.setNotifyUrl(notifyUrl);
        refundOrderDownstreamNotifyDO.setCreateTime(now);
        refundOrderDownstreamNotifyDO.setModifyTime(now);
        refundOrderDownstreamNotifyMapper.insert(refundOrderDownstreamNotifyDO);

        WeChatRefundRequestDTO weChatRefundRequestDTO =
                buildWeChatRefundRequestDTO(payOrderDTO, refundOrderDO.getId().toString(),
                        amount, reason, weChatPaymentProperties.getRefundNotifyApiUrl());

        long weChatPostRequestStartTime = System.currentTimeMillis();
        WeChatResponseResultDTO<WeChatRefundOrderInfoDTO> weChatResponseResult =
                weChatPostRequest(appDTO.getWeChatAppId(), appDTO.getWeChatMchid(),
                        weChatPaymentProperties.getRefundApiUrl(), weChatRefundRequestDTO, WeChatRefundOrderInfoDTO.class);
        long weChatPostRequestEndTime = System.currentTimeMillis();

        log.info("Create refund order time consuming [{}], user_id:[{}], app_id:[{}], third_app_id:[{}]" +
                        "amount:[{}], pay_type:[{}], business_type:[{}], reason:[{}]",
                (weChatPostRequestEndTime - weChatPostRequestStartTime), payOrderDTO.getId(), appDTO.getId(),
                appDTO.getWeChatAppId(), amount, payOrderDTO.getThirdPayType(), payOrderDTO.getType(), reason);

        try {
            if (weChatResponseResult.getMessage() != null) {
                RefundRecordDO refundRecordDO = new RefundRecordDO();
                refundRecordDO.setRefundOrderId(refundOrderDO.getId());
                refundRecordDO.setResultStatus(RefundRecordStatusEnum.OTHER);
                refundRecordDO.setResult(objectMapper.writeValueAsString(
                        weChatResponseResult.getMessage().getDetail()));
                refundRecordDO.setThirdErrorCode(weChatResponseResult.getMessage().getCode());
                refundRecordDO.setThirdErrorMsg(weChatResponseResult.getMessage().getMessage());
                refundRecordDO.setCreateTime(now);
                refundRecordDO.setModifyTime(now);
                refundRecordMapper.insert(refundRecordDO);
            } else {

                RefundOrderDO updateRefundOrderDO = new RefundOrderDO();
                updateRefundOrderDO.setId(refundOrderDO.getId());
                updateRefundOrderDO.setThirdOutRefundNo(refundOrderDO.getId().toString());
                // 如果当时就退款成功则直接设置成功
                if (RefundStatusEnum.SUCCESS.equals(weChatResponseResult.getResult().getStatus())) {
                    updateRefundOrderDO.setStatus(RefundOrderStatusEnum.SUCCESS);
                    updateRefundOrderDO.setCompletionTime(now);
                }
                refundOrderMapper.updateById(updateRefundOrderDO);

                refundOrderDO = refundOrderMapper.selectById(refundOrderDO.getId());
                return ThirdRefundService.convertTo(refundOrderDO);
            }
        } catch (JsonProcessingException e) {
            log.error("We chat error response detail object to json string process fail.", e);
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
        }
        return null;
    }

    @Override
    public RefundOrderDTO findByPayOrderId(Long payOrderId) {
        // TODO: 后续添加缓存
        RefundOrderDO refundOrderDO = refundOrderMapper.selectByPayOrderId(payOrderId);
        return ThirdRefundService.convertTo(refundOrderDO);
    }

    @Override
    public RefundOrderDTO findByRefundOrderId(Long refundOrderId) {
        // TODO: 后续添加缓存
        RefundOrderDO refundOrderDO = refundOrderMapper.selectById(refundOrderId);
        return ThirdRefundService.convertTo(refundOrderDO);
    }

    /**
     * 退款通知下游商户, 异步调用
     * @param appId app id
     * @param outRefundNo 订单id
     * @param refundRecordDTO 支付记录
     */
    @Async
    public void refundNotifyDownstreamMerchant(Long appId, String outRefundNo, Long payOrderId, RefundRecordDTO refundRecordDTO) {
        RefundOrderDO refundOrderDO =
                refundOrderMapper.selectById(refundRecordDTO.getRefundOrderId());

        log.info("Refund order downstream merchant start. refund order id:[{}]", refundOrderDO.getId());

        RefundOrderDownstreamNotifyDO refundOrderDownstreamNotifyDO =
                refundOrderDownstreamNotifyMapper.selectByRefundOrderId(refundOrderDO.getId());
        if (refundOrderDownstreamNotifyDO == null) {
            log.error("Refund order downstream merchant by pay order id not found. refund order id:[{}]",
                    refundOrderDO.getId());
            return;
        }

        RefundNotifyMessageDTO notifyMessageDTO = new RefundNotifyMessageDTO();
        notifyMessageDTO.setRefundOrderId(refundOrderDO.getId());
        notifyMessageDTO.setPayOrderId(payOrderId);
        notifyMessageDTO.setAmount(refundOrderDO.getAmount());
        notifyMessageDTO.setStatus(refundRecordDTO.getResultStatus());

        // 请求下游接口
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(refundOrderDownstreamNotifyDO.getNotifyUrl(),
                        notifyMessageDTO, String.class);

        log.info("Refund notify downstream result:[{}], refund order id:[{}]",
                responseEntity.getBody(), refundOrderDO.getId());

        // TODO: 失败处理，后面需要重新发送
    }

    @Override
    public NotifyResultDTO refundNotifyProcess(WeChatNotifyMessageDTO notifyMessage) {

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
            WeChatNotifyRefundOrderInfoDTO weChatNotifyRefundOrderInfoDTO =
                    objectMapper.readValue(decryptStr, WeChatNotifyRefundOrderInfoDTO.class);

            RefundOrderDO refundOrderDO =
                    refundOrderMapper.selectById(Long.parseLong(weChatNotifyRefundOrderInfoDTO.getOutRefundNo()));

            if (refundOrderDO == null) {
                log.warn("退款订单未找到, out refund no:[{}], resource:[{}]",
                        weChatNotifyRefundOrderInfoDTO.getOutRefundNo(), weChatNotifyRefundOrderInfoDTO);
                return NotifyResultDTO.success();
            }

            if (RefundOrderStatusEnum.SUCCESS.equals(refundOrderDO.getStatus())) {
                log.info("退款订单已完成, out refund no:[{}], resource:[{}]",
                        weChatNotifyRefundOrderInfoDTO.getOutRefundNo(), weChatNotifyRefundOrderInfoDTO);
                return NotifyResultDTO.success();
            }

            Date now = new Date();
            RefundRecordDTO refundRecordDTO = new RefundRecordDTO();
            // 退款成功且订单未完成则更新交易订单信息, 交易失败只将失败记录记录到表中
            if (RefundStatusEnum.SUCCESS.equals(weChatNotifyRefundOrderInfoDTO.getRefundStatus()) &&
                    !RefundOrderStatusEnum.SUCCESS.equals(refundOrderDO.getStatus())) {
                RefundOrderDO updateRefundOrderDO = new RefundOrderDO();
                updateRefundOrderDO.setId(refundOrderDO.getId());
                updateRefundOrderDO.setStatus(RefundOrderStatusEnum.SUCCESS);
                updateRefundOrderDO.setCompletionTime(now);
                refundOrderMapper.updateById(updateRefundOrderDO);

                RefundRecordDO refundRecordDO = new RefundRecordDO();
                refundRecordDO.setRefundOrderId(refundOrderDO.getId());
                refundRecordDO.setResultStatus(RefundRecordStatusEnum.SUCCESS);
                refundRecordDO.setEventType(notifyMessage.getEventType());
                refundRecordDO.setSummary(notifyMessage.getSummary());
                refundRecordDO.setResult(decryptStr);
                refundRecordDO.setCreateTime(now);
                refundRecordDO.setModifyTime(now);
                refundRecordMapper.insert(refundRecordDO);
                log.info("We chat notify message:[{}]", notifyMessage);

                refundRecordDTO = ThirdRefundService.convertTo(refundRecordDO);
            } else {
                RefundRecordDO refundRecordDO = new RefundRecordDO();
                refundRecordDO.setRefundOrderId(refundOrderDO.getId());
                refundRecordDO.setResultStatus(RefundRecordStatusEnum.FAIL);
                refundRecordDO.setEventType(notifyMessage.getEventType());
                refundRecordDO.setSummary(notifyMessage.getSummary());
                refundRecordDO.setResult(decryptStr);
                refundRecordDO.setCreateTime(now);
                refundRecordDO.setModifyTime(now);
                refundRecordMapper.insert(refundRecordDO);

                refundRecordDTO = ThirdRefundService.convertTo(refundRecordDO);
                log.info("We chat notify message:[{}]", notifyMessage);
            }

            // 通知下游商户
            weChatRefundService.refundNotifyDownstreamMerchant(refundOrderDO.getAppId(),
                    weChatNotifyRefundOrderInfoDTO.getOutRefundNo(), refundOrderDO.getPayOrderId(), refundRecordDTO);
        } catch (JsonProcessingException e) {
            log.error(String.format("退款通知格式错误, notify resource:[%s]", decryptStr), e);
            return NotifyResultDTO.fail();
        } catch (Exception e) {
            log.error(String.format("未知错误, notify resource:[%s]", decryptStr), e);
            return NotifyResultDTO.fail();
        }

        return NotifyResultDTO.success();
    }
}
