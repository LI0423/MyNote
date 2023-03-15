package com.video.manager.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.video.entity.RCConfigRecordDO;
import com.video.entity.RCRewardConfigDO;
import com.video.manager.domain.dto.RCRewardConfigAddRespDTO;
import com.video.manager.domain.dto.RCRewardConfigQueryRespConfigDTO;
import com.video.manager.domain.dto.RCRewardConfigQueryRespDTO;
import com.video.manager.domain.dto.RCRewardConfigUpdateRespDTO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.RCConfigRecordMapper;
import com.video.manager.mapper.RCRewardConfigMapper;
import com.video.manager.service.RCRewardConfigService;
import com.video.manager.service.RCRewardConfigSingleCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class RCRewardConfigServiceImpl implements RCRewardConfigService {

    @Autowired
    RCRewardConfigMapper rcRewardConfigMapper;
    @Autowired
    RCConfigRecordMapper rcConfigRecordMapper;
    @Autowired
    RCRewardConfigSingleCacheService rcRewardConfigSingleCacheService;

    @Override
    public RCRewardConfigQueryRespDTO query(String pkg, List<String> tokenList) {
        RCRewardConfigQueryRespDTO.RCRewardConfigQueryRespDTOBuilder builder = RCRewardConfigQueryRespDTO.builder();
        // 参数有效性校验
        boolean checkValidQueryParamParam = checkValidQueryParam(pkg, tokenList);
        if (!checkValidQueryParamParam) {
            builder.status(RCRewardConfigQueryRespDTO.Status.FAIL);
            return builder.build();
        }
        // 查询
        List<RCRewardConfigDO> rcRewardConfigDOS = rcRewardConfigMapper.queryList(pkg, tokenList);
        Map<String, RCRewardConfigDO> queryConfigMap = new HashMap<>();
        for (RCRewardConfigDO config : rcRewardConfigDOS) {
            queryConfigMap.put(config.getToken(), config);
        }
        List<RCRewardConfigQueryRespConfigDTO> rcRewardConfigList = new ArrayList<>();
        for (String token : tokenList) {
            RCRewardConfigDO rcRewardConfigDO = queryConfigMap.get(token);
            Float queryCoefficient = null;
            if (rcRewardConfigDO != null) {
                queryCoefficient = rcRewardConfigDO.getCoefficient();
            }
            RCRewardConfigQueryRespConfigDTO rcRewardConfigQueryRespConfigDTO = RCRewardConfigQueryRespConfigDTO.builder()
                    .pkg(pkg)
                    .token(token)
                    .coefficient(queryCoefficient)
                    .build();
            rcRewardConfigList.add(rcRewardConfigQueryRespConfigDTO);
        }
        builder.configList(rcRewardConfigList);
        builder.status(RCRewardConfigQueryRespDTO.Status.SUCCESS);
        return builder.build();
    }

    private boolean checkValidQueryParam(String pkg, List<String> tokenList) {
        if (StringUtils.isBlank(pkg) || tokenList == null || tokenList.size() < 1) {
            log.warn("rc reward config qurey error , request is null .pkg is [{}] , token is [{}]"
                    , pkg, tokenList);
            return false;
        }
        for (String token : tokenList) {
            if (StringUtils.isBlank(token)) {
                log.warn("rc reward config query error , token is blank. token [{}]", tokenList);
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public RCRewardConfigAddRespDTO add(String pkg, List<String> tokenList, Float coefficient) {
        RCRewardConfigAddRespDTO.RCRewardConfigAddRespDTOBuilder builder = RCRewardConfigAddRespDTO.builder();
        // 参数有效性校验
        boolean checkValidAddParam = checkValidAddParam(pkg, tokenList, coefficient);
        if (!checkValidAddParam) {
            builder.status(RCRewardConfigAddRespDTO.Status.FAIL);
            return builder.build();
        }
        // 查重
        List<String> repeatTokenList = rcRewardConfigMapper.queryTokenList(pkg, tokenList);
        tokenList.removeAll(repeatTokenList);
        // 写数据库 数据库中不存在的token进行插入
        for (String token : tokenList) {
            RCRewardConfigDO rcRewardConfigDO = RCRewardConfigDO.builder()
                    .coefficient(coefficient)
                    .pkg(pkg)
                    .token(token)
                    .cacheStatus(RCRewardConfigDO.CacheStatusEnum.WAIT_ADD)
                    .build();
            rcRewardConfigMapper.insert(rcRewardConfigDO);
            RCConfigRecordDO build = RCConfigRecordDO.builder()
                    .token(token)
                    .pkg(pkg)
                    .target(String.valueOf(coefficient))
                    .type(RCConfigRecordDO.RCConfigTypeEnum.REWARD_ADD)
                    .build();
            rcConfigRecordMapper.insert(build);
        }
        // 组装返回
        if (repeatTokenList.size() > 0) {
            builder.failToken(repeatTokenList);
            builder.status(RCRewardConfigAddRespDTO.Status.PARTIAl_SUCCESS);
        } else {
            builder.status(RCRewardConfigAddRespDTO.Status.SUCCESS);
        }
        return builder.build();
    }


    private boolean checkValidAddParam(String pkg, List<String> tokenList, Float coefficient) {
        if (StringUtils.isBlank(pkg) || tokenList == null || tokenList.size() < 1 || coefficient == null) {
            log.warn("rc reward config add error , request is null . token is [{}]" +
                    " , coefficient is [{}]", tokenList, coefficient);
            return false;
        }
        for (String token : tokenList) {
            if (StringUtils.isBlank(token)) {
                log.warn("rc reward config add error , token is blank. token [{}]", tokenList);
                return false;
            }
        }
        if (new HashSet<>(tokenList).size() != tokenList.size()) {
            log.warn("rc reward config add error , token is repat. token [{}]", tokenList);
            return false;
        }
        return true;
    }


    @Override
    @Transactional
    public RCRewardConfigUpdateRespDTO update(String pkg, List<String> tokenList, Float coefficient) {
        RCRewardConfigUpdateRespDTO.RCRewardConfigUpdateRespDTOBuilder builder = RCRewardConfigUpdateRespDTO.builder();
        // 参数有效性校验
        boolean checkValidUpdateParam = checkValidUpdateParam(pkg, tokenList, coefficient);
        if (!checkValidUpdateParam) {
            builder.status(RCRewardConfigUpdateRespDTO.Status.FAIL);
            return builder.build();
        }
        // 查重
        List<String> repeatTokenList = rcRewardConfigMapper.queryTokenList(pkg, tokenList);
        tokenList.removeAll(repeatTokenList);
        // 写数据库 重复的token进行更新
        for (String token : repeatTokenList) {
            RCRewardConfigDO rcRewardConfigDO = RCRewardConfigDO.builder()
                    .coefficient(coefficient)
                    .update_time(LocalDateTime.now())
                    .cacheStatus(RCRewardConfigDO.CacheStatusEnum.WAIT_UPDATE)
                    .build();
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("pkg", pkg);
            queryWrapper.eq("token", token);
            int updateNum = rcRewardConfigMapper.update(rcRewardConfigDO, queryWrapper);
            if (updateNum > 1) {
                log.error("RcRewardConfig update fail , update num [{}], pkg [{}] , token [{}]",
                        updateNum, pkg, token);
                throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
            }
            RCConfigRecordDO build = RCConfigRecordDO.builder()
                    .pkg(pkg)
                    .token(token)
                    .target(String.valueOf(coefficient))
                    .type(RCConfigRecordDO.RCConfigTypeEnum.REWARD_UPDATE)
                    .build();
            rcConfigRecordMapper.insert(build);
        }
        // 组装返回
        if (tokenList.size() > 0) {
            builder.failToken(tokenList);
            builder.status(RCRewardConfigUpdateRespDTO.Status.PARTIAl_SUCCESS);
        } else {
            builder.status(RCRewardConfigUpdateRespDTO.Status.SUCCESS);
        }
        return builder.build();
    }

    @Override
    public void syncCache() {
        QueryWrapper<RCRewardConfigDO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("cache_status", RCRewardConfigDO.CacheStatusEnum.WAIT_ADD)
                .or().eq("cache_status", RCRewardConfigDO.CacheStatusEnum.WAIT_UPDATE);
        List<RCRewardConfigDO> rcRewardConfigDOS = rcRewardConfigMapper.selectList(queryWrapper);
        // 每个缓存更新，都单独放在一个事务中， 防止批量操作失败，导致数据不一致。
        for (RCRewardConfigDO rcRewardConfigDO : rcRewardConfigDOS) {
            rcRewardConfigSingleCacheService.syncSingleCache(rcRewardConfigDO);
        }
    }


    private boolean checkValidUpdateParam(String pkg, List<String> tokenList, Float coefficient) {
        if (StringUtils.isBlank(pkg) || tokenList == null || tokenList.size() < 1 || coefficient == null) {
            log.warn("rc reward config update error, request is null. token is [{}]" +
                    " , coefficient is [{}]", tokenList, coefficient);
            return false;
        }
        for (String token : tokenList) {
            if (StringUtils.isBlank(token)) {
                log.warn("rc reward config update error , token is blank. token [{}]", tokenList);
                return false;
            }
        }
        if (new HashSet<>(tokenList).size() != tokenList.size()) {
            log.warn("rc reward config update error , token is repat. token [{}]", tokenList);
            return false;
        }
        return true;
    }

}
