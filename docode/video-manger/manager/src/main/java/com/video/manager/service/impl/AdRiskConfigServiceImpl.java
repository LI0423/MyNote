/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.video.entity.UserAdLimitConfigDO;
import com.video.entity.UserAdRiskConfigDO;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.AdLimitConfigQueryDTO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.UserAdLimitConfigMapper;
import com.video.manager.service.AdRiskConfigService;
import com.video.manager.domain.dto.AdLimitConfigDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangchao 2022-01-14
 */
@Slf4j
@Service
public class AdRiskConfigServiceImpl implements AdRiskConfigService {

    @Autowired
    private UserAdLimitConfigMapper userAdLimitConfigMapper;

    @Override
    public PageResult<List<AdLimitConfigDTO>> list(AdLimitConfigQueryDTO adLimitConfigQuery) {
        PageResult<List<AdLimitConfigDTO>> result = new PageResult<>();
        QueryWrapper<UserAdLimitConfigDO> queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(adLimitConfigQuery.getApp())) {
            queryWrapper.eq("app", adLimitConfigQuery.getApp());
        }
        if (StringUtils.isNotBlank(adLimitConfigQuery.getSource())) {
            queryWrapper.eq("source", adLimitConfigQuery.getSource());
        }
        if (adLimitConfigQuery.getType() != null) {
            queryWrapper.eq("type", adLimitConfigQuery.getType());
        }
        if (adLimitConfigQuery.getConfigType() != null) {
            queryWrapper.eq("config_type", adLimitConfigQuery.getConfigType());
        }
        if (StringUtils.isNotBlank(adLimitConfigQuery.getModel())) {
            queryWrapper.like("model", adLimitConfigQuery.getModel());
        }
        if (StringUtils.isNotBlank(adLimitConfigQuery.getToken())) {
            queryWrapper.like("token", adLimitConfigQuery.getToken());
        }
        if (StringUtils.isNotBlank(adLimitConfigQuery.getAid())) {
            queryWrapper.like("aid", adLimitConfigQuery.getAid());
        }
        if (StringUtils.isNotBlank(adLimitConfigQuery.getOrderBy()) && StringUtils.isNotBlank(adLimitConfigQuery.getSequence())) {
            if (Constants.ASCENDING.equals(adLimitConfigQuery.getSequence())) {
                queryWrapper.orderByAsc(adLimitConfigQuery.getOrderBy());
            } else {
                queryWrapper.orderByDesc(adLimitConfigQuery.getOrderBy());
            }
        } else {
            queryWrapper.orderByDesc("create_time");
        }
        Page<UserAdLimitConfigDO> page = new Page(adLimitConfigQuery.getPageNum(), adLimitConfigQuery.getPageSize());
        IPage<UserAdLimitConfigDO> iPage = userAdLimitConfigMapper.selectPage(page, queryWrapper);
        List<AdLimitConfigDTO> list = iPage.getRecords().stream().map(u -> {
            AdLimitConfigDTO adRiskConfigDTO = new AdLimitConfigDTO();
            BeanUtils.copyProperties(u, adRiskConfigDTO);
            adRiskConfigDTO.setId(u.getId() + "");
            return adRiskConfigDTO;
        }).collect(Collectors.toList());
        result.setTotal(iPage.getTotal());
        result.setLists(list);
        return result;
    }

    @Override
    public void delete(Long id) {
        log.info("[AD RISK] delete {}", id);
        userAdLimitConfigMapper.deleteById(id);
    }

    @Override
    public void add(AdLimitConfigDTO adLimitConfigDTO) {
        log.info("[AD RISK] add {}", adLimitConfigDTO);
        if (adLimitConfigDTO.getTokens() == null || adLimitConfigDTO.getTokens().size() == 0) {
            UserAdLimitConfigDO adLimitConfigDO = new UserAdLimitConfigDO();
            BeanUtils.copyProperties(adLimitConfigDTO, adLimitConfigDO);
            if (checkDuplicate(adLimitConfigDO)) {
                log.info("[AD RISK] add, config duplicate, {}", adLimitConfigDTO);
                throw new BusinessException(ErrorCodeEnum.VIDEO_REPEAT);
            }
            userAdLimitConfigMapper.insert(adLimitConfigDO);
        } else {
            for (String token : adLimitConfigDTO.getTokens()) {
                UserAdLimitConfigDO adLimitConfigDO = new UserAdLimitConfigDO();
                BeanUtils.copyProperties(adLimitConfigDTO, adLimitConfigDO);
                adLimitConfigDO.setToken(token);
                if (checkDuplicate(adLimitConfigDO)) {
                    log.info("[AD RISK] add, config duplicate batch, {}", adLimitConfigDTO);
                    updateWithAdd(adLimitConfigDO);
                } else {
                    userAdLimitConfigMapper.insert(adLimitConfigDO);
                }
            }
        }
    }

    @Override
    public void update(AdLimitConfigDTO adLimitConfigDTO) {
        log.info("[AD RISK] update {}", adLimitConfigDTO);
        UserAdLimitConfigDO adLimitConfigDO = new UserAdLimitConfigDO();
        BeanUtils.copyProperties(adLimitConfigDTO, adLimitConfigDO);
        adLimitConfigDO.setId(Long.valueOf(adLimitConfigDTO.getId()));
        adLimitConfigDO.setToken(adLimitConfigDTO.getTokens().get(0));
        if (checkDuplicate(adLimitConfigDO)) {
            log.info("AD RISK] update, config duplicate, {}", adLimitConfigDTO);
            throw new BusinessException(ErrorCodeEnum.VIDEO_REPEAT);
        }
        userAdLimitConfigMapper.updateById(adLimitConfigDO);
    }

    private boolean checkDuplicate(UserAdLimitConfigDO adLimitConfigDO) {
        QueryWrapper<UserAdLimitConfigDO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(adLimitConfigDO.getApp())) {
            queryWrapper.eq("app", adLimitConfigDO.getApp());
        }
        if (adLimitConfigDO.getType() != null) {
            queryWrapper.eq("type", adLimitConfigDO.getType());
        }
        if (adLimitConfigDO.getConfigType() != null) {
            queryWrapper.eq("config_type", adLimitConfigDO.getConfigType());
        }
        if (StringUtils.isNotBlank(adLimitConfigDO.getSource())) {
            queryWrapper.eq("source", adLimitConfigDO.getSource());
        }
        if (StringUtils.isNotBlank(adLimitConfigDO.getModel())) {
            queryWrapper.eq("model", adLimitConfigDO.getModel());
        } else {
            queryWrapper.isNull("model");
        }
        if (StringUtils.isNotBlank(adLimitConfigDO.getToken())) {
            queryWrapper.eq("token", adLimitConfigDO.getToken());
        } else {
            queryWrapper.isNull("token");
        }
        if (StringUtils.isNotBlank(adLimitConfigDO.getAid())) {
            queryWrapper.eq("aid", adLimitConfigDO.getAid());
        } else {
            queryWrapper.isNull("aid");
        }
        UserAdLimitConfigDO datebaseUserAdLimitConfigDO = userAdLimitConfigMapper.selectOne(queryWrapper);
        if (datebaseUserAdLimitConfigDO != null) {
            if (!Objects.equals(datebaseUserAdLimitConfigDO.getId(), adLimitConfigDO.getId())) {
                return true;
            }
        }
        return false;
    }

    private void updateWithAdd(UserAdLimitConfigDO adLimitConfigDO) {
        QueryWrapper<UserAdLimitConfigDO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(adLimitConfigDO.getApp())) {
            queryWrapper.eq("app", adLimitConfigDO.getApp());
        }
        if (adLimitConfigDO.getType() != null) {
            queryWrapper.eq("type", adLimitConfigDO.getType());
        }
        if (adLimitConfigDO.getConfigType() != null) {
            queryWrapper.eq("config_type", adLimitConfigDO.getConfigType());
        }
        if (StringUtils.isNotBlank(adLimitConfigDO.getSource())) {
            queryWrapper.eq("source", adLimitConfigDO.getSource());
        }
        if (StringUtils.isNotBlank(adLimitConfigDO.getModel())) {
            queryWrapper.eq("model", adLimitConfigDO.getModel());
        } else {
            queryWrapper.isNull("model");
        }
        if (StringUtils.isNotBlank(adLimitConfigDO.getToken())) {
            queryWrapper.eq("token", adLimitConfigDO.getToken());
        } else {
            queryWrapper.isNull("token");
        }
        if (StringUtils.isNotBlank(adLimitConfigDO.getAid())) {
            queryWrapper.eq("aid", adLimitConfigDO.getAid());
        } else {
            queryWrapper.isNull("aid");
        }
        UserAdLimitConfigDO datebaseUserAdLimitConfigDO = userAdLimitConfigMapper.selectOne(queryWrapper);
        Long id = datebaseUserAdLimitConfigDO.getId();
        if (datebaseUserAdLimitConfigDO == null) {
            return;
        }
        BeanUtils.copyProperties(adLimitConfigDO, datebaseUserAdLimitConfigDO);
        datebaseUserAdLimitConfigDO.setId(id);
        userAdLimitConfigMapper.updateById(datebaseUserAdLimitConfigDO);
    }
}