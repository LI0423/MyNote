package com.video.withdrawal.service.impl;

import com.video.withdrawal.domain.dto.app.AppDTO;
import com.video.withdrawal.domain.dto.third.PaymentCheckNameEnum;
import com.video.withdrawal.domain.dto.third.PaymentDTO;
import com.video.withdrawal.domain.dto.third.PaymentResultDTO;
import com.video.withdrawal.domain.dto.third.PaymentReturnCodeEnum;
import com.video.withdrawal.domain.dto.user.UserAuthDTO;
import com.video.withdrawal.domain.dto.withdrawal.WithdrawalResultDTO;
import com.video.withdrawal.exception.BusinessException;
import com.video.withdrawal.exception.ErrorCodeEnum;
import com.video.withdrawal.service.AppService;
import com.video.withdrawal.service.UserAuthService;
import com.video.withdrawal.service.WithdrawalService;
import com.video.withdrawal.service.third.ThirdPaymentFactoryService;
import com.video.withdrawal.service.third.ThirdPaymentService;
import com.video.withdrawal.util.IdentifierGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    private static final String WITHDRAWAL_DESC = "任务奖励";

    @Autowired
    protected UserAuthService userAuthService;

    @Autowired
    private ThirdPaymentFactoryService thirdPaymentFactoryService;

    @Autowired
    private AppService appService;

    @Override
    public WithdrawalResultDTO withdrawal(Long userId, String pkg, Long amount, String partnerTradeNo) {

        UserAuthDTO userAuthDTO = userAuthService.findByUserId(userId);
        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
            throw new BusinessException(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }

        // 获取app配置，顺便判断pkg的合法性
        AppDTO appDTO = appService.find(pkg);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND);
        }

        String openId = userAuthDTO.getOpenId();
        Long randomId = IdentifierGeneratorUtils.nextId();

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPartnerTradeNo(partnerTradeNo);
        paymentDTO.setOpenId(openId);
        paymentDTO.setNonceStr(Long.toString(randomId));
        paymentDTO.setMchAppId(appDTO.getWeChatAppId());
        paymentDTO.setMchId(appDTO.getWeChatMchid());
        paymentDTO.setDesc(WITHDRAWAL_DESC);
        paymentDTO.setCheckName(PaymentCheckNameEnum.NO_CHECK);
        paymentDTO.setAmount(amount);

        // 获取微信支付service
        ThirdPaymentService thirdPaymentService =
                thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");

        // 生成sign
        String sign = thirdPaymentService.generateSign(paymentDTO, pkg);
        paymentDTO.setSign(sign);

        log.info("Payment info:[{}], user id:[{}], pkg:[{}]", paymentDTO, userId, pkg);
        // 调用微信支付接口进行支付
        PaymentResultDTO paymentResultDTO = thirdPaymentService.pay(paymentDTO, pkg);

        if (paymentResultDTO == null) {
            throw new BusinessException(ErrorCodeEnum.THIRD_PAY_ERROR);
        }

        WithdrawalResultDTO withdrawalResultDTO = new WithdrawalResultDTO();
        withdrawalResultDTO.setAmount(amount);
        withdrawalResultDTO.setPartnerTradeNo(partnerTradeNo);

        // 根据微信支付的操作结果更新订单信息(支付成功或者支付失败)
        if (!PaymentReturnCodeEnum.SUCCESS.equals(paymentResultDTO.getReturnCode())
                || !PaymentReturnCodeEnum.SUCCESS.equals(paymentResultDTO.getResultCode())) {
            withdrawalResultDTO.setErrorCode(paymentResultDTO.getErrCode());
            withdrawalResultDTO.setErrorDesc(paymentResultDTO.getErrCodeDes());
        }

        return withdrawalResultDTO;
    }
}
