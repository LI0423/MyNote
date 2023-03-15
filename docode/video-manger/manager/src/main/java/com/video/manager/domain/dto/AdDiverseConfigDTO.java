/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangchao 2022-01-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdDiverseConfigDTO {

    private String id;

    private String app;

    private String aid;

    private String source;

    private Long intervalTime;
}
