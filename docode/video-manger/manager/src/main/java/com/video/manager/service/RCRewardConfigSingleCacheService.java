package com.video.manager.service;

import com.video.entity.RCRewardConfigDO;
import com.video.entity.RCWithdrawalConfigDO;
import com.video.manager.domain.dto.RCRewardConfigAddRespDTO;
import com.video.manager.domain.dto.RCRewardConfigQueryRespDTO;
import com.video.manager.domain.dto.RCRewardConfigUpdateRespDTO;

import java.util.List;

public interface RCRewardConfigSingleCacheService {

    void syncSingleCache(RCRewardConfigDO rcRewardConfigDO);

}
