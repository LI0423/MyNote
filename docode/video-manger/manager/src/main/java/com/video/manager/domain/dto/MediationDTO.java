/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangchao 2022-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediationDTO {

    private String id;

    private String name;

    private Double price;
}
