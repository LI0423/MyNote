package com.video.manager.task;

import com.video.manager.service.RCRewardConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RCRewardConfigTask {

    @Autowired
    RCRewardConfigService rcRewardConfigService;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void asyncCache() {
        log.info("RCRewardConfigTask asyncCache start.");
        rcRewardConfigService.syncCache();
        log.info("RCRewardConfigTask asyncCache end.");
    }

}
