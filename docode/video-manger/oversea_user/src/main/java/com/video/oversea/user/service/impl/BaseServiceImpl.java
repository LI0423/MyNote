package com.video.oversea.user.service.impl;

import com.video.oversea.user.cache.UserCache;
import com.video.oversea.user.mapper.AppMapper;
import com.video.oversea.user.mapper.BaseUserMapper;
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
    protected BaseUserMapper baseUserMapper;

    @Autowired
    protected UserCache userCache;

}
