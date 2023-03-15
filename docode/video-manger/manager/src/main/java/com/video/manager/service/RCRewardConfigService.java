package com.video.manager.service;

import com.video.manager.domain.dto.RCRewardConfigAddRespDTO;
import com.video.manager.domain.dto.RCRewardConfigQueryRespDTO;
import com.video.manager.domain.dto.RCRewardConfigUpdateRespDTO;

import java.util.List;

public interface RCRewardConfigService {

    RCRewardConfigQueryRespDTO query(String pkg, List<String> tokenList);

    RCRewardConfigAddRespDTO add(String pkg, List<String> token, Float coefficient);

    RCRewardConfigUpdateRespDTO update(String pkg, List<String> token, Float coefficient);

    void syncCache();

}
