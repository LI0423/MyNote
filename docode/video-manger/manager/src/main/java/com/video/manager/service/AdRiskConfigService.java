/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.service;

import java.util.List;

import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.AdLimitConfigDTO;
import com.video.manager.domain.dto.AdLimitConfigQueryDTO;

/**
 * @author zhangchao 2022-01-14
 */
public interface AdRiskConfigService {

    PageResult<List<AdLimitConfigDTO>> list(AdLimitConfigQueryDTO adRiskConfigQueryDTO);

    void delete(Long id);

    void add(AdLimitConfigDTO adRiskConfigDTO);

    void update(AdLimitConfigDTO adRiskConfigDTO);
}
