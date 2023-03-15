package com.video.manager.task;

import com.video.manager.service.RCRewardConfigService;
import com.video.manager.service.RCWithdrawalConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RCWithdrawalConfigTask {

    @Autowired
    RCWithdrawalConfigService rcWithdrawalConfigService;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void asyncCache() {
        log.info("RCWithdrawalConfigTask asyncCache start.");
        rcWithdrawalConfigService.syncCache();
        log.info("RCWithdrawalConfigTask asyncCache end.");
    }

}
