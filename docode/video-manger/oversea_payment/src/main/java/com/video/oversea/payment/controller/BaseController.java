package com.video.oversea.payment.controller;

import com.video.oversea.payment.service.third.ThirdPaymentFactoryService;
import com.video.oversea.payment.service.AppService;
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
    protected ThirdPaymentFactoryService thirdPaymentFactoryService;

}
