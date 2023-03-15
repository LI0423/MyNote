package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.DataConfigDTO;
import com.video.manager.domain.dto.DataConfigQueryDTO;
import com.video.manager.domain.dto.ManagerDTO;
import com.video.manager.domain.entity.DataConfigDO;
import com.video.manager.domain.entity.DataConfigRecordDO;
import com.video.manager.mapper.DataConfigMapper;
import com.video.manager.mapper.DataConfigRecordMapper;
import com.video.manager.service.DataConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DataConfigServiceImpl implements DataConfigService {

    @Autowired
    DataConfigMapper dataConfigMapper;

    @Autowired
    DataConfigRecordMapper dataConfigRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addConfig(ManagerDTO managerDTO, DataConfigDTO dataConfigDTO) {

        Boolean aBoolean = dataConfigMapper.ifRepeat(dataConfigDTO.getItem(),null);
        if (aBoolean!=null&&aBoolean){
            return false;
        }
        DataConfigDO dataConfigDO = new DataConfigDO();
        BeanUtils.copyProperties(dataConfigDTO, dataConfigDO);
        dataConfigDO.setCreator(managerDTO.getName());
        dataConfigDO.setStatus(1);
        int insert = dataConfigMapper.insert(dataConfigDO);
        return insert > 0;

    }

    @Override
    public PageResult<DataConfigDTO> getAll(PageQuery<DataConfigQueryDTO> pageQuery) {
        QueryWrapper<DataConfigDO> queryWrapper = new QueryWrapper<>();

        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());

        if (pageQuery.getQuery().getItem() != null) {
            queryWrapper.like("item", pageQuery.getQuery().getItem());
        }

        if (pageQuery.getQuery().getRemark() != null) {
            queryWrapper.like("remark", pageQuery.getQuery().getRemark());
        }

        if (pageQuery.getSequence()!=null){
            if ("descending".equals(pageQuery.getSequence())){
                queryWrapper.orderByDesc(orderBy);
            }else {
                queryWrapper.orderByAsc(orderBy);
            }
        }

        queryWrapper.eq("status",1);

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());
        IPage<DataConfigDO> dataList = dataConfigMapper.selectPage(page, queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(dataList.getTotal());
        pageResult.setLists(dataList.getRecords());

        return pageResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateConfig(Long id, DataConfigDTO dataConfigDTO, ManagerDTO managerDTO) {

        DataConfigDO source = dataConfigMapper.selectById(id);
        if (source!=null){
            DataConfigRecordDO dataConfigRecordDO = new DataConfigRecordDO();
            dataConfigRecordDO.setSItem(source.getItem());
            dataConfigRecordDO.setTItem(dataConfigDTO.getItem());
            dataConfigRecordDO.setSValue(source.getValue());
            dataConfigRecordDO.setTValue(dataConfigDTO.getValue());
            dataConfigRecordDO.setSRemark(source.getRemark());
            dataConfigRecordDO.setTRemark(dataConfigDTO.getRemark());
            dataConfigRecordDO.setSStatus(source.getStatus());
            dataConfigRecordDO.setTStatus(dataConfigDTO.getStatus());
            dataConfigRecordDO.setOperator(managerDTO.getName());
            dataConfigRecordMapper.insert(dataConfigRecordDO);
        }

        Boolean aBoolean = dataConfigMapper.ifRepeat(dataConfigDTO.getItem(),id);
        if (aBoolean!=null&&aBoolean){
            return false;
        }
        DataConfigDO dataConfigDO = new DataConfigDO();
        BeanUtils.copyProperties(dataConfigDTO, dataConfigDO);
        dataConfigDTO.setId(id);
        dataConfigDO.setModifier(managerDTO.getName());
        dataConfigDO.setUpdateTime(new Date());
        int i = dataConfigMapper.updateById(dataConfigDO);
        return i > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteConfig(Long id, ManagerDTO managerDTO) {
        DataConfigDO dataConfigDO = dataConfigMapper.selectById(id);
        if (dataConfigDO!=null){
            DataConfigRecordDO dataConfigRecordDO = new DataConfigRecordDO();
            dataConfigRecordDO.setSItem(dataConfigDO.getItem());
            dataConfigRecordDO.setSValue(dataConfigDO.getValue());
            dataConfigRecordDO.setSRemark(dataConfigDO.getRemark());
            dataConfigRecordDO.setSStatus(dataConfigDO.getStatus());
            dataConfigRecordDO.setTStatus(0);
            dataConfigRecordDO.setOperator(managerDTO.getName());
            dataConfigRecordMapper.insert(dataConfigRecordDO);
        }

        Boolean aBoolean = dataConfigMapper.updateStatus(id, managerDTO.getName());
        return aBoolean;
    }
}
