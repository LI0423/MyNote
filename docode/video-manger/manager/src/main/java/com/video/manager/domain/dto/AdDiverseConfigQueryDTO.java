/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.domain.dto;

import lombok.Data;

/**
 * @author zhangchao 2022-01-17
 */
@Data
public class AdDiverseConfigQueryDTO {

    private String app;

    private String aid;

    private String source;

    private Long intervalTime;

    private String orderBy;

    private String sequence;

    private Integer pageNum = 1;

    private Integer pageSize = 20;
}
