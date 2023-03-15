package com.video.manager.service;

import java.util.List;

/**
 * @program: mango
 * @description: 查询广告位配置
 * @author: laojiang
 * @create: 2020-10-21 16:24
 **/
public interface AppSidService {

    /**
     * 查询广告位的配置
     * @param pkg
     * @return
     */
    List<String> findSid(String pkg);
}
