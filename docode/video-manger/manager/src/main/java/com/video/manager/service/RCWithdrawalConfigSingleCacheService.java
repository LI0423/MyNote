package com.video.manager.service;

import com.video.entity.RCWithdrawalConfigDO;
import com.video.manager.domain.dto.RCWithdrawalConfigAddRespDTO;
import com.video.manager.domain.dto.RCWithdrawalConfigQueryRespDTO;
import com.video.manager.domain.dto.RCWithdrawalConfigUpdateRespDTO;

import java.util.List;

public interface RCWithdrawalConfigSingleCacheService {

    void syncSingleCache(RCWithdrawalConfigDO rcWithdrawalConfigDO);

}
