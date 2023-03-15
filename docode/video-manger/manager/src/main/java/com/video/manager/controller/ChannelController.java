package com.video.manager.controller;

import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.ChannelDTO;
import com.video.manager.domain.dto.VideoCategoryDTO;
import com.video.manager.domain.dto.VideoChannelDTO;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.ChannelService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController {
    @Autowired
    ChannelService channelService;

    @PostMapping
    public ResponseResult save(@NotNull @RequestBody ChannelDTO channelDTO) {
        channelService.add(channelDTO);
        return ResponseResult.success("新增频道成功");
    }

    @PutMapping("/{id}")
    public ResponseResult update(@NotNull @RequestBody ChannelDTO channelDTO) {
        channelService.update(channelDTO);
        return ResponseResult.success("修改频道成功");
    }

    @GetMapping
    public ResponseResult find(Long appId) {
        return ResponseResult.success(channelService.find(appId));
    }

}
