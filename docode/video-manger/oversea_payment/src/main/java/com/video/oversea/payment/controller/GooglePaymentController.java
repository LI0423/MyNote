package com.video.oversea.payment.controller;

import com.video.entity.GooglePayOrderDO;
import com.video.oversea.payment.domain.common.ResponseResult;
import com.video.oversea.payment.domain.dto.payment.PrepayRequestDTO;
import com.video.oversea.payment.service.third.google.GooglePaymentService;
import com.video.oversea.payment.constant.BusinessParamConstants;
import com.video.oversea.payment.exception.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment/google")
public class GooglePaymentController extends BaseController {

    @Autowired
    private GooglePaymentService googlePaymentService;

    /**
     * 用于客户端订阅支付完成后的校验
     *
     * @param pkg
     * @param originalJson 谷歌返回给客户端的订单json信息
     * @param signature 谷歌返回的加密信息
     * @param userId
     * @param thirdOutTradeNo 本地生成的订单id
     */
    @PostMapping("/subscribe")
    public ResponseResult googleSubPrepay(@RequestParam(BusinessParamConstants.PKG) String pkg,
                                          @RequestParam("originalJson") String originalJson,
                                          @RequestParam("signature") String signature,
                                          @RequestParam("userId") Long userId,
                                          @RequestParam("thirdOutTradeNo") String thirdOutTradeNo) {

        Boolean subscribe = googlePaymentService.subscribe(pkg, originalJson, signature, userId, thirdOutTradeNo);
        if (!subscribe) {
            return ResponseResult.failure(ErrorCodeEnum.PAY_GOOGLE_ORDER_CHECK_ERROR);
        }
        return ResponseResult.success(ErrorCodeEnum.SUCCESS);
    }

    /**
     * 用于直接购买
     * @param pkg
     * @param originalJson
     * @param signature
     * @param prepayRequestDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/pay")
    public ResponseResult googlePrepay(@RequestParam(BusinessParamConstants.PKG) String pkg,
                                       @RequestParam("originalJson") String originalJson,
                                       @RequestParam("signature") String signature,
                                       @RequestBody PrepayRequestDTO prepayRequestDTO) throws Exception{

        GooglePayOrderDO googlePayOrderDO = googlePaymentService.pay(prepayRequestDTO.getUserId(), pkg,
                prepayRequestDTO.getAmount(), prepayRequestDTO.getPayType(),
                prepayRequestDTO.getBusinessType(), prepayRequestDTO.getOrderDesc(),
                prepayRequestDTO.getNotifyUrl(),originalJson, signature);
        return ResponseResult.success(googlePayOrderDO);
    }
}
