package com.video.payment.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.parser.json.JsonConverter;
import com.video.payment.domain.dto.payment.NotifyResultDTO;
import com.video.payment.domain.dto.third.AliPayNotifyMessageDTO;
import com.video.payment.domain.dto.third.WeChatNotifyMessageDTO;
import com.video.payment.service.third.ThirdPaymentService;
import com.video.payment.service.third.ThirdRefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 通知接口
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/notify")
public class NotifyController extends BaseController {

    /**
     * 支付通知
     * @param weChatNotifyMessageDTO 通知请求体
     * @return 通知支付结果
     */
    @PostMapping("/payment")
    public NotifyResultDTO paymentNotify(
            @RequestBody WeChatNotifyMessageDTO weChatNotifyMessageDTO) {

        ThirdPaymentService thirdPaymentService =
                thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");

        NotifyResultDTO notifyResultDTO =
                thirdPaymentService.payResultCallback(weChatNotifyMessageDTO);

        return notifyResultDTO;
    }

    /**
     * 退款通知
     * @param weChatNotifyMessageDTO
     * @return 通知支付结果
     */
    @PostMapping("/refund")
    public NotifyResultDTO refundNotify(
            @RequestBody WeChatNotifyMessageDTO weChatNotifyMessageDTO) {

        ThirdRefundService thirdRefundService =
                thirdRefundFactoryService.getRefundService("WE_CHART_REFUND");

        NotifyResultDTO notifyResultDTO =
                thirdRefundService.refundNotifyProcess(weChatNotifyMessageDTO);

        return notifyResultDTO;
    }

    /**
     * 支付通知
     * @return 通知支付结果
     */
    @PostMapping("/alipay/payment")
    public NotifyResultDTO alipayPaymentNotify(HttpServletRequest request) {

        // 获取请求参数
        Map<String,String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        Optional.ofNullable(requestParams)
                .orElse(new HashMap<>()).entrySet().stream().forEach(
                stringEntry -> {
                    String key = stringEntry.getKey();
                    String[] values = stringEntry.getValue();
                    String valueStr = "";
                    for (int i = 0; i < values.length; i++) {
                        valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                    }
                    //乱码解决，这段代码在出现乱码时使用。
                    //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                    params.put(key, valueStr);
                }
                );

        log.info("Notify params: " + params.toString());

        // 构建notify message对象
        JsonConverter jsonConverter = new JsonConverter();
        AliPayNotifyMessageDTO aliPayNotifyMessageDTO = null;
        try {
            aliPayNotifyMessageDTO = jsonConverter.fromJson(params, AliPayNotifyMessageDTO.class);
        } catch (AlipayApiException e) {

            log.error(String.format("Alipay payment notify error. message:[{}]", params.toString()), e);

            return NotifyResultDTO.fail();
        }

        ThirdPaymentService thirdPaymentService =
                thirdPaymentFactoryService.getPaymentService("ALI_PAY_PAYMENT");

        // 签名检查
        if (!thirdPaymentService.signCheck(params)) {
            log.info("Alipay payment notify error.");
            return NotifyResultDTO.fail();
        }

        NotifyResultDTO notifyResultDTO;

        // 回调处理
        if (aliPayNotifyMessageDTO.getRefundFee() != null) {
            ThirdRefundService thirdRefundService =
                    thirdRefundFactoryService.getRefundService("ALI_PAY_REFUND");
            notifyResultDTO = thirdRefundService.refundNotifyProcess(aliPayNotifyMessageDTO);
        } else {
            notifyResultDTO =
                    thirdPaymentService.payResultCallback(aliPayNotifyMessageDTO);
        }

        log.info("notify params:" + request.getQueryString());
        return notifyResultDTO;
    }

    /**
     * 退款通知
     * @param weChatNotifyMessageDTO
     * @return 通知支付结果
     */
    @PostMapping("/alipay/refund")
    public NotifyResultDTO alipayRefundNotify(
            @RequestBody WeChatNotifyMessageDTO weChatNotifyMessageDTO) {

        ThirdRefundService thirdRefundService =
                thirdRefundFactoryService.getRefundService("ALI_PAY_PAYMENT");

        NotifyResultDTO notifyResultDTO =
                thirdRefundService.refundNotifyProcess(weChatNotifyMessageDTO);

        return NotifyResultDTO.fail();
    }
}
