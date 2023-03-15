package com.video.manager.controller;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.*;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.CrabAddressService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yifan
 */
@RestController
@RequestMapping("/api/v1/CrabAddress")
public class CrabAddressController {
    @Autowired
    CrabAddressService crabAddressService;

    @GetMapping
    public ResponseResult find(@NotNull CrabAddressQueryDTO query,
                               @NotNull Integer pageNum,
                               @NotNull Integer pageSize,
                               @Param("orderBy") String orderBy,
                               @Param("sequence") String sequence){

        return ResponseResult.success(getResult(query, pageNum, pageSize, orderBy, sequence));
    }

    private PageResult<List<CrabAddressDTO>> getResult(CrabAddressQueryDTO query,
                                                       Integer pageNum,
                                                       Integer pageSize,
                                                       String orderBy,
                                                       String sequence
    ) {
        PageQuery<CrabAddressQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);
        pageQuery.setSequence(sequence);
        pageQuery.setOrderBy(orderBy);

        return crabAddressService.find(pageQuery);
    }

    @PostMapping
    public ResponseResult insert(@RequestBody CrabAddressDTO crabAddressDTO){
        if(crabAddressService.insert(crabAddressDTO)){
            return ResponseResult.success("新增成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
    }

    @PutMapping("/{id}")
    public ResponseResult update(@RequestBody CrabAddressDTO crabAddressDTO,@PathVariable("id") Long id){
        if(crabAddressService.update(crabAddressDTO, id)){
            return ResponseResult.success("修改成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long id){
        if(crabAddressService.delete(id)){
            return ResponseResult.success("删除成功!");
        }
        return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
    }
}
