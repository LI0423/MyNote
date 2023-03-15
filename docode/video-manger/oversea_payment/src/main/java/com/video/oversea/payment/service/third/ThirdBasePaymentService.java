package com.video.oversea.payment.service.third;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.entity.*;
import com.video.oversea.payment.cache.PayOrderCache;
import com.video.oversea.payment.cache.PayRecordCache;
import com.video.oversea.payment.domain.dto.app.AppDTO;
import com.video.oversea.payment.domain.dto.payment.PayNotifyMessageDTO;
import com.video.oversea.payment.domain.dto.payment.PayOrderDTO;
import com.video.oversea.payment.domain.dto.payment.PayRecordDTO;
import com.video.oversea.payment.mapper.PayOrderDownstreamNotifyMapper;
import com.video.oversea.payment.mapper.PayOrderMapper;
import com.video.oversea.payment.mapper.PayRecordMapper;
import com.video.oversea.payment.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Slf4j
public abstract class ThirdBasePaymentService {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PayOrderMapper payOrderMapper;

    @Autowired
    protected PayOrderDownstreamNotifyMapper payOrderDownstreamNotifyMapper;

    @Autowired
    protected PayRecordMapper payRecordMapper;

    @Autowired
    protected AppService appService;

    @Autowired
    protected PayOrderCache payOrderCache;

    @Autowired
    protected PayRecordCache payRecordCache;

    @Autowired
    protected RestTemplate restTemplate;

    /**
     * 支付通知下游商户, 异步调用
     * @param appId app id
     * @param outTradeNo 订单id
     * @param payRecordDTO 支付记录
     */
    @Async
    public void payNotifyDownstreamMerchant(Long appId, String outTradeNo, PayRecordDTO payRecordDTO, Long startTimeMillis, Long expiryTimeMillis) {
        PayOrderDO payOrderDO =
                payOrderMapper.selectByThirdOutTradeNo(outTradeNo);

        log.info("Pay order downstream merchant start. pay order id:[{}]", payOrderDO.getId());

        PayOrderDownstreamNotifyDO payOrderDownstreamNotifyDO =
                payOrderDownstreamNotifyMapper.selectByPayOrderId(payOrderDO.getId());
        if (payOrderDownstreamNotifyDO == null) {
            log.error("Pay order downstream merchant by pay order id not found. pay order id:[{}]", payOrderDO.getId());
            return;
        }

        AppDTO appDTO = appService.find(appId);
        if (appDTO == null) {
            log.error(String.format("app[%s]数据查询不到", appId));
            return;
        }

        PayNotifyMessageDTO notifyMessageDTO = new PayNotifyMessageDTO();
        notifyMessageDTO.setPayOrderId(payOrderDO.getId());
        notifyMessageDTO.setAmount(payOrderDO.getAmount());
        notifyMessageDTO.setStatus(payRecordDTO.getResultStatus());
        notifyMessageDTO.setAppId(appId);
        notifyMessageDTO.setPkg(appDTO.getPkg());
        notifyMessageDTO.setType(payOrderDO.getType());
        notifyMessageDTO.setStartTimeMillis(startTimeMillis);
        notifyMessageDTO.setExpiryTimeMillis(expiryTimeMillis);

        // 请求下游接口
        // 重试机制
        ResponseEntity<String> responseEntity = null;
        for (int i = 0; i <= 2; i++) {
            try {
                responseEntity =
                        restTemplate.postForEntity(payOrderDownstreamNotifyDO.getNotifyUrl(),
                                notifyMessageDTO, String.class);

                log.info("Pay notify downstream result:[{}], pay order id:[{}]",
                        responseEntity.getBody(), payOrderDO.getId());

                break;
            } catch (Exception e) {
                log.error("Payment notify post request error. retry num:" + i + ", pay order id:" + payOrderDO.getId(), e);
                continue;
            }
        }

    }

    /**
     * 保存订单以及通知url信息
     * @return
     */
    protected PayOrderDTO save(String thirdMchid, String thirdAppId, PayOrderPayTypeEnum payType, Long appId, PayOrderChannelEnum channel,
                               Long userId, Integer businessType, Long amount, Date prepayExpireTime, String shortDesc, String notifyUrl) {

        Date now = new Date();
        // 预支付订单关闭时间
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setChannel(channel);
        payOrderDO.setThirdMchid(thirdMchid);
        payOrderDO.setThirdAppId(thirdAppId);
        payOrderDO.setThirdPayType(payType);
        payOrderDO.setAppId(appId);
        payOrderDO.setUserId(userId);
        payOrderDO.setType(businessType);
        payOrderDO.setAmount(amount);
        payOrderDO.setPrepayExpireTime(prepayExpireTime);
        payOrderDO.setShortDesc(shortDesc);
        payOrderDO.setCreateTime(now);
        payOrderDO.setModifyTime(now);
        payOrderMapper.insert(payOrderDO);

        PayOrderDO updateCondition = new PayOrderDO();
        updateCondition.setId(payOrderDO.getId());
        updateCondition.setThirdOutTradeNo(payOrderDO.getId().toString());
        payOrderMapper.updateById(updateCondition);

        // 记录下游商户与订单关联的信息
        PayOrderDownstreamNotifyDO payOrderDownstreamNotifyDO = new PayOrderDownstreamNotifyDO();
        payOrderDownstreamNotifyDO.setPayOrderId(payOrderDO.getId());
        payOrderDownstreamNotifyDO.setNotifyUrl(notifyUrl);
        payOrderDownstreamNotifyMapper.insert(payOrderDownstreamNotifyDO);

        return ThirdPaymentService.convertTo(payOrderDO);
    }

}
