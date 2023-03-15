package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.video.entity.RCConfigRecordDO;
import com.video.entity.RCRewardConfigDO;
import com.video.entity.RCWithdrawalConfigDO;
import com.video.manager.domain.dto.*;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.RCConfigRecordMapper;
import com.video.manager.mapper.RCWithdrawalConfigMapper;
import com.video.manager.service.RCWithdrawalConfigService;
import com.video.manager.service.RCWithdrawalConfigSingleCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class RCWithdrawalConfigServiceImpl implements RCWithdrawalConfigService {

    @Autowired
    RCWithdrawalConfigMapper rcWithdrawalConfigMapper;
    @Autowired
    RCConfigRecordMapper rcConfigRecordMapper;
    @Autowired
    RCWithdrawalConfigSingleCacheService rcWithdrawalConfigSingleCacheService;

    @Override
    public RCWithdrawalConfigQueryRespDTO query(String pkg, List<String> tokenList) {
        RCWithdrawalConfigQueryRespDTO.RCWithdrawalConfigQueryRespDTOBuilder respDTOBuilder = RCWithdrawalConfigQueryRespDTO.builder();
        // 参数有效性校验
        boolean checkValidQueryParamParam = checkValidQueryParam(pkg, tokenList);
        if (!checkValidQueryParamParam) {
            respDTOBuilder.status(RCWithdrawalConfigQueryRespDTO.Status.FAIL);
            return respDTOBuilder.build();
        }
        // 查询
        List<RCWithdrawalConfigDO> queryRCWithdrawalConfigDOS = rcWithdrawalConfigMapper.queryList(pkg, tokenList);
        Map<String, RCWithdrawalConfigDO> queryConfigMap = new HashMap<>();
        for (RCWithdrawalConfigDO config : queryRCWithdrawalConfigDOS) {
            queryConfigMap.put(config.getToken(), config);
        }
        List<RCWithdrawalConfigQueryRespConfigDTO> rcWithdrawalConfigList = new ArrayList<>();
        for (String token : tokenList) {
            RCWithdrawalConfigDO rcWithdrawalConfigDO = queryConfigMap.get(token);
            Long queryCeiling = null;
            if (rcWithdrawalConfigDO != null) {
                queryCeiling = rcWithdrawalConfigDO.getDayCeiling();
            }
            RCWithdrawalConfigQueryRespConfigDTO.RCWithdrawalConfigQueryRespConfigDTOBuilder
                    rcWithdrawalConfigQueryRespConfigDTOBuilder = RCWithdrawalConfigQueryRespConfigDTO.builder();
            RCWithdrawalConfigQueryRespConfigDTO rcRewardConfigQueryRespConfigDTO = rcWithdrawalConfigQueryRespConfigDTOBuilder
                    .pkg(pkg)
                    .token(token)
                    .ceiling(queryCeiling)
                    .build();
            rcWithdrawalConfigList.add(rcRewardConfigQueryRespConfigDTO);
        }
        respDTOBuilder.configList(rcWithdrawalConfigList);
        respDTOBuilder.status(RCWithdrawalConfigQueryRespDTO.Status.SUCCESS);
        return respDTOBuilder.build();
    }


    private boolean checkValidQueryParam(String pkg, List<String> tokenList) {
        if (StringUtils.isBlank(pkg) || tokenList == null || tokenList.size() < 1) {
            log.warn("rc withdrawal config query error , request is null .pkg is [{}] , token is [{}]"
                    , pkg, tokenList);
            return false;
        }
        for (String token : tokenList) {
            if (StringUtils.isBlank(token)) {
                log.warn("rc withdrawal config query error , token is blank. token [{}]", tokenList);
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public RCWithdrawalConfigAddRespDTO add(String pkg, List<String> tokenList, Long ceiling) {
        RCWithdrawalConfigAddRespDTO.RCWithdrawalConfigAddRespDTOBuilder builder = RCWithdrawalConfigAddRespDTO.builder();
        // 参数有效性校验
        boolean checkValidAddParam = checkValidAddParam(pkg, tokenList, ceiling);
        if (!checkValidAddParam) {
            builder.status(RCWithdrawalConfigAddRespDTO.Status.FAIL);
            return builder.build();
        }
        // 查重
        List<String> repeatTokenList = rcWithdrawalConfigMapper.queryTokenList(pkg, tokenList);
        tokenList.removeAll(repeatTokenList);
        // 写数据库 数据库中不存在的token进行插入
        for (String token : tokenList) {
            RCWithdrawalConfigDO rcRewardConfigDO = RCWithdrawalConfigDO.builder()
                    .pkg(pkg)
                    .token(token)
                    .dayCeiling(ceiling)
                    .cacheStatus(RCWithdrawalConfigDO.CacheStatusEnum.WAIT_ADD)
                    .build();
            rcWithdrawalConfigMapper.insert(rcRewardConfigDO);

            RCConfigRecordDO build = RCConfigRecordDO.builder()
                    .token(token)
                    .pkg(pkg)
                    .target(String.valueOf(ceiling))
                    .type(RCConfigRecordDO.RCConfigTypeEnum.WITHDRAWAL_ADD)
                    .build();
            rcConfigRecordMapper.insert(build);
        }
        // 组装返回
        if (repeatTokenList.size() > 0) {
            builder.failToken(repeatTokenList);
            builder.status(RCWithdrawalConfigAddRespDTO.Status.PARTIAl_SUCCESS);
        } else {
            builder.status(RCWithdrawalConfigAddRespDTO.Status.SUCCESS);
        }
        return builder.build();
    }


    private boolean checkValidAddParam(String pkg, List<String> tokenList, Long ceiling) {
        if (StringUtils.isBlank(pkg) || tokenList == null || tokenList.size() < 1 || ceiling == null) {
            log.warn("rc withdrawal config add error , request is null . token is [{}]" +
                    " , ceiling is [{}]", tokenList, ceiling);
            return false;
        }
        for (String token : tokenList) {
            if (StringUtils.isBlank(token)) {
                log.warn("rc withdrawal config add error , token is blank. token [{}]", tokenList);
                return false;
            }
        }
        if (new HashSet<>(tokenList).size() != tokenList.size()) {
            log.warn("rc withdrawal config add error , token is repeat. token [{}]", tokenList);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public RCWithdrawalConfigUpdateRespDTO update(String pkg, List<String> tokenList, Long ceiling) {
        RCWithdrawalConfigUpdateRespDTO.RCWithdrawalConfigUpdateRespDTOBuilder builder = RCWithdrawalConfigUpdateRespDTO.builder();
        // 参数有效性校验
        boolean checkValidUpdateParam = checkValidUpdateParam(pkg, tokenList, ceiling);
        if (!checkValidUpdateParam) {
            builder.status(RCWithdrawalConfigUpdateRespDTO.Status.FAIL);
            return builder.build();
        }
        // 查重
        List<String> repeatTokenList = rcWithdrawalConfigMapper.queryTokenList(pkg, tokenList);
        tokenList.removeAll(repeatTokenList);
        // 写数据库 重复的token进行更新
        for (String token : repeatTokenList) {
            RCWithdrawalConfigDO rcWithdrawalConfigDO = RCWithdrawalConfigDO.builder()
                    .dayCeiling(ceiling)
                    .updateTime(LocalDateTime.now())
                    .cacheStatus(RCWithdrawalConfigDO.CacheStatusEnum.WAIT_UPDATE)
                    .build();

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("pkg", pkg);
            queryWrapper.eq("token", token);
            int updateNum = rcWithdrawalConfigMapper.update(rcWithdrawalConfigDO, queryWrapper);
            if (updateNum > 1) {
                log.error("RcWithdrawalConfig update fail , update num [{}], pkg [{}] , token [{}]",
                        updateNum, pkg, token);
                throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
            }
            RCConfigRecordDO build = RCConfigRecordDO.builder()
                    .pkg(pkg)
                    .token(token)
                    .target(String.valueOf(ceiling))
                    .type(RCConfigRecordDO.RCConfigTypeEnum.WITHDRAWAL_UPDATE)
                    .build();
            rcConfigRecordMapper.insert(build);
        }
        // 组装返回
        if (tokenList.size() > 0) {
            builder.failToken(tokenList);
            builder.status(RCWithdrawalConfigUpdateRespDTO.Status.PARTIAl_SUCCESS);
        } else {
            builder.status(RCWithdrawalConfigUpdateRespDTO.Status.SUCCESS);
        }
        return builder.build();
    }

    @Override
    public void syncCache() {
        QueryWrapper<RCWithdrawalConfigDO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("cache_status", RCWithdrawalConfigDO.CacheStatusEnum.WAIT_ADD)
                .or().eq("cache_status", RCWithdrawalConfigDO.CacheStatusEnum.WAIT_UPDATE);
        List<RCWithdrawalConfigDO> rcRewardConfigDOS = rcWithdrawalConfigMapper.selectList(queryWrapper);
        // 每个缓存更新，都单独放在一个事务中， 防止批量操作失败，导致数据不一致。
        for (RCWithdrawalConfigDO rcWithdrawalConfigDO : rcRewardConfigDOS) {
            rcWithdrawalConfigSingleCacheService.syncSingleCache(rcWithdrawalConfigDO);
        }
    }


    private boolean checkValidUpdateParam(String pkg, List<String> tokenList, Long ceiling) {
        if (StringUtils.isBlank(pkg) || tokenList == null || tokenList.size() < 1 || ceiling == null) {
            log.warn("rc withdrawal config update error, request is null. token is [{}]" +
                    " , ceiling is [{}]", tokenList, ceiling);
            return false;
        }
        for (String token : tokenList) {
            if (StringUtils.isBlank(token)) {
                log.warn("rc withdrawal config update error , token is blank. token [{}]", tokenList);
                return false;
            }
        }
        if (new HashSet<>(tokenList).size() != tokenList.size()) {
            log.warn("rc withdrawal config update error , token is repeat. token [{}]", tokenList);
            return false;
        }
        return true;
    }

}
