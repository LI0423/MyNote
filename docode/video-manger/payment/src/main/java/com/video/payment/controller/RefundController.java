package com.video.payment.controller;

import com.video.entity.PayOrderChannelEnum;
import com.video.payment.constant.BusinessParamConstants;
import com.video.payment.constant.BusinessPathVariableConstants;
import com.video.payment.domain.common.ResponseResult;
import com.video.payment.domain.dto.payment.PayOrderDTO;
import com.video.payment.domain.dto.refund.RefundOrderDTO;
import com.video.payment.domain.dto.refund.RefundRequestDTO;
import com.video.payment.service.third.ThirdPaymentService;
import com.video.payment.service.third.ThirdRefundService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/refund")
public class RefundController extends BaseController {

    /**
     * 退款接口
     * @param pkg app pkg
     * @param refundRequestDTO 退款请求体
     * @return 退款单信息
     */
    @PostMapping
    public ResponseResult<RefundOrderDTO> refund(@RequestParam(BusinessParamConstants.PKG) String pkg,
                                                 @RequestBody RefundRequestDTO refundRequestDTO) {

//        AppDTO appDTO = appService.find(pkg);
//        if (appDTO == null) {
//            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
//                    String.format("app[%s]数据查询不到", pkg));
//        }

        ThirdPaymentService thirdPaymentService =
                thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");
        PayOrderDTO payOrderDTO = thirdPaymentService.find(refundRequestDTO.getPayOrderId());

        ThirdRefundService thirdRefundService =
                thirdRefundFactoryService.getRefundService("WE_CHART_REFUND");
        if (payOrderDTO.getChannel() == PayOrderChannelEnum.ALI_PAY) {
            thirdRefundService = thirdRefundFactoryService.getRefundService("ALI_PAY_REFUND");
        }

        RefundOrderDTO refundOrderDTO = thirdRefundService.refund(
                pkg, refundRequestDTO.getPayOrderId(),
                refundRequestDTO.getAmount(), refundRequestDTO.getReason(),
                refundRequestDTO.getNotifyUrl());

        return ResponseResult.success(refundOrderDTO);
    }

    @GetMapping("/{id}")
    public ResponseResult<RefundOrderDTO> find(@RequestParam(BusinessParamConstants.PKG) String pkg,
                                               @PathVariable(BusinessPathVariableConstants.ID) Long id) {

//        AppDTO appDTO = appService.find(pkg);
//        if (appDTO == null) {
//            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
//                    String.format("app[%s]数据查询不到", pkg));
//        }

        ThirdRefundService thirdRefundService =
                thirdRefundFactoryService.getRefundService("WE_CHART_REFUND");

        RefundOrderDTO refundOrderDTO = thirdRefundService.findByRefundOrderId(id);

        return ResponseResult.success(refundOrderDTO);
    }
}
