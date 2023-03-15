package com.video.manager.service;

import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.DataConfigRecordQueryDTO;
import com.video.manager.domain.entity.DataConfigRecordDO;

public interface DataConfigRecordService {

    PageResult<DataConfigRecordDO> getAllInfo(PageQuery<DataConfigRecordQueryDTO> pageQuery);

}
