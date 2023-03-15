package com.video.manager.service;

import com.video.manager.domain.dto.CategoryDTO;
import com.video.manager.domain.dto.ChannelDTO;
import com.video.manager.domain.dto.VideoCategoryDTO;
import com.video.manager.domain.entity.ChannelDO;

import java.util.List;

public interface ChannelService {

    void add(ChannelDTO channelDTO);

    void update(ChannelDTO channelDTO);

    List<ChannelDTO> find(Long appId);

    List<ChannelDO> findByChannelIdIn(List<Integer> ids);

}
