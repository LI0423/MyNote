package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.*;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.AppService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: video-manger
 * @description: app
 * @author: laojiang
 * @create: 2020-09-05 15:04
 **/
@RestController
@RequestMapping("/api/v1/app")
public class AppController {
    @Autowired
    AppService appService;

    @GetMapping("/name")
    public ResponseResult appName() {
        return ResponseResult.success(appService.findIdAndName());
    }

    @GetMapping
    public ResponseResult find(@NotNull AppQueryDTO query,
                               @NotNull Integer pageNum,
                               @NotNull Integer pageSize,
                               @Param("orderBy") String orderBy,
                               @Param("sequence") String sequence) {

        return ResponseResult.success(getResult(query, pageNum, pageSize, orderBy, sequence));
    }

    private PageResult<List<AppDTO>> getResult(AppQueryDTO query,
                                               Integer pageNum,
                                               Integer pageSize,
                                               String orderBy,
                                               String sequence
    ) {
        PageQuery<AppQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return appService.find(pageQuery);
    }

    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile fileByte) {
        return ResponseResult.success(appService.upload(fileByte));
    }

    @PostMapping
    public ResponseResult insert(@RequestBody AppDTO appDTO) {
        if (appService.insert(appDTO)) {
            return ResponseResult.success("新增成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
    }

    @PutMapping("/{id}")
    public ResponseResult update(@RequestBody AppDTO appDTO, @PathVariable Long id) {
        if (appService.update(appDTO, id)) {
            return ResponseResult.success("修改成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        if (appService.delete(id)) {
            return ResponseResult.success("删除成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
    }

    @GetMapping("/{id}")
    public ResponseResult getOne(@PathVariable("id") Long id) {

        return ResponseResult.success(appService.findOne(id));
    }

    @PostMapping("/addVersion")
    public ResponseResult insertAppConfig(@RequestBody AppVersionConfigDTO appVersionConfigDTO) {
        if (appService.addAppVersionConfig(appVersionConfigDTO)) {
            return ResponseResult.success("新增成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
    }

    @PostMapping("/updateVersion")
    public ResponseResult updateVersion(@RequestBody AppVersionConfigDTO appVersionConfigDTO) {
        if (appService.updateAppVersionConfig(appVersionConfigDTO)) {
            return ResponseResult.success("修改成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
    }

    @DeleteMapping("/deleteVersion")
    public ResponseResult deleteVersion(@RequestParam("id") Long id) {
        appService.deleteAppVersionConfig(id);
        return ResponseResult.success("success");
    }

    @GetMapping("/versionConfigList")
    public ResponseResult versionConfigList(@RequestParam("appId") Long appId) {
        return ResponseResult.success(appService.findAppVersionConfigList(appId));
    }

    @GetMapping("/getAidList")
    public ResponseResult getAidList(@RequestParam("pkg") String pkg) {
        return ResponseResult.success(appService.getAidList(pkg));
    }

}
