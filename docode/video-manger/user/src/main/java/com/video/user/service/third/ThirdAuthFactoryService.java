package com.video.user.service.third;

import com.video.user.exception.BusinessException;
import com.video.user.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: mango
 * @description: 第三方认证service工厂
 * @author: laojiang
 * @create: 2020-09-11 13:56
 **/
@Service
@Slf4j
public class ThirdAuthFactoryService {

    @Autowired
    private Map<String,ThirdAuthService> authServiceMap=new ConcurrentHashMap<>();

    public ThirdAuthService getAuthService(String serviceName){

        ThirdAuthService thirdAuthService=authServiceMap.get(serviceName);

        if(thirdAuthService==null){
            log.error("unknown service.{}",serviceName);
            throw new BusinessException(ErrorCodeEnum.SERVICE_NOT_FOUND);
        }

        return thirdAuthService;
    }
}
