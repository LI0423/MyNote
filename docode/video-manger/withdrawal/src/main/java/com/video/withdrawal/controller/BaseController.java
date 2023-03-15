package com.video.withdrawal.controller;

import com.video.withdrawal.service.AppService;
import com.video.withdrawal.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: mango
 * @description: base controller
 * @author: laojiang
 * @create: 2020-09-10 13:02
 **/
public abstract class BaseController {

    @Autowired
    protected AppService appService;

    @Autowired
    protected UserAuthService userAuthService;

}
