package com.video.manager.task;

import com.video.manager.excel.ExcelUtil;
import com.video.manager.service.UserService;
import com.video.manager.service.WithdrawalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserWithdrawTask {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    @Autowired
    UserService userService;

//    @Scheduled(cron = "15 0/30 * * * * ")
    private void withdrawTimes() {
        logger.info("start to update withdrawTimes");
        userService.withdrawTimes();
    }
}

