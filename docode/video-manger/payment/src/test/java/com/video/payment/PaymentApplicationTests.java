package com.video.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.payment.service.third.wechat.WeChatPaymentServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

//@SpringBootTest
public class PaymentApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private ThirdPaymentFactoryService thirdPaymentFactoryService;

    @Qualifier("WE_CHART_PAYMENT")
    @Autowired
    private WeChatPaymentServiceImpl weChatPaymentService;

    @Test
    public void test() throws JsonProcessingException {
//        WeChatPaymentRequestDTO weChatPaymentRequestDTO =
//                WeChatPaymentRequestDTO.builder().timeExpire(new Date()).build();
//        System.out.println(objectMapper.writeValueAsString(weChatPaymentRequestDTO));

//        ThirdPaymentService paymentService =
//                thirdPaymentFactoryService.getPaymentService("WE_CHART_PAYMENT");

//        PayOrderDTO payOrderDTO =
//                weChatPaymentService.prepay(1437360833242873858L,
//                        1427919686070202369L, 50L, PayOrderPayTypeEnum.APP,
//                        0, "微信下的预支付测试", "xxx");
//
//        System.out.println(payOrderDTO);
//
//        payOrderDTO = weChatPaymentService.tryUpdateFromThirdInfo(payOrderDTO.getId());
//
//        System.out.println(objectMapper.writeValueAsString(payOrderDTO));
        String filename= RandomStringUtils.randomAlphanumeric(32);
        System.out.println(filename);

        System.out.println(System.currentTimeMillis());
    }
}
