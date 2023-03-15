package com.video.manager.controller;

import com.video.entity.AppAdConfigStatusEnum;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.AppAdConfigQueryDTO;
import com.video.manager.domain.dto.CreateAppAdConfigDTO;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.AppAdConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @program: mango
 * @description:
 * @author: laojiang
 * @create: 2020-10-22 16:03
 **/
@RestController
@Slf4j
@RequestMapping("/api/v1/appAdConfig")
public class AppAdConfigController {

    @Autowired
    private AppAdConfigService appAdConfigService;

    @Autowired
    public AppAdConfigController(AppAdConfigService appAdConfigService) {
        this.appAdConfigService = appAdConfigService;
    }

    @PostMapping
    public ResponseResult create(@RequestBody CreateAppAdConfigDTO createAppAdConfigDTO){

        log.debug("createAppAdConfigDTO={}",createAppAdConfigDTO);

        Boolean result=appAdConfigService.create(createAppAdConfigDTO);

        if(result){
            return ResponseResult.success("添加成功");
        }else{
            return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
        }
    }

    @PutMapping("/{id}")
    public ResponseResult modify(@RequestBody CreateAppAdConfigDTO createAppAdConfigDTO,
                                 @PathVariable String id){

        Boolean result=appAdConfigService.modify(createAppAdConfigDTO,Long.valueOf(id));

        if(result){
            return ResponseResult.success("修改成功");
        }else{
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @PutMapping("/{id}/{status}")
    public ResponseResult updateStatus(@PathVariable String id,@PathVariable Integer status){

        Boolean result=appAdConfigService.updateStatus(Long.valueOf(id), AppAdConfigStatusEnum.values()[status]);

        if(result){
            return ResponseResult.success("修改成功");
        }else{
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable String id){

        return ResponseResult.success(appAdConfigService.findOne(Long.valueOf(id)));
    }


    @GetMapping
    public ResponseResult find(@NotNull AppAdConfigQueryDTO query,
                               @NotNull Integer pageNum,
                               @NotNull Integer pageSize,
                               @Param("orderBy") String orderBy,
                               @Param("sequence") String sequence) {

        PageQuery<AppAdConfigQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return ResponseResult.success(appAdConfigService.findPage(pageQuery));
    }


}
