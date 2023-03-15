package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.DataConfigDTO;
import com.video.manager.domain.dto.DataConfigQueryDTO;
import com.video.manager.domain.dto.ManagerDTO;

public interface DataConfigService {

    Boolean addConfig(ManagerDTO managerDTO, DataConfigDTO dataConfigDTO);

    PageResult<DataConfigDTO> getAll(PageQuery<DataConfigQueryDTO> pageQuery);

    Boolean updateConfig(Long id, DataConfigDTO dataConfigDTO, ManagerDTO managerDTO);

    Boolean deleteConfig(Long id, ManagerDTO managerDTO);

}
