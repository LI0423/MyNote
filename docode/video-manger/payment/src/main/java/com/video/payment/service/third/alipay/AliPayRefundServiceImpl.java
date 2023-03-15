package com.video.payment.service.third.alipay;

import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.video.entity.PayOrderChannelEnum;
import com.video.entity.RefundOrderDO;
import com.video.entity.RefundOrderDownstreamNotifyDO;
import com.video.entity.RefundOrderStatusEnum;
import com.video.entity.RefundRecordDO;
import com.video.entity.RefundRecordStatusEnum;
import com.video.payment.domain.dto.app.AppDTO;
import com.video.payment.domain.dto.payment.NotifyResultDTO;
import com.video.payment.domain.dto.payment.PayOrderDTO;
import com.video.payment.domain.dto.refund.RefundNotifyMessageDTO;
import com.video.payment.domain.dto.refund.RefundOrderDTO;
import com.video.payment.domain.dto.refund.RefundRecordDTO;
import com.video.payment.domain.dto.third.AliPayNotifyMessageDTO;
import com.video.payment.domain.dto.third.AliPayTradeStatusEnum;
import com.video.payment.exception.BusinessException;
import com.video.payment.exception.ErrorCodeEnum;
import com.video.payment.mapper.RefundOrderDownstreamNotifyMapper;
import com.video.payment.mapper.RefundOrderMapper;
import com.video.payment.mapper.RefundRecordMapper;
import com.video.payment.service.third.ThirdRefundService;
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
import java.util.Objects;

@Slf4j
@Service("ALI_PAY_REFUND")
public class AliPayRefundServiceImpl extends AliPayBasePaymentService implements ThirdRefundService<AliPayNotifyMessageDTO> {

    @Qualifier("ALI_PAY_PAYMENT")
    @Autowired
    private AliPayPaymentServiceImpl aliPayPaymentService;

    @Autowired
    private RefundOrderMapper refundOrderMapper;

    @Autowired
    private RefundRecordMapper refundRecordMapper;

    @Autowired
    private RefundOrderDownstreamNotifyMapper refundOrderDownstreamNotifyMapper;

    @Qualifier("ALI_PAY_REFUND")
    @Autowired
    private AliPayRefundServiceImpl aliPayRefundService;

    @Override
    public RefundOrderDTO refund(String pkg, Long payOrderId, Integer amount, String reason, String notifyUrl) {

        if (StringUtils.isBlank(notifyUrl)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR, "必要请求参数notifyUrl没找到");
        }

