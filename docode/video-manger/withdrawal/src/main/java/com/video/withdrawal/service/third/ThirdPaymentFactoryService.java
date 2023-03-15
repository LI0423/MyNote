package com.video.withdrawal.service.third;

import com.video.withdrawal.exception.BusinessException;
import com.video.withdrawal.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: mango
 * @description: 第三方支付service工厂
 * @author: laojiang
 * @create: 2020-09-11 13:56
 **/
@Service
@Slf4j
public class ThirdPaymentFactoryService {

    @Autowired
    private Map<String, ThirdPaymentService> paymentServiceMap=new ConcurrentHashMap<>();

    public ThirdPaymentService getPaymentService(String serviceName){

        ThirdPaymentService thirdPaymentService=paymentServiceMap.get(serviceName);

        if(thirdPaymentService==null){
            log.error("unknown service.{}",serviceName);
            throw new BusinessException(ErrorCodeEnum.SERVICE_NOT_FOUND);
        }

        return thirdPaymentService;
    }
}
