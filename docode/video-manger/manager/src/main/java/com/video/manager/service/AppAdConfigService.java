package com.video.manager.service;

import com.video.entity.AppAdConfigStatusEnum;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.AppAdConfigDTO;
import com.video.manager.domain.dto.AppAdConfigListDTO;
import com.video.manager.domain.dto.AppAdConfigQueryDTO;
import com.video.manager.domain.dto.CreateAppAdConfigDTO;

import java.util.List;

/**
 * @program: mango
 * @description: app ad config service
 * @author: laojiang
 * @create: 2020-10-21 16:16
 **/
public interface AppAdConfigService {

    /**
     * 添加配置
     * @param createAppAdConfigDTO
     * @return
     */
    Boolean create(CreateAppAdConfigDTO createAppAdConfigDTO);

    /**
     * 修改配置
     * @param createAppAdConfigDTO
     * @param id
     * @return
     */
    Boolean modify(CreateAppAdConfigDTO createAppAdConfigDTO,Long id);

    /**
     * 修改状态
     * @param id
     * @param status
     * @return
     */
    Boolean updateStatus(Long id, AppAdConfigStatusEnum status);

    /**
     * 查询，支持分页
     * @param page
     * @return
     */
    PageResult<List<AppAdConfigListDTO>> findPage(PageQuery<AppAdConfigQueryDTO> page);

    /**
     * 查询配置信息
     * @param id
     * @return
     */
    AppAdConfigDTO findOne(Long id);
}
