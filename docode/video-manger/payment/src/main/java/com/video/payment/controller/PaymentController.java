package com.video.payment.controller;

import com.video.entity.PayOrderChannelEnum;
import com.video.payment.constant.BusinessParamConstants;
import com.video.payment.constant.BusinessPathVariableConstants;
import com.video.payment.domain.common.ResponseResult;
import com.video.payment.domain.dto.payment.PayOrderDTO;
import com.video.payment.domain.dto.payment.PaymentQuerySignDTO;
import com.video.payment.domain.dto.payment.PrepayRequestDTO;
import com.video.payment.exception.BusinessException;
import com.video.payment.exception.ErrorCodeEnum;
import com.video.payment.service.PayOrderService;
import com.video.payment.service.third.ThirdPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController extends BaseController {

    @Autowired
    private PayOrderService payOrderService;

    @PostMapping("/prepay")
    public ResponseResult<PayOrderDTO> prepay(
            @RequestParam(BusinessParamConstants.PKG) String pkg,
            @RequestBody PrepayRequestDTO prepayRequestDTO) {

        ThirdPaymentService thirdPaymentService;
        if (prepayRequestDTO.getChannel() == PayOrderChannelEnum.WE_CHAT) {
            thirdPaymentService = thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");
        } else if (prepayRequestDTO.getChannel() == PayOrderChannelEnum.ALI_PAY) {
            thirdPaymentService = thirdPaymentFactoryService.getPaymentService("ALI_PAY_PAYMENT");
        } else {
            thirdPaymentService = thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");
        }

        PayOrderDTO payOrderDTO = thirdPaymentService.prepay(
                prepayRequestDTO.getUserId(), pkg,
                prepayRequestDTO.getAmount(), prepayRequestDTO.getPayType(),
                prepayRequestDTO.getBusinessType(), prepayRequestDTO.getOrderDesc(),
                prepayRequestDTO.getNotifyUrl());

        return ResponseResult.success(payOrderDTO);
    }

    @GetMapping("/{id}")
    public ResponseResult<PayOrderDTO> find(@RequestParam(BusinessParamConstants.PKG) String pkg,
                                            @PathVariable(BusinessPathVariableConstants.ID) Long id) {

        ThirdPaymentService thirdPaymentService =
                thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");

        PayOrderDTO payOrderDTO = thirdPaymentService.tryUpdateFromThirdInfo(id);

        return ResponseResult.success(payOrderDTO);
    }

    @GetMapping("/sign/{id}")
    public ResponseResult<PaymentQuerySignDTO> findSign(@RequestParam(BusinessParamConstants.PKG) String pkg,
                                                        @RequestParam(value = BusinessParamConstants.CHANNEL, required = false) Integer channel,
                                                        @PathVariable(BusinessPathVariableConstants.ID) Long id) {

        PayOrderChannelEnum payOrderChannel = PayOrderChannelEnum.WE_CHAT;

        if (Objects.nonNull(channel)) {
            payOrderChannel = PayOrderChannelEnum.valueOf(channel);
        }

        if (Objects.isNull(payOrderChannel)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }


//        PayOrderDTO payOrderDTO = payOrderService.find(id);
//        if (Objects.isNull(payOrderDTO)) {
//            throw new BusinessException(ErrorCodeEnum.RESOURCE_NOT_FOUND,
//                    String.format("pay order[%s]数据查询不到", id));
//        }

//        AppDTO appDTO;
//        if (payOrderChannel == PayOrderChannelEnum.WE_CHAT) {
//            appDTO = appService.findByWeChatMchid(payOrderDTO.getThirdAppId(), payOrderDTO.getThirdMchid());
//        } else if (payOrderChannel == PayOrderChannelEnum.ALI_PAY){
//            appDTO = appService.findByThirdAppId(payOrderDTO.getThirdAppId(), PayOrderChannelEnum.ALI_PAY);
//        } else {
//            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR, String.format("channel[%s]数据查不到", channel));
//        }
//        if (appDTO == null) {
//            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
//                    String.format("app[%s]数据查询不到"));
//        }

        ThirdPaymentService thirdPaymentService;
        if (payOrderChannel == PayOrderChannelEnum.WE_CHAT) {
            thirdPaymentService = thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");
        } else if (payOrderChannel == PayOrderChannelEnum.ALI_PAY) {
            thirdPaymentService = thirdPaymentFactoryService.getPaymentService("ALI_PAY_PAYMENT");
        } else {
            thirdPaymentService = thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");
        }

        PaymentQuerySignDTO sign = thirdPaymentService.sign(id);

        return ResponseResult.success(sign);
    }
}
