/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.domain.dto;

import com.video.entity.UserAdLimitConfigTypeEnum;
import com.video.entity.UserAdRiskTypeEnum;

import lombok.Data;

/**
 * @author zhangchao 2022-01-14
 */
@Data
public class AdLimitConfigQueryDTO {

    private String app;

    private String source;

    private UserAdRiskTypeEnum type;

    private UserAdLimitConfigTypeEnum configType;

    private String model;

    private String token;

    private String aid;

    private String orderBy;

    private String sequence;

    private Integer pageNum = 1;

    private Integer pageSize = 20;
}
