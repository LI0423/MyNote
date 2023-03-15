package com.video.oversea.payment.controller;

import com.video.entity.PayOrderChannelEnum;
import com.video.oversea.payment.domain.common.ResponseResult;
import com.video.oversea.payment.domain.dto.payment.PayOrderDTO;
import com.video.oversea.payment.domain.dto.payment.PrepayRequestDTO;
import com.video.oversea.payment.service.third.ThirdPaymentService;
import com.video.oversea.payment.constant.BusinessParamConstants;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController extends BaseController {

    @PostMapping("/prepay")
    public ResponseResult<PayOrderDTO> prepay(
            @RequestParam(BusinessParamConstants.PKG) String pkg,
            @RequestBody PrepayRequestDTO prepayRequestDTO) {

        ThirdPaymentService thirdPaymentService;
        if (prepayRequestDTO.getChannel() == PayOrderChannelEnum.WE_CHAT) {
            thirdPaymentService = thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");
        } else if (prepayRequestDTO.getChannel() == PayOrderChannelEnum.ALI_PAY) {
            thirdPaymentService = thirdPaymentFactoryService.getPaymentService("ALI_PAY_PAYMENT");
        } else if (prepayRequestDTO.getChannel() == PayOrderChannelEnum.GOOGLE_PAY) {
            thirdPaymentService = thirdPaymentFactoryService.getPaymentService("GOOGLE_PAYMENT");
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

}
