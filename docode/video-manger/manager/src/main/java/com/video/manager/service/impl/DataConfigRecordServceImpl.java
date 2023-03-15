package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.DataConfigRecordQueryDTO;
import com.video.manager.domain.entity.DataConfigRecordDO;
import com.video.manager.mapper.DataConfigRecordMapper;
import com.video.manager.service.DataConfigRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataConfigRecordServceImpl implements DataConfigRecordService {

    @Autowired
    DataConfigRecordMapper dataConfigRecordMapper;

    @Override
    public PageResult<DataConfigRecordDO> getAllInfo(PageQuery<DataConfigRecordQueryDTO> pageQuery) {

        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());

        QueryWrapper<DataConfigRecordDO> queryWrapper = new QueryWrapper<>();

        if (pageQuery.getQuery().getOperator()!=null){
            queryWrapper.like("operator",pageQuery.getQuery().getOperator());
        }

        if (pageQuery.getQuery().getSItem()!=null){
            queryWrapper.like("s_item",pageQuery.getQuery().getSItem());
        }

        if (pageQuery.getQuery().getTItem()!=null){
            queryWrapper.like("t_item",pageQuery.getQuery().getTItem());
        }

        if (pageQuery.getQuery().getTStatus()!=null){
            queryWrapper.eq("t_status",pageQuery.getQuery().getTStatus());
        }

        if (pageQuery.getQuery().getBeginTime()!=null){
            queryWrapper.between("createTime",pageQuery.getQuery().getBeginTime()+" 00:00:00",
                    pageQuery.getQuery().getEndTime()+" 23:59:59");
        }

        if ("descending".equals(pageQuery.getSequence())){
            queryWrapper.orderByDesc(orderBy);
        }else {
            queryWrapper.orderByAsc(orderBy);
        }

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());
        IPage<DataConfigRecordDO>  result = dataConfigRecordMapper.selectPage(page,queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(result.getTotal());
        pageResult.setLists(result.getRecords());

        return pageResult;
    }
}
