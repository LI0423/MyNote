package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.video.manager.domain.dto.CategoryDTO;
import com.video.manager.domain.entity.CategoryDO;
import com.video.manager.domain.entity.ChannelCategoryDO;
import com.video.manager.domain.entity.MaterialDO;
import com.video.manager.domain.entity.VideoCategoryDO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.CategoryMapper;
import com.video.manager.mapper.MaterialMapper;
import com.video.manager.mapper.VideoCategoryMapper;
import com.video.manager.mapper.VideoMapper;
import com.video.manager.service.CategoryService;
import com.video.manager.service.ChannelCategoryService;
import com.video.manager.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: api
 * @description: category service
 * @author: laojiang
 * @create: 2020-08-19 10:11
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryDO> implements CategoryService {

    private MaterialMapper materialMapper;
    private VideoService videoService;

    @Autowired
    public CategoryServiceImpl(MaterialMapper materialMapper, VideoService videoService) {
        this.materialMapper = materialMapper;
        this.videoService = videoService;
    }

    @Override
    public Boolean save(CategoryDTO categoryDTO) {
        CategoryDO categoryDO1 = query().eq("name", categoryDTO.getName()).one();
        if (categoryDO1 != null) {
            throw new BusinessException(ErrorCodeEnum.NAME_REPEAT);
        }

        CategoryDO categoryDO = new CategoryDO();
        categoryDO.setName(categoryDTO.getName());
        categoryDO.setSort(0);
        categoryDO.setHidden(categoryDTO.getHidden());
        return save(categoryDO);
    }

    @Override
    public Boolean delete(Integer id) {
        //判断是否可以删除
//        Integer count = materialMapper.selectCount(new QueryWrapper<MaterialDO>()
//                .eq(true, "category_id", id));
//        if (count > 0) {
//            throw new BusinessException(ErrorCodeEnum.CATEGORY_NOT_DELETE);
//        }
//
//        videoService.deleteCategory(Integer.parseInt(id + ""));
//        return removeById(id);
        CategoryDO categoryDO = new CategoryDO();
        categoryDO.setHidden(categoryDO.getHidden());
        return updateById(categoryDO);
    }

    @Override
    public Boolean update(CategoryDTO categoryDTO) {

        CategoryDO categoryDO = new CategoryDO();
        categoryDO.setId(categoryDTO.getId());
        categoryDO.setName(categoryDTO.getName());
        categoryDO.setHidden(categoryDTO.getHidden());
        return updateById(categoryDO);
    }

    @Override
    public List<CategoryDTO> searchAll() {
        List<CategoryDO> categoryDOList = query().orderByAsc("sort").list();

        List<CategoryDTO> categoryDTOList = Optional
                .ofNullable(categoryDOList)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(categoryDO -> {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    BeanUtils.copyProperties(categoryDO, categoryDTO);
                    return categoryDTO;
                })
                .collect(Collectors.toList());
        return categoryDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSort(List<Long> idList) {

        int sort = 0;
        List<CategoryDO> categoryDOList = new ArrayList<>();
        for (Long id : idList) {
            CategoryDO categoryDO = new CategoryDO();
            categoryDO.setId(id);
            categoryDO.setSort(sort);
            categoryDOList.add(categoryDO);
            sort++;
        }

        return updateBatchById(categoryDOList);
    }
}
