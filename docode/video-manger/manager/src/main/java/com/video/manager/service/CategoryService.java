package com.video.manager.service;

import com.video.manager.domain.dto.CategoryDTO;

import java.util.List;

/**
 * @program: api
 * @description: category service
 * @author: laojiang
 * @create: 2020-08-18 14:49
 **/
public interface CategoryService {

    /**
     * 添加分类
     * @param categoryDTO
     * @return
     */
    Boolean save(CategoryDTO categoryDTO);

    /**
     * 删除视频，如果该分类下存在视频则不能删除
     * @param id 分类编号
     * @return
     */
    Boolean delete(Integer id);


    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    Boolean update(CategoryDTO categoryDTO);

    /**
     * 查询分类，按照sort的排序返回
     * @return
     */
    List<CategoryDTO> searchAll();

    /**
     * 更新分类排序
     * @param idList
     * @return
     */
    Boolean saveSort(List<Long> idList);

}
