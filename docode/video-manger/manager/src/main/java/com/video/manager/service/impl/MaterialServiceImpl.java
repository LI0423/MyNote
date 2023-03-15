package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.MaterialDTO;
import com.video.manager.domain.dto.MaterialQueryDTO;
import com.video.manager.domain.entity.MaterialDO;
import com.video.manager.mapper.CategoryMapper;
import com.video.manager.mapper.MaterialCategoryMapper;
import com.video.manager.mapper.MaterialMapper;
import com.video.manager.service.MaterialService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: api
 * @description: material service
 * @author: laojiang
 * @create: 2020-08-19 10:49
 **/
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper,MaterialDO> implements MaterialService {

    private CategoryMapper categoryMapper;
    private MaterialCategoryMapper materialCategoryMapper;

    @Autowired
    public MaterialServiceImpl(CategoryMapper categoryMapper, MaterialCategoryMapper materialCategoryMapper) {
        this.categoryMapper = categoryMapper;
        this.materialCategoryMapper = materialCategoryMapper;
    }

    @Override
    public PageResult<List<MaterialDTO>> query(PageQuery<MaterialQueryDTO> pageQuery) {

        Page page =new Page(pageQuery.getPageNo(),pageQuery.getPageSize());

        QueryWrapper<MaterialDO> queryWrapper=new QueryWrapper();

        if(pageQuery.getQuery().getId()!=null){
            queryWrapper.eq("id",pageQuery.getQuery().getId());
        }

        if(pageQuery.getQuery().getTitle()!=null){
            queryWrapper.like("title",pageQuery.getQuery().getTitle());
        }

        if(pageQuery.getQuery().getTypes()!=null){
            queryWrapper.eq("types",pageQuery.getQuery().getTypes());
        }

        String orderBy= CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if(Constants.ASCENDING.equals(pageQuery.getSequence())){
            queryWrapper.orderByAsc(orderBy);
        }

        if(Constants.DESCENDING.equals(pageQuery.getSequence())){
            queryWrapper.orderByDesc(orderBy);
        }

        IPage<MaterialDO> materialDOIPage=page(page,queryWrapper);

        PageResult pageResult=new PageResult();
        pageResult.setTotal(materialDOIPage.getTotal());

        List<MaterialDTO> materialDTOList= Optional
                .ofNullable(materialDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(materialDO -> {
                    MaterialDTO materialDTO=new MaterialDTO();
                    BeanUtils.copyProperties(materialDO,materialDTO);
                    return materialDTO;
                })
                .collect(Collectors.toList());

        pageResult.setLists(materialDTOList);

        return pageResult;
    }

    @Override
    public Boolean updateTime(Long materialId) {

        MaterialDO materialDO=new MaterialDO();
        materialDO.setId(materialId);
        materialDO.setUtime(System.currentTimeMillis());

        return updateById(materialDO);
    }
}
