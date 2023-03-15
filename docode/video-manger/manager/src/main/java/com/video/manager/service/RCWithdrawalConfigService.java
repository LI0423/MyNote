package com.video.manager.service;

import com.video.manager.domain.dto.RCWithdrawalConfigAddRespDTO;
import com.video.manager.domain.dto.RCWithdrawalConfigQueryRespDTO;
import com.video.manager.domain.dto.RCWithdrawalConfigUpdateRespDTO;

import java.util.List;

public interface RCWithdrawalConfigService {

    RCWithdrawalConfigQueryRespDTO query(String pkg, List<String> token);

    RCWithdrawalConfigAddRespDTO add(String pkg, List<String> token, Long ceiling);

    RCWithdrawalConfigUpdateRespDTO update(String pkg, List<String> token, Long ceiling);

    void syncCache();

}
