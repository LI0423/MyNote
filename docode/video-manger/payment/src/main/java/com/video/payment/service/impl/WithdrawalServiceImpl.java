package com.video.payment.service.impl;


import com.video.entity.PayOrderChannelEnum;
import com.video.entity.WithdrawalFundDo;
import com.video.payment.domain.dto.app.AppDTO;
import com.video.payment.domain.dto.third.*;
import com.video.payment.domain.dto.user.UserAuthDTO;
import com.video.payment.domain.dto.withdrawal.WithdrawalQueryDTO;
import com.video.payment.domain.dto.withdrawal.WithdrawalResultDTO;
import com.video.payment.enums.AliPaymentBusinessSource;
import com.video.payment.exception.BusinessException;
import com.video.payment.exception.ErrorCodeEnum;
import com.video.payment.mapper.WithdrawalFundMapper;
import com.video.payment.service.AppService;
import com.video.payment.service.UserAuthService;
import com.video.payment.service.WithdrawalService;
import com.video.payment.service.third.ThirdPaymentFactoryService;
import com.video.payment.service.third.ThirdPaymentService;
import com.video.payment.util.IdentifierGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

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

    @Autowired
    protected WithdrawalFundMapper withdrawalFundMapper;

    @Override
    public WithdrawalResultDTO withdrawal(WithdrawalQueryDTO withdrawalQueryDTO) {

        UserAuthDTO userAuthDTO = userAuthService.findByUserId(withdrawalQueryDTO.getUserId());
        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
            throw new BusinessException(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }

        // 按顺序获取所有的app数据尝试提现，其中一个提现成功，则结束。(这里的逻辑需要谨慎，需要覆盖到所有需要跳出的位置)
        List<AppDTO> appPaymentInfoDTOS =
                appService.findPaymentList(withdrawalQueryDTO.getPkg(), PayOrderChannelEnum.WE_CHAT, 1);

        if (CollectionUtils.isEmpty(appPaymentInfoDTOS)) {
            throw new BusinessException(ErrorCodeEnum.APP_PAYMENT_INFO_NOT_FOUND,
                    String.format("pkg[%s]数据查询不到, pay order channel:[%s], mapping type:[%s]",
                            withdrawalQueryDTO.getPkg(),PayOrderChannelEnum.WE_CHAT, 1));
        }

        WithdrawalResultDTO withdrawalResultDTO = new WithdrawalResultDTO();
        withdrawalResultDTO.setAmount(withdrawalQueryDTO.getAmount());
        withdrawalResultDTO.setPartnerTradeNo(withdrawalQueryDTO.getPartnerTradeNo());

        boolean success = false;
        String errorCode = null;
        String errorDesc = null;

        for (AppDTO appPaymentInfoDTO : appPaymentInfoDTOS) {
            String openId = userAuthDTO.getOpenId();
            Long randomId = IdentifierGeneratorUtils.nextId();

            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPartnerTradeNo(withdrawalQueryDTO.getPartnerTradeNo());
            paymentDTO.setOpenId(openId);
            paymentDTO.setNonceStr(Long.toString(randomId));
            paymentDTO.setMchAppId(appPaymentInfoDTO.getWeChatAppId());
            paymentDTO.setMchId(appPaymentInfoDTO.getWeChatMchid());
            paymentDTO.setDesc(WITHDRAWAL_DESC);
            paymentDTO.setCheckName(PaymentCheckNameEnum.NO_CHECK);
            paymentDTO.setAmount(withdrawalQueryDTO.getAmount());

            // 获取微信支付service
            ThirdPaymentService thirdPaymentService =
                    thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");

            // 生成sign
            String sign = thirdPaymentService.generateSign(paymentDTO,
                    appPaymentInfoDTO.getWeChatAppId(), appPaymentInfoDTO.getWeChatMchid());
            paymentDTO.setSign(sign);

            log.info("Payment info:[{}], user id:[{}], pkg:[{}]", paymentDTO, withdrawalQueryDTO.getUserId(), withdrawalQueryDTO.getPkg());

            // 调用微信支付接口进行支付
            PaymentResultDTO paymentResultDTO = thirdPaymentService.pay(paymentDTO,
                    appPaymentInfoDTO.getWeChatAppId(), appPaymentInfoDTO.getWeChatMchid());

            if (paymentResultDTO == null) {
                throw new BusinessException(ErrorCodeEnum.THIRD_PAY_ERROR);
            }

            //存储流水
            WithdrawalFundDo withdrawalFundDo = new WithdrawalFundDo();
            //业务基本信息
            withdrawalFundDo.setAppId(appPaymentInfoDTO.getId());
            withdrawalFundDo.setUserId(withdrawalQueryDTO.getUserId());
            withdrawalFundDo.setAmount(withdrawalQueryDTO.getAmount());
            withdrawalFundDo.setBusinessId(withdrawalQueryDTO.getPartnerTradeNo());
            withdrawalFundDo.setBusinessSource(withdrawalQueryDTO.getBusinessSource());
            withdrawalFundDo.setChannel(PayOrderChannelEnum.WE_CHAT.getCode());

            // 根据微信支付的操作结果更新订单信息(支付成功或者支付失败)
            if (!PaymentReturnCodeEnum.SUCCESS.equals(paymentResultDTO.getReturnCode())
                    || !PaymentReturnCodeEnum.SUCCESS.equals(paymentResultDTO.getResultCode())) {
                withdrawalFundDo.setErrorCode(paymentResultDTO.getErrCode());
                withdrawalFundDo.setErrorMsg(paymentResultDTO.getErrCodeDes());
                withdrawalFundDo.setPayStatus(PaymentReturnCodeEnum.FAIL.getCode());

                success = false;
                errorCode = paymentResultDTO.getErrCode();
                errorDesc = paymentResultDTO.getErrCodeDes();
            } else {
                withdrawalFundDo.setPayOrderId(paymentResultDTO.getPaymentNo());
                withdrawalFundDo.setPayTime(paymentResultDTO.getPaymentTime());
                withdrawalFundDo.setPayStatus(PaymentReturnCodeEnum.SUCCESS.getCode());

                success = true;
            }

            withdrawalFundMapper.insert(withdrawalFundDo);

            // 遇到第一次提现成功则跳出循环
            // TODO: 后续需要支持部分出错状态的错误码也直接返回, 比如用户
            if (success) {
                break;
            }
        }

        if (!success) {
            withdrawalResultDTO.setErrorCode(errorCode);
            withdrawalResultDTO.setErrorDesc(errorDesc);
        }

        return withdrawalResultDTO;
    }

    @Override
    public WithdrawalResultDTO aliWithdrawal(WithdrawalQueryDTO withdrawalQueryDTO) {
        log.info("ali Withdrawal userId : {}, partnerTradeNo: {}", withdrawalQueryDTO.getUserId(), withdrawalQueryDTO.getPartnerTradeNo());

        //参数校验
        if(withdrawalQueryDTO.getIdentity() == null
                || withdrawalQueryDTO.getIdentityName() == null
                || withdrawalQueryDTO.getPayerShowName() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }

        withdrawalQueryDTO.setPayBusinessId(withdrawalQueryDTO.getPartnerTradeNo() + "-" + withdrawalQueryDTO.getBusinessSource());

        UserAuthDTO userAuthDTO = userAuthService.findByUserId(withdrawalQueryDTO.getUserId());
        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
            throw new BusinessException(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }

//        // 获取app配置，顺便判断pkg的合法性
//        AppDTO appDTO = appService.findRand(withdrawalQueryDTO.getPkg(), PayOrderChannelEnum.ALI_PAY, 1);
//        if (appDTO == null) {
//            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
//                    String.format("pkg[%s]数据查询不到", withdrawalQueryDTO.getPkg()));
//        }

        if(null == withdrawalQueryDTO.getBusinessSource()  || null == AliPaymentBusinessSource.getSource(withdrawalQueryDTO.getBusinessSource())) {
            throw new BusinessException(ErrorCodeEnum.PAY_BUSINESS_ERR);
        }

        // 按顺序获取所有的app数据尝试提现，其中一个提现成功，则结束。(这里的逻辑需要谨慎，需要覆盖到所有需要跳出的位置)
        List<AppDTO> appPaymentInfoDTOS =
                appService.findPaymentList(withdrawalQueryDTO.getPkg(), PayOrderChannelEnum.ALI_PAY, 1);

        if (CollectionUtils.isEmpty(appPaymentInfoDTOS)) {
            throw new BusinessException(ErrorCodeEnum.APP_PAYMENT_INFO_NOT_FOUND,
                    String.format("pkg[%s]数据查询不到, pay order channel:[%s], mapping type:[%s]",
                            withdrawalQueryDTO.getPkg(),PayOrderChannelEnum.WE_CHAT, 1));
        }

        WithdrawalResultDTO withdrawalResultDTO = new WithdrawalResultDTO();
        withdrawalResultDTO.setAmount(withdrawalQueryDTO.getAmount());
        withdrawalResultDTO.setPartnerTradeNo(withdrawalQueryDTO.getPartnerTradeNo());

        boolean success = false;
        String errorCode = null;
        String errorDesc = null;

        for (AppDTO appPaymentInfoDTO : appPaymentInfoDTOS) {

            //转账
            ThirdPaymentService thirdPaymentService = thirdPaymentFactoryService.getPaymentService("ALI_PAY_PAYMENT");
            AliPaymentResultDTO aliPaymentResultDTO = thirdPaymentService.alPay(withdrawalQueryDTO, appPaymentInfoDTO.getAliPayAppId());

            if (aliPaymentResultDTO == null) {
                throw new BusinessException(ErrorCodeEnum.THIRD_PAY_ERROR);
            }

            //存储流水
            WithdrawalFundDo withdrawalFundDo = new WithdrawalFundDo();
            //业务基本信息
            withdrawalFundDo.setAppId(appPaymentInfoDTO.getId());
            withdrawalFundDo.setUserId(withdrawalQueryDTO.getUserId());
            withdrawalFundDo.setAmount(withdrawalQueryDTO.getAmount());
            withdrawalFundDo.setBusinessId(withdrawalQueryDTO.getPartnerTradeNo());
            withdrawalFundDo.setBusinessSource(withdrawalQueryDTO.getBusinessSource());
            withdrawalFundDo.setChannel(PayOrderChannelEnum.ALI_PAY.getCode());
            withdrawalFundDo.setPayBusinessId(withdrawalQueryDTO.getPayBusinessId());
            //支付信息
            withdrawalFundDo.setPayStatus(aliPaymentResultDTO.getPayStatus());
            if (aliPaymentResultDTO.getPayStatus() == PaymentReturnCodeEnum.SUCCESS.getCode()) {
                withdrawalFundDo.setPayOrderFundId(aliPaymentResultDTO.getPayFundOrderId());
                withdrawalFundDo.setPayOrderId(aliPaymentResultDTO.getOrderId());
                withdrawalFundDo.setPayTime(aliPaymentResultDTO.getTransDate());

                success = true;
            } else {
                withdrawalFundDo.setErrorCode(aliPaymentResultDTO.getSubCode());
                withdrawalFundDo.setErrorMsg(aliPaymentResultDTO.getSubMsg());

                success = false;
                errorCode = aliPaymentResultDTO.getSubCode();
                errorDesc = aliPaymentResultDTO.getSubMsg();
            }

            withdrawalFundMapper.insert(withdrawalFundDo);

            // 遇到第一次提现成功则跳出循环
            // TODO: 后续需要支持部分出错状态的错误码也直接返回, 比如用户
            if (success) {
                break;
            }
        }

        if (!success) {
            withdrawalResultDTO.setErrorCode(errorCode);
            withdrawalResultDTO.setErrorDesc(errorDesc);
        }

        return withdrawalResultDTO;
    }
}
