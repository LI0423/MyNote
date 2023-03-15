package com.video.manager.controller;

import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.VideoChannelDTO;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.ChannelCategoryService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/channelCategory")
public class ChannelCategoryContoller {
    @Autowired
    ChannelCategoryService channelCategoryService;

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id,
                                 @Param("categoryId") Integer categoryId) {

        channelCategoryService.delete(categoryId, id);
        return ResponseResult.success("删除标签成功");

    }

    @PostMapping("/{id}")
    public ResponseResult save(@PathVariable("id") Integer id,
                               @NotNull @RequestBody VideoChannelDTO videoChannelDTO) {
        channelCategoryService.add(videoChannelDTO.getCategoryId(), id);
        return ResponseResult.success("新增标签成功");
    }

}
