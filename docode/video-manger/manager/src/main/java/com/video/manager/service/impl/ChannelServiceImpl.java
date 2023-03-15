package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.video.entity.AppDO;
import com.video.manager.domain.dto.AppDTO;
import com.video.manager.domain.dto.ChannelDTO;
import com.video.manager.domain.dto.VideoCategoryDTO;
import com.video.manager.domain.entity.ChannelCategoryDO;
import com.video.manager.domain.entity.ChannelDO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.ChannelCategoryMapper;
import com.video.manager.mapper.ChannelMapper;
import com.video.manager.mapper.VideoCategoryMapper;
import com.video.manager.service.AppService;
import com.video.manager.service.ChannelService;
import com.video.manager.service.ManagerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, ChannelDO> implements ChannelService {
    @Autowired
    ChannelMapper channelMapper;
    @Autowired
    VideoCategoryMapper videoCategoryMapper;
    @Autowired
    ChannelCategoryMapper channelCategoryMapper;
    @Autowired
    AppService appService;
    @Autowired
    ManagerService managerService;


    @Override
    public void add(ChannelDTO channelDTO) {
        channelMapper.insert(buildDO(channelDTO));
    }

    private ChannelDO buildDO(ChannelDTO channelDTO) {
        AppDTO appDTO = appService.findOne(Long.parseLong(channelDTO.getAppId()));
        if (appDTO != null) {
            String pkg = appDTO.getPkg();
            return ChannelDO.builder()
                    .channelName(channelDTO.getChannelName())
                    .pkg(pkg)
                    .sort(channelDTO.getSort())
                    .source(channelDTO.getSource())
                    .sourceChannelId(channelDTO.getSourceChannelId())
                    .build();
        } else {
            throw new BusinessException(ErrorCodeEnum.APP_FORBIDDEN);
        }
    }

    @Override
    public void update(ChannelDTO channelDTO) {
        ChannelDO channelDO = buildDO(channelDTO);
        channelDO.setId(channelDTO.getId());
        updateById(channelDO);
    }

    @Override
    public List<ChannelDO> findByChannelIdIn(List<Integer> ids) {
        return channelMapper.selectBatchIds(ids);
    }

    @Override
    public List<ChannelDTO> find(Long appId) {
        List<String> apps = managerService.listApp();
        AppDTO appDTO = appService.findOne(appId);
        if (!apps.contains("all") && !apps.contains(appDTO.getPkg())) {
            throw new BusinessException(ErrorCodeEnum.APP_FORBIDDEN);
        }


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("sort");
        queryWrapper.eq("pkg", appDTO.getPkg());

        List<ChannelDO> channelDOList = channelMapper.selectList(queryWrapper);

        List<ChannelCategoryDO> channelCategoryDOList = channelCategoryMapper.selectList(new QueryWrapper<ChannelCategoryDO>());

        Map<Integer, List<Integer>> channelCategoryMap = new HashMap<>();
        for (ChannelCategoryDO channelCategoryDO : channelCategoryDOList) {
            List<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(channelCategoryDO.getCategoryId());
            if (channelCategoryMap.get(channelCategoryDO.getChannelId()) != null) {
                categoryIds.addAll(channelCategoryMap.get(channelCategoryDO.getChannelId()));
            }
            channelCategoryMap.put(channelCategoryDO.getChannelId(), categoryIds);
        }

        List<ChannelDTO> channelDTOList = Optional
                .ofNullable(channelDOList)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(channelDO -> {
                    ChannelDTO channelDTO = new ChannelDTO();
                    BeanUtils.copyProperties(channelDO, channelDTO);
                    channelDTO.setCategoryIds(channelCategoryMap.get(channelDTO.getId()) == null ? new ArrayList<>() : channelCategoryMap.get(channelDTO.getId()));
                    channelDTO.setAppId(appId + "");
                    return channelDTO;
                })
                .collect(Collectors.toList());
        return channelDTOList;
    }

}
