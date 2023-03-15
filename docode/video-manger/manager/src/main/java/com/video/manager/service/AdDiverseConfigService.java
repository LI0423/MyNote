/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.service;

import java.util.List;

import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.AdDiverseConfigDTO;
import com.video.manager.domain.dto.AdDiverseConfigQueryDTO;

/**
 * @author zhangchao 2022-01-17
 */
public interface AdDiverseConfigService {

    PageResult<List<AdDiverseConfigDTO>> list(AdDiverseConfigQueryDTO adDiverseConfigQueryDTO);

    void delete(Long id);

    void add(AdDiverseConfigDTO adDisverseConfigDTO);

    void update(AdDiverseConfigDTO adDisverseConfigDTO);
}
