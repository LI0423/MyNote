package com.video.manager.service;


import com.video.manager.domain.entity.ChannelCategoryDO;

import java.util.List;

public interface ChannelCategoryService {

    List<ChannelCategoryDO> findChannel(Integer categoryId);

    void delete(Integer categoryId, Integer id);

    void add(Integer categoryId, Integer id);
}
