package com.video.oversea.payment.service.third;

import com.video.oversea.payment.exception.BusinessException;
import com.video.oversea.payment.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: mango
 * @description: 第三方支付(用户给企业付款)service工厂
 * @author: wangwei
 **/
@Service
@Slf4j
public class ThirdPaymentFactoryService {

    @Autowired
    private Map<String, ThirdPaymentService> paymentServiceMap = new ConcurrentHashMap<>();

    public ThirdPaymentService getPaymentService(String serviceName){

        ThirdPaymentService thirdPaymentService=paymentServiceMap.get(serviceName);

        if(thirdPaymentService==null){
            log.error("unknown service.{}",serviceName);
            throw new BusinessException(ErrorCodeEnum.SERVICE_NOT_FOUND);
        }

        return thirdPaymentService;
    }
}
