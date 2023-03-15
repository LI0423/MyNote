package com.video.manager.controller;

import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.CategoryDTO;
import com.video.manager.domain.dto.CategorySortDTO;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.service.CategoryService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * @program: api
 * @description: category
 * @author: laojiang
 * @create: 2020-08-19 11:16
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseResult save(@NotNull @RequestBody CategoryDTO categoryDTO) {

        Boolean result = categoryService.save(categoryDTO);
        if (result) {
            return ResponseResult.success("增加成功");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
        }
    }

    @PostMapping("{categoryId}")
    public ResponseResult update(@NotNull @RequestBody CategoryDTO categoryDTO,
                                 @PathVariable Long categoryId) {
        categoryDTO.setId(categoryId);
        Boolean result = categoryService.update(categoryDTO);
        if (result) {
            return ResponseResult.success("修改成功");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @DeleteMapping("{categoryId}")
    public ResponseResult delete(@PathVariable Integer categoryId) {
        Boolean result = categoryService.delete(categoryId);
        if (result) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.DELETE_FAILURE);
        }
    }

    @PostMapping("/sort")
    public ResponseResult saveSort(@NotNull @RequestBody CategorySortDTO categorySortDTO) {

        Boolean result = categoryService.saveSort(Arrays.asList(categorySortDTO.getIdList()));
        if (result) {
            return ResponseResult.success("保存成功");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    @GetMapping
    public ResponseResult searchAll() {

        List<CategoryDTO> categoryDTOList = categoryService.searchAll();

        return ResponseResult.success(categoryDTOList);
    }


}
