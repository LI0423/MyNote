package com.video.payment.service.third;

import com.video.payment.exception.BusinessException;
import com.video.payment.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: mango
 * @description: 第三方支付退款相关service工厂
 * @author: wangwei
 **/
@Service
@Slf4j
public class ThirdRefundFactoryService {

    @Autowired
    private Map<String, ThirdRefundService> refundServiceMap = new ConcurrentHashMap<>();

    public ThirdRefundService getRefundService(String serviceName){

        ThirdRefundService thirdRefundService = refundServiceMap.get(serviceName);

        if(thirdRefundService == null){
            log.error("unknown service.{}",serviceName);
            throw new BusinessException(ErrorCodeEnum.SERVICE_NOT_FOUND);
        }

        return thirdRefundService;
    }
}
