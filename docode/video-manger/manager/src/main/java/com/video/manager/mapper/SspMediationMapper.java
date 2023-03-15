/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.manager.domain.dto.MediationDTO;

/**
 * @author zhangchao 2022-01-26
 */
@DS("mysql_ssp")
@Mapper
public interface SspMediationMapper extends BaseMapper {

    List<MediationDTO> getAidList(String pkg);
}
