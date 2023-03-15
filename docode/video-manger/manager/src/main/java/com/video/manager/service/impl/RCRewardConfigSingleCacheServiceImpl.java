package com.video.manager.service.impl;

import com.video.entity.RCRewardConfigDO;
import com.video.manager.domain.dto.RCRewardConfigAddRespDTO;
import com.video.manager.domain.dto.RCRewardConfigQueryRespDTO;
import com.video.manager.domain.dto.RCRewardConfigUpdateRespDTO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.RCRewardConfigMapper;
import com.video.manager.service.RCRewardConfigSingleCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RCRewardConfigSingleCacheServiceImpl implements RCRewardConfigSingleCacheService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RCRewardConfigMapper rcRewardConfigMapper;

    @Override
    @Transactional
    public void syncSingleCache(RCRewardConfigDO rcRewardConfigDO) {
        log.info("AsyncSingleCache: [{}]", rcRewardConfigDO);
        Float coefficient = rcRewardConfigDO.getCoefficient();
        String pkg = rcRewardConfigDO.getPkg();
        String token = rcRewardConfigDO.getToken();
        String key = String.format("risk_control:reward_config:%s:%s", pkg, token);
        rcRewardConfigDO.setCacheStatus(RCRewardConfigDO.CacheStatusEnum.ALREADY_SYNC);
        rcRewardConfigMapper.updateById(rcRewardConfigDO);
        stringRedisTemplate.opsForValue().set(key, String.valueOf(coefficient));
    }
}
