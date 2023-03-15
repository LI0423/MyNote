package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.*;
import com.video.manager.domain.entity.VideoCategoryDO;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.VideoService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: api
 * @description: material controller
 * @author: laojiang
 * @create: 2020-08-19 13:48
 **/
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    private VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    public ResponseResult updateCategory(@NotNull @RequestBody VideoCategoryDTO videoCategoryDTO,
                                         @PathVariable("videoId") Long videoId) {

        boolean result = videoService.updateCategory(videoCategoryDTO.getCategoryIds(), videoId);

        if (result) {
            return ResponseResult.success("修改分类成功");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @PostMapping("/refresh/{id}")
    public ResponseResult refresh(@PathVariable("id") Long videoId) {
        boolean result = videoService.refresh(videoId);

        if (result) {
            return ResponseResult.success("修改分类成功");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @PostMapping("/category/{videoId}")
    public ResponseResult addCategory(@NotNull @RequestBody VideoCategoryDTO videoCategoryDTO,
                                      @PathVariable("videoId") Long videoId) {

        videoService.addCategory(videoCategoryDTO.getCategoryId(), videoId);
        return ResponseResult.success("修改分类成功");
    }

    @DeleteMapping("/category/{videoId}")
    public ResponseResult deleteCategory(@PathVariable("videoId") Long videoId, @Param("categoryId") Integer categoryId) {

        videoService.deleteCategory(categoryId, videoId);
        return ResponseResult.success("修改分类成功");
    }

    @PutMapping("/contentLevel/{videoId}/{status}")
    public ResponseResult blockMinor(@PathVariable("videoId") Long videoId,
                                     @PathVariable("status") Integer status) {
        boolean result = videoService.updateContentLevel(videoId, status);

        if (result) {
            return ResponseResult.success("屏蔽青少年成功");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @GetMapping
    public ResponseResult search(@NotNull VideoQueryDTO query,
                                 @NotNull Integer pageNum,
                                 @NotNull Integer pageSize,
                                 @Param("orderBy") String orderBy,
                                 @Param("sequence") String sequence) {

        PageResult<List<VideoDTO>> result = getResult(query, pageNum, pageSize, orderBy, sequence);

        return ResponseResult.success(result);
    }

    private PageResult<List<VideoDTO>> getResult(VideoQueryDTO query,
                                                 Integer pageNum,
                                                 Integer pageSize,
                                                 String orderBy,
                                                 String sequence
    ) {
        PageQuery<VideoQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return videoService.query(pageQuery);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long videoId) {
        if (videoService.delete(videoId)) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.DELETE_FAILURE);
        }
    }

    @PutMapping("/syncHistory")
    public ResponseResult syncHistory() {
        videoService.sync();
        return ResponseResult.success("缓存已清除！");
    }

}
