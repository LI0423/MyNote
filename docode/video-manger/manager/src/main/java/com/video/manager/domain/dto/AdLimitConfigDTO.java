/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.domain.dto;

import java.util.List;

import com.video.entity.UserAdLimitConfigTypeEnum;
import com.video.entity.UserAdRiskTypeEnum;

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
public class AdLimitConfigDTO {

    private String id;

    private String app;

    private UserAdRiskTypeEnum type;

    private UserAdLimitConfigTypeEnum configType;

    /**
     * 型号
     */
    private String model;

    private String token;

    private List<String> tokens;

    private String source;

    private String aid;

    /**
     * h5的展示次数最小值
     */
    private Integer minValue;
    /**
     * h5展示次数最大值
     */
    private Integer maxValue;
    /**
     * h5在总广告展示中的占比
     */
    private Double ratio;

    /**
     * 最小点击次数
     */
    private Integer minClick;

    /**
     * 警告ctr
     */
    private Double warnCtr;
    /**
     * 最大ctr
     */
    private Double maxCtr;

    /**
     * 填充次数
     */
    private Integer fillValue;
}
