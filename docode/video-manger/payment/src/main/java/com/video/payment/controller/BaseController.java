package com.video.payment.controller;

import com.video.payment.service.AppService;
import com.video.payment.service.UserAuthService;
import com.video.payment.service.third.ThirdPaymentFactoryService;
import com.video.payment.service.third.ThirdRefundFactoryService;
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

    @Autowired
    protected ThirdPaymentFactoryService thirdPaymentFactoryService;

    @Autowired
    protected ThirdRefundFactoryService thirdRefundFactoryService;
}
