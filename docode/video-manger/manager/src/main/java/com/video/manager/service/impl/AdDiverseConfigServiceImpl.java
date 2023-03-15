/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.video.entity.UserAdDiverseConfigDO;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.AdDiverseConfigDTO;
import com.video.manager.domain.dto.AdDiverseConfigQueryDTO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.UserAdDiverseConfigMapper;
import com.video.manager.service.AdDiverseConfigService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangchao 2022-01-14
 */
@Slf4j
@Service
public class AdDiverseConfigServiceImpl implements AdDiverseConfigService {

    @Autowired
    private UserAdDiverseConfigMapper userAdDiverseConfigMapper;

    @Override
    public PageResult<List<AdDiverseConfigDTO>> list(AdDiverseConfigQueryDTO adDiverseConfigQueryDTO) {
        PageResult<List<AdDiverseConfigDTO>> result = new PageResult<>();
        QueryWrapper<UserAdDiverseConfigDO> queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(adDiverseConfigQueryDTO.getApp())) {
            queryWrapper.eq("app", adDiverseConfigQueryDTO.getApp());
        }
        if (StringUtils.isNotBlank(adDiverseConfigQueryDTO.getAid())) {
            queryWrapper.eq("aid", adDiverseConfigQueryDTO.getAid());
        }
        if (StringUtils.isNotBlank(adDiverseConfigQueryDTO.getSource())) {
            queryWrapper.eq("source", adDiverseConfigQueryDTO.getSource());
        }
        queryWrapper.orderByDesc("create_time");
        Page<UserAdDiverseConfigDO> page = new Page(adDiverseConfigQueryDTO.getPageNum(), adDiverseConfigQueryDTO.getPageSize());
        IPage<UserAdDiverseConfigDO> iPage = userAdDiverseConfigMapper.selectPage(page, queryWrapper);
        List<AdDiverseConfigDTO> list = iPage.getRecords().stream().map(u -> {
            AdDiverseConfigDTO adDisverseConfigDTO = new AdDiverseConfigDTO();
            BeanUtils.copyProperties(u, adDisverseConfigDTO);
            adDisverseConfigDTO.setId(u.getId() + "");
            return adDisverseConfigDTO;
        }).collect(Collectors.toList());
        result.setTotal(iPage.getTotal());
        result.setLists(list);
        return result;
    }

    @Override
    public void delete(Long id) {
        log.info("[AD DIVERSE] delete {}", id);
        userAdDiverseConfigMapper.deleteById(id);
    }

    @Override
    public void add(AdDiverseConfigDTO adDiverseConfigDTO) {
        log.info("[AD DIVERSE] add {}", adDiverseConfigDTO);
        try {
            UserAdDiverseConfigDO adDiverseConfigDO = new UserAdDiverseConfigDO();
            BeanUtils.copyProperties(adDiverseConfigDTO, adDiverseConfigDO);
            userAdDiverseConfigMapper.insert(adDiverseConfigDO);
        } catch (DuplicateKeyException e) {
            log.info("[AD DIVERSE] add repeat {}", adDiverseConfigDTO);
            throw new BusinessException(ErrorCodeEnum.VIDEO_REPEAT);
        }
    }

    @Override
    public void update(AdDiverseConfigDTO adDiverseConfigDTO) {
        log.info("[AD DIVERSE] update {}", adDiverseConfigDTO);
        try {
            UserAdDiverseConfigDO adDiverseConfigDO = new UserAdDiverseConfigDO();
            BeanUtils.copyProperties(adDiverseConfigDTO, adDiverseConfigDO);
            adDiverseConfigDO.setId(Long.valueOf(adDiverseConfigDTO.getId()));
            userAdDiverseConfigMapper.updateById(adDiverseConfigDO);
        } catch (DuplicateKeyException e) {
            log.info("[AD DIVERSE] update repeat {}", adDiverseConfigDTO);
            throw new BusinessException(ErrorCodeEnum.VIDEO_REPEAT);
        }
    }
}
