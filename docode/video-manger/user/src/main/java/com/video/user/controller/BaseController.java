package com.video.user.controller;

import com.video.user.service.AppService;
import com.video.user.service.UserAuthService;
import com.video.user.service.BaseUserService;
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
    protected BaseUserService baseUserService;

    @Autowired
    protected UserAuthService userAuthService;

}
