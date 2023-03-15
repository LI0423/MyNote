package com.video.user.service.impl;

import com.video.user.cache.UserAuthCache;
import com.video.user.cache.UserCache;
import com.video.user.mapper.AppMapper;
import com.video.user.mapper.BaseUserMapper;
import com.video.user.mapper.UserAuthMapper;
import com.video.user.mapper.UserIdTokenMappingMapper;
import com.video.user.service.third.ThirdAuthFactoryService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: mango
 * @description: base service
 * @author: laojiang
 * @create: 2020-09-10 13:19
 **/
public abstract class BaseServiceImpl {

    @Autowired
    protected AppMapper appMapper;

    @Autowired
    protected UserAuthMapper userAuthMapper;

    @Autowired
    protected BaseUserMapper baseUserMapper;

    @Autowired
    protected UserCache userCache;

    @Autowired
    protected UserAuthCache userAuthCache;

    @Autowired
    protected ThirdAuthFactoryService thirdAuthFactoryService;

    @Autowired
    protected UserIdTokenMappingMapper userIdTokenMappingMapper;

}
