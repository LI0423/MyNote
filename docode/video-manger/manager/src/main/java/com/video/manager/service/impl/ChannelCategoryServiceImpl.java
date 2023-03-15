package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.video.manager.domain.entity.ChannelCategoryDO;
import com.video.manager.mapper.ChannelCategoryMapper;
import com.video.manager.mapper.VideoCategoryMapper;
import com.video.manager.service.ChannelCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelCategoryServiceImpl extends ServiceImpl<ChannelCategoryMapper, ChannelCategoryDO> implements ChannelCategoryService {
    @Autowired
    ChannelCategoryMapper channelCategoryMapper;
    @Autowired
    VideoCategoryMapper videoCategoryMapper;

    @Override
    public List<ChannelCategoryDO> findChannel(Integer categoryId){
        return channelCategoryMapper.selectList(new QueryWrapper<ChannelCategoryDO>().eq("category_id",categoryId));
    }

    @Override
    public void delete(Integer categoryId, Integer channelId) {
        channelCategoryMapper.delete(new QueryWrapper<ChannelCategoryDO>().eq("channel_id", channelId).eq("category_id",categoryId));
    }


    @Override
    public void add(Integer categoryId, Integer channelId) {
        ChannelCategoryDO channelCategoryDO = ChannelCategoryDO.builder().categoryId(categoryId).channelId(channelId).build();
        saveOrUpdate(channelCategoryDO);
    }

}
