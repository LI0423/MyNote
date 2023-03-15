package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.*;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.DataConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/system/dict")
public class DataConfigController extends BaseController {

    @Autowired
    DataConfigService dataConfigService;

    @RequestMapping("/add")
    public ResponseResult addConfig(@RequestBody DataConfigDTO configDTO) {

        ManagerDTO managerDTO = getManager();
        Boolean aBoolean = dataConfigService.addConfig(managerDTO, configDTO);
        if (aBoolean) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.ADD_ERROR);
        }

    }

    @GetMapping("/list")
    public ResponseResult getAll(@RequestParam(value = "item", required = false) String item,
                                 @RequestParam(value = "remark", required = false) String remark,
                                 @RequestParam("pageNum") Integer pageNum,
                                 @RequestParam("pageSize") Integer pageSize,
                                 @RequestParam(value = "orderBy") String orderBy,
                                 @RequestParam(value = "sequence") String sequence) {

        DataConfigQueryDTO dataConfigQueryDTO = new DataConfigQueryDTO();
        dataConfigQueryDTO.setItem(item);
        dataConfigQueryDTO.setRemark(remark);

        PageResult<DataConfigDTO> all = dataConfigService.getAll(getResult(dataConfigQueryDTO, pageNum, pageSize, orderBy, sequence));
        return ResponseResult.success(all);
    }

    private PageQuery<DataConfigQueryDTO> getResult(DataConfigQueryDTO query,
                                                    Integer pageNum,
                                                    Integer pageSize,
                                                    String orderBy,
                                                    String sequence
    ) {
        PageQuery<DataConfigQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return pageQuery;
    }

    @PutMapping("/update/{id}")
    public ResponseResult updateInfo(@PathVariable("id") Long id,
                                     @RequestBody DataConfigDTO dataConfigDTO) {
        ManagerDTO managerDTO = getManager();
        Boolean aBoolean = dataConfigService.updateConfig(id, dataConfigDTO, managerDTO);
        if (aBoolean) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        ManagerDTO managerDTO = getManager();
        Boolean aBoolean = dataConfigService.deleteConfig(id, managerDTO);
        if (aBoolean) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.DELETE_FAILURE);
        }
    }

}
