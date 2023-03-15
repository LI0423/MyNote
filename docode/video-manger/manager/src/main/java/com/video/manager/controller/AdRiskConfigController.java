/*
 * Copyright (C) 2022 Baidu, Inc. All Rights Reserved.
 */
package com.video.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.AdLimitConfigDTO;
import com.video.manager.domain.dto.AdLimitConfigQueryDTO;
import com.video.manager.service.AdRiskConfigService;

/**
 * @author zhangchao 2022-01-14
 */
@RestController
@RequestMapping("/api/v1/risk/control")
public class AdRiskConfigController {

    @Autowired
    private AdRiskConfigService adRiskConfigService;

    @GetMapping("/list")
    public ResponseResult list(AdLimitConfigQueryDTO adLimitConfigQueryDTO) {
        return ResponseResult.success(adRiskConfigService.list(adLimitConfigQueryDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        adRiskConfigService.delete(id);
        return ResponseResult.success("删除成功");
    }

    @PostMapping("/add")
    public ResponseResult save(@RequestBody AdLimitConfigDTO adLimitConfigDTO) {
        adRiskConfigService.add(adLimitConfigDTO);
        return ResponseResult.success("保存成功");
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody AdLimitConfigDTO adLimitConfigDTO) {
            adRiskConfigService.update(adLimitConfigDTO);
        return ResponseResult.success("修改成功");
    }
}