        try {
            URL url = new URL(notifyUrl);
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR,
                    String.format("请求参数notifyUrl格式错误, notifyUrl:[%s]", notifyUrl));
        }

        PayOrderDTO payOrderDTO = aliPayPaymentService.find(payOrderId);

        if (Objects.isNull(payOrderDTO)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR,
                    String.format("支付订单信息未找到, pay order id:[%s]", payOrderId));
        }

        AppDTO appDTO = appService.findByAliAppId(payOrderDTO.getThirdAppId());
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("ali payment app id config[%s]数据查询不到", payOrderDTO.getThirdAppId()));
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
        refundOrderDO.setChannel(PayOrderChannelEnum.ALI_PAY);
        refundOrderDO.setStatus(RefundOrderStatusEnum.UNDONE);
        refundOrderMapper.insert(refundOrderDO);

        RefundOrderDownstreamNotifyDO refundOrderDownstreamNotifyDO = new RefundOrderDownstreamNotifyDO();
        refundOrderDownstreamNotifyDO.setRefundOrderId(refundOrderDO.getId());
        refundOrderDownstreamNotifyDO.setNotifyUrl(notifyUrl);
        refundOrderDownstreamNotifyDO.setCreateTime(now);
        refundOrderDownstreamNotifyDO.setModifyTime(now);
        refundOrderDownstreamNotifyMapper.insert(refundOrderDownstreamNotifyDO);

        // 开始构建ali退款请求的参数
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel alipayTradeRefundModel = new AlipayTradeRefundModel();
        Double refundAmount = amount / 100.00;
        alipayTradeRefundModel.setRefundAmount(refundAmount.toString());
        alipayTradeRefundModel.setOutTradeNo(payOrderDTO.getThirdOutTradeNo());
        alipayTradeRefundModel.setOutRequestNo(refundOrderDO.getId().toString());
        request.setBizModel(alipayTradeRefundModel);

        long aliPayRefundRequestStartTime = System.currentTimeMillis();
        AlipayTradeRefundResponse alipayTradeRefundResponse = certificateExecute(payOrderDTO.getThirdAppId(), request);
        long aliPayRefundRequestEndTime = System.currentTimeMillis();

        log.info("Create refund order time consuming [{}], user_id:[{}], app_id:[{}], third_app_id:[{}]" +
                        "amount:[{}], pay_type:[{}], business_type:[{}], reason:[{}]",
                (aliPayRefundRequestEndTime - aliPayRefundRequestStartTime), payOrderDTO.getId(), appDTO.getId(),
                appDTO.getAliPayAppId(), amount, payOrderDTO.getThirdPayType(), payOrderDTO.getType(), reason);

        if (Objects.isNull(alipayTradeRefundResponse) || !alipayTradeRefundResponse.isSuccess()) {
            RefundRecordDO refundRecordDO = new RefundRecordDO();
            refundRecordDO.setRefundOrderId(refundOrderDO.getId());
            refundRecordDO.setResultStatus(RefundRecordStatusEnum.OTHER);
            refundRecordDO.setResult(alipayTradeRefundResponse.getMsg());
            refundRecordDO.setThirdErrorCode(alipayTradeRefundResponse.getCode());
            refundRecordDO.setThirdErrorMsg(alipayTradeRefundResponse.getMsg());
            refundRecordDO.setCreateTime(now);
            refundRecordDO.setModifyTime(now);
            refundRecordMapper.insert(refundRecordDO);
        } else {
            RefundOrderDO updateRefundOrderDO = new RefundOrderDO();
            updateRefundOrderDO.setId(refundOrderDO.getId());
            updateRefundOrderDO.setThirdOutRefundNo(refundOrderDO.getId().toString());

            refundOrderMapper.updateById(updateRefundOrderDO);

            refundOrderDO = refundOrderMapper.selectById(refundOrderDO.getId());

            return ThirdRefundService.convertTo(refundOrderDO);
        }
        return null;
    }

    @Override
    public RefundOrderDTO findByPayOrderId(Long payOrderId) {
        RefundOrderDO refundOrderDO = refundOrderMapper.selectByPayOrderId(payOrderId);
        return ThirdRefundService.convertTo(refundOrderDO);
    }

    @Override
    public RefundOrderDTO findByRefundOrderId(Long refundOrderId) {
        RefundOrderDO refundOrderDO = refundOrderMapper.selectById(refundOrderId);
        return ThirdRefundService.convertTo(refundOrderDO);
    }

    @Override
    public NotifyResultDTO refundNotifyProcess(AliPayNotifyMessageDTO notifyMessage) {
        if (Objects.isNull(notifyMessage)) {
            return NotifyResultDTO.fail();
        }

        RefundOrderDO refundOrderDO =
                refundOrderMapper.selectById(Long.parseLong(notifyMessage.getOutBizNo()));

        if (refundOrderDO == null) {
            log.warn("退款订单未找到, out biz no:[{}], resource:[{}]",
                    notifyMessage.getOutBizNo(), notifyMessage);
            return NotifyResultDTO.success();
        }

        if (RefundOrderStatusEnum.SUCCESS.equals(refundOrderDO.getStatus())) {
            log.info("退款订单已完成, out biz no:[{}], resource:[{}]",
                    notifyMessage.getOutBizNo(), notifyMessage);
            return NotifyResultDTO.success();
        }

        Date now = new Date();
        RefundRecordDTO refundRecordDTO = new RefundRecordDTO();
        String result = null;
        try {
            result = objectMapper.writeValueAsString(notifyMessage);
        } catch (JsonProcessingException e) {
            log.error(String.format("退款通知格式错误, notify resource:[%s]", notifyMessage), e);
            return NotifyResultDTO.fail();
        }

        if ((AliPayTradeStatusEnum.TRADE_SUCCESS.name().equals(notifyMessage.getTradeStatus()) ||
                AliPayTradeStatusEnum.TRADE_CLOSED.name().equals(notifyMessage.getTradeStatus())) &&
                !RefundOrderStatusEnum.SUCCESS.equals(refundOrderDO.getStatus())) {
            RefundOrderDO updateRefundOrder = new RefundOrderDO();
            updateRefundOrder.setId(refundOrderDO.getId());
            updateRefundOrder.setStatus(RefundOrderStatusEnum.SUCCESS);
            updateRefundOrder.setCompletionTime(now);
            refundOrderMapper.updateById(updateRefundOrder);

            RefundRecordDO refundRecordDO = new RefundRecordDO();
            refundRecordDO.setRefundOrderId(refundOrderDO.getId());
            refundRecordDO.setResultStatus(RefundRecordStatusEnum.SUCCESS);
            refundRecordDO.setEventType(notifyMessage.getNotifyType());
            refundRecordDO.setSummary(StringUtils.EMPTY);
            refundRecordDO.setResult(result);
            refundRecordDO.setCreateTime(now);
            refundRecordDO.setModifyTime(now);
            refundRecordMapper.insert(refundRecordDO);
            log.info("Ali refund notify message:[{}]", notifyMessage);

            refundRecordDTO = ThirdRefundService.convertTo(refundRecordDO);
        } else {
            RefundRecordDO refundRecordDO = new RefundRecordDO();
            refundRecordDO.setRefundOrderId(refundOrderDO.getId());
            refundRecordDO.setResultStatus(RefundRecordStatusEnum.FAIL);
            refundRecordDO.setEventType(notifyMessage.getNotifyType());
            refundRecordDO.setSummary(StringUtils.EMPTY);
            refundRecordDO.setResult(result);
            refundRecordDO.setCreateTime(now);
            refundRecordDO.setModifyTime(now);
            refundRecordMapper.insert(refundRecordDO);

            refundRecordDTO = ThirdRefundService.convertTo(refundRecordDO);
            log.info("Ali refund notify message:[{}]", notifyMessage);
        }

        // 通知下游商户
        aliPayRefundService.refundNotifyDownstreamMerchant(refundOrderDO.getAppId(), notifyMessage.getOutBizNo(), refundOrderDO.getPayOrderId(), refundRecordDTO);

        return NotifyResultDTO.success();
    }

    /**
     * 退款通知下游商户, 异步调用
     * @param appId app id
     * @param outRefundNo 订单id
     * @param refundRecordDTO 支付记录
     */
    @Async
    public void refundNotifyDownstreamMerchant(Long appId, String outRefundNo, Long payOrderId, RefundRecordDTO refundRecordDTO) {
        RefundOrderDO refundOrderDO = refundOrderMapper.selectById(refundRecordDTO.getRefundOrderId());

        log.info("Refund order downstream merchant start. Refund order id:[{}]", refundOrderDO.getId());

        RefundOrderDownstreamNotifyDO refundOrderDownstreamNotifyDO = refundOrderDownstreamNotifyMapper.selectByRefundOrderId(refundOrderDO.getId());
        if (refundOrderDownstreamNotifyDO == null) {
            log.error("Refund order downstream merchant by pay order id not found. Refund order id:[{}]", refundOrderDO.getId());
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
                responseEntity.getBody(), outRefundNo);

    }
}
