package com.video.manager.service.impl;

import com.video.entity.RCWithdrawalConfigDO;
import com.video.manager.domain.dto.RCWithdrawalConfigAddRespDTO;
import com.video.manager.domain.dto.RCWithdrawalConfigQueryRespDTO;
import com.video.manager.domain.dto.RCWithdrawalConfigUpdateRespDTO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.RCWithdrawalConfigMapper;
import com.video.manager.service.RCWithdrawalConfigSingleCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RCWithdrawalConfigSingleCacheServiceImpl implements RCWithdrawalConfigSingleCacheService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RCWithdrawalConfigMapper rcWithdrawalConfigMapper;

    @Override
    @Transactional
    public void syncSingleCache(RCWithdrawalConfigDO rcWithdrawalConfigDO) {
        log.info("AsyncSingleCache: [{}]", rcWithdrawalConfigDO);
        Long dayCeiling = rcWithdrawalConfigDO.getDayCeiling();
        String pkg = rcWithdrawalConfigDO.getPkg();
        String token = rcWithdrawalConfigDO.getToken();
        String key = String.format("risk_control:withdrawal_config:%s:%s", pkg, token);
        rcWithdrawalConfigDO.setCacheStatus(RCWithdrawalConfigDO.CacheStatusEnum.ALREADY_SYNC);
        rcWithdrawalConfigMapper.updateById(rcWithdrawalConfigDO);
        stringRedisTemplate.opsForValue().set(key, String.valueOf(dayCeiling));
    }

}
