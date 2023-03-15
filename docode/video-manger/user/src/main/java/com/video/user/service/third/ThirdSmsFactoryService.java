package com.video.user.service.third;

import com.video.user.exception.BusinessException;
import com.video.user.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: mango
 * @description: 第三方短信服务service工厂
 * @author: laojiang
 * @create: 2021-08-02 16:27
 **/
@Service
@Slf4j
public class ThirdSmsFactoryService {

    @Autowired
    private Map<String, ThirdSmsService> smsServiceMap;

    public ThirdSmsService getSmsService(String serviceName) {

        ThirdSmsService thirdSmsService = smsServiceMap.get(serviceName);

        if (thirdSmsService == null) {
            log.error("unknown service.{}", serviceName);
            throw new BusinessException(ErrorCodeEnum.SERVICE_NOT_FOUND);
        }

        return thirdSmsService;
    }

}