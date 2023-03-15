package com.video.oversea.user.controller;

import com.video.oversea.user.service.AppService;
import com.video.oversea.user.service.BaseUserService;
//import com.video.oversea.user.service.UserAuthService;
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
//
//    @Autowired
//    protected UserAuthService userAuthService;

}
