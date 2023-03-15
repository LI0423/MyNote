package com.video.manager.controller;

import com.video.manager.domain.common.CrabVideoSandboxStatusEnum;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.*;
import com.video.manager.domain.entity.MaterialNewDO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.CrabVideoSandboxService;
import com.video.manager.service.VideoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yifan
 */
@RestController
@RequestMapping("/api/v1/videoSandbox")
public class VideoSandboxController {
    @Autowired
    CrabVideoSandboxService crabVideoSandboxService;
    public static final String LANDSCAPE = "10";

    @PostMapping("/category/{videoId}")
    public ResponseResult updateCategory(@NotNull @RequestBody VideoCategoryDTO videoCategoryDTO,
                                      @PathVariable("videoId") Long videoId){

        boolean result=crabVideoSandboxService.addCategory(videoCategoryDTO.getCategoryId(),videoId);

        if(result){
            return ResponseResult.success("修改分类成功");
        }else{
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }
    @DeleteMapping("/category/{videoId}")
    public ResponseResult deleteCategory(@PathVariable("videoId") Long videoId,@Param("categoryId") Integer categoryId) {

        boolean result=crabVideoSandboxService.deleteCategory(categoryId, videoId);
        if(result){
            return ResponseResult.success("修改分类成功");
        }else{
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @PutMapping("/contentLevel/{videoId}/{status}")
    public ResponseResult blockMinor(@PathVariable("videoId") Long videoId,
                                     @PathVariable("status") Integer status){
        boolean result=crabVideoSandboxService.updateContentLevel(videoId,status);

        if(result){
            return ResponseResult.success("屏蔽青少年成功");
        }else{
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @GetMapping
    public ResponseResult find(@NotNull MaterialNewDTO query,
                                 @NotNull Integer pageNum,
                                 @NotNull Integer pageSize,
                                 @Param("orderBy") String orderBy,
                                 @Param("sequence") String sequence){

        if(query!=null&&LANDSCAPE.equals(query.getTypes())){
            query.setTypes("10");
        }else{
            query.setTypes("20");
        }

        PageResult<List<MaterialNewDTO>> result = getResult(query, pageNum, pageSize,orderBy,sequence);

        return ResponseResult.success(result);
    }

    private PageResult<List<MaterialNewDTO>> getResult(MaterialNewDTO query,
                                                            Integer pageNum,
                                                            Integer pageSize,
                                                            String orderBy,
                                                            String sequence
    ) {
        PageQuery<MaterialNewDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return crabVideoSandboxService.find(pageQuery);
    }

    @PostMapping("/publish/{id}")
    public ResponseResult publish(@PathVariable("id") Long id) {
        /// TODO: 2020/9/8
        MaterialNewDO materialNewDO = crabVideoSandboxService.findMaterialNewById(id);
        //todo:try
        try {
            crabVideoSandboxService.publish(materialNewDO);
            crabVideoSandboxService.updateMaterialNewById(id, CrabVideoSandboxStatusEnum.PUBLISH_COMPLETE);
        } catch (Exception e) {
            crabVideoSandboxService.updateMaterialNewById(id, CrabVideoSandboxStatusEnum.PUBLISH_FAIL);
            if (e.getClass().equals(BusinessException.class)) {
                return ResponseResult.failure(((BusinessException) e).getCode(), e.getMessage());
            }
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
        return ResponseResult.success("发布成功!");
    }

}
