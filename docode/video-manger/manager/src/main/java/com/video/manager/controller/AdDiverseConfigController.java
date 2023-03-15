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
import com.video.manager.domain.dto.AdDiverseConfigDTO;
import com.video.manager.domain.dto.AdDiverseConfigQueryDTO;
import com.video.manager.service.AdDiverseConfigService;

/**
 * @author zhangchao 2022-01-14
 */
@RestController
@RequestMapping("/api/v1/diverse/control")
public class AdDiverseConfigController {

    @Autowired
    private AdDiverseConfigService adDiverseConfigService;

    @GetMapping("/list")
    public ResponseResult list(AdDiverseConfigQueryDTO adDiverseConfigQueryDTO) {
        return ResponseResult.success(adDiverseConfigService.list(adDiverseConfigQueryDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        adDiverseConfigService.delete(id);
        return ResponseResult.success("删除成功");
    }

    @PostMapping("/add")
    public ResponseResult save(@RequestBody AdDiverseConfigDTO adDisverseConfigDTO) {
        adDiverseConfigService.add(adDisverseConfigDTO);
        return ResponseResult.success("保存成功");
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody AdDiverseConfigDTO adDiverseConfigDTO) {
        adDiverseConfigService.update(adDiverseConfigDTO);
        return ResponseResult.success("修改成功");
    }
}
