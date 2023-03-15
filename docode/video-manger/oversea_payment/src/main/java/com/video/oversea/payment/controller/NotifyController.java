package com.video.oversea.payment.controller;

import com.video.oversea.payment.domain.dto.payment.NotifyResultDTO;
import com.video.oversea.payment.service.third.google.GooglePaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知接口
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/notify")
public class NotifyController extends BaseController {

    @Qualifier(value = "GOOGLE_PAYMENT")
    @Autowired
    private GooglePaymentService googlePaymentService;

    /**
     * google系统自动订阅的回调接口
     * @param eventJson
     * @return
     */
    @PostMapping("/googleSubscribeNotify")
    public NotifyResultDTO googleSubscribeNotify(@RequestBody String eventJson) {

        googlePaymentService.googleSubscribeNotify(eventJson);
        return NotifyResultDTO.success();
    }
}
