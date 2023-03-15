package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.video.manager.domain.common.*;
import com.video.manager.domain.dto.VideoChannelDTO;
import com.video.manager.domain.dto.VideoDTO;
import com.video.manager.domain.dto.VideoQueryDTO;
import com.video.manager.domain.entity.*;
import com.video.manager.mapper.*;
import com.video.manager.service.*;
import com.video.manager.shiro.MyShiroCasRealm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: api
 * @description: material service
 * @author: laojiang
 * @create: 2020-08-19 10:49
 **/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoDO> implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    private VideoCategoryMapper videoCategoryMapper;
    private VideoMapper videoMapper;
    private ChannelCategoryService channelCategoryService;

    @Autowired
    ObsManager obsManager;

    @Autowired
    ChannelService channelService;

    @Autowired
    public VideoServiceImpl(
            VideoCategoryMapper videoCategoryMapper,
            VideoMapper videoMapper,
            ChannelCategoryService channelCategoryService
    ) {
        this.videoCategoryMapper = videoCategoryMapper;
        this.videoMapper = videoMapper;
        this.channelCategoryService = channelCategoryService;
    }

    @Override
    public PageResult<List<VideoDTO>> query(PageQuery<VideoQueryDTO> pageQuery) {

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());

        QueryWrapper<VideoDO> queryWrapper = new QueryWrapper();

        if (pageQuery.getQuery().getCategoryId() != null && pageQuery.getQuery().getCategoryId() == -1) {
            queryWrapper.notInSql("id",
                    "select distinct video_id from video_category");
        } else if (pageQuery.getQuery().getCategoryId() != null) {
            queryWrapper.inSql("id",
                    "select video_id from video_category where category_id="
                            + pageQuery.getQuery().getCategoryId());
        }

        if (pageQuery.getQuery().getId() != null) {
            queryWrapper.eq("id", pageQuery.getQuery().getId());
        }

        if (pageQuery.getQuery().getTitle() != null) {
            queryWrapper.like("title", pageQuery.getQuery().getTitle());
        }

        if (pageQuery.getQuery().getTypes() != null) {
            queryWrapper.eq("types", pageQuery.getQuery().getTypes());
        }

        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        IPage<VideoDO> videoDOIPage = page(page, queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(videoDOIPage.getTotal());

        List<VideoDTO> videoDTOList = Optional
                .ofNullable(videoDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(videoDO -> {
                    VideoDTO videoDTO = new VideoDTO();
                    BeanUtils.copyProperties(videoDO, videoDTO);
                    videoDTO.setCategoryIds(getCategoryIdList(videoDO.getId()));
                    return videoDTO;
                })
                .collect(Collectors.toList());

        pageResult.setLists(videoDTOList);

        return pageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCategory(Integer categoryId, Long videoId) {
        VideoCategoryDO videoCategoryDO = new VideoCategoryDO();
        videoCategoryDO.setCategoryId(Long.valueOf(categoryId));
        videoCategoryDO.setVideoId(videoId);
        videoCategoryMapper.insert(videoCategoryDO);

        VideoDO videoDO = videoMapper.selectOne(new QueryWrapper<VideoDO>().eq("id", videoId));
        videoDO.setCategoryIds(videoDO.getCategoryIds() + "," + categoryId);
        videoMapper.updateById(videoDO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Integer categoryId, Long videoId) {
        videoCategoryMapper.delete(new QueryWrapper<VideoCategoryDO>().eq("video_id", videoId).eq("category_id", categoryId));

        VideoDO videoDO = videoMapper.selectOne(new QueryWrapper<VideoDO>().eq("id", videoId));
        if (StringUtils.isNotEmpty(videoDO.getCategoryIds())) {
            List<String> cateList = Arrays.asList(videoDO.getCategoryIds().split(","));
            List<String> categoryList = new ArrayList<>(cateList);
            categoryList.remove(categoryId + "");
            videoDO.setCategoryIds(String.join(",", categoryList));
            videoMapper.updateById(videoDO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Integer categoryId) {
        List<ChannelCategoryDO> channelCategoryDOList = channelCategoryService.findChannel(categoryId);
        channelCategoryDOList.forEach(channelCategoryDO -> {
            channelCategoryService.delete(categoryId, channelCategoryDO.getChannelId());
        });
        videoCategoryMapper.delete(new QueryWrapper<VideoCategoryDO>().eq("category_id", categoryId));
    }


    private List<Long> getCategoryIdList(Long videoId) {
        List<VideoCategoryDO> videoCategoryDOList = videoCategoryMapper
                .selectList(new QueryWrapper<VideoCategoryDO>()
                        .eq("video_id", videoId));
        List<Long> categoryIds = new ArrayList<>();
        for (VideoCategoryDO videoCategoryDO : videoCategoryDOList) {
            categoryIds.add(videoCategoryDO.getCategoryId());
        }
        return categoryIds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCategory(List<Integer> categoryIdList, Long videoId) {

        Integer result = 0;

        result = videoCategoryMapper.delete(new QueryWrapper<VideoCategoryDO>().eq("video_id", videoId));

        for (Integer categoryId : categoryIdList) {
            VideoCategoryDO videoCategoryDO = new VideoCategoryDO();
            videoCategoryDO.setCategoryId(Long.parseLong(categoryId + ""));
            videoCategoryDO.setVideoId(videoId);
            result += videoCategoryMapper.insert(videoCategoryDO);
        }

        return result > 0 ? true : false;
    }

    @Override
    public Boolean updateContentLevel(Long videoId, Integer contentLevel) {

        VideoDO videoDO = new VideoDO();
        videoDO.setContentLevel(ContentLevelEnum.values()[contentLevel]);
        videoDO.setId(videoId);

        return updateById(videoDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long videoId) {
        VideoDO videoDO = videoMapper.selectById(videoId);
        List<String> pathList = new ArrayList<>();
        pathList.add(videoDO.getCoverUrl());
        pathList.add(videoDO.getFileUrl());
        obsManager.batchDeleteFile(pathList);
        videoCategoryMapper.delete(new QueryWrapper<VideoCategoryDO>().eq("video_id", videoId));
        return removeById(videoId);
    }

    @Override
    public Boolean refresh(Long videoId) {
        VideoDO videoDO = new VideoDO();
        videoDO.setId(videoId);
        return updateById(videoDO);
    }

    @Override
    public void sync() {
        for (long i = 80; i <= 777884; i++) {
            List<VideoCategoryDO> videoCategoryDOList = videoCategoryMapper.selectList(
                    new QueryWrapper<VideoCategoryDO>().eq("video_id", i));
            if (videoCategoryDOList != null && !videoCategoryDOList.isEmpty()) {
                List<Long> categoryIds = new ArrayList<>();
                for (VideoCategoryDO videoCategoryDO : videoCategoryDOList) {
                    categoryIds.add(videoCategoryDO.getCategoryId());
                }
                VideoDO videoDO = videoMapper.selectOne(
                        new QueryWrapper<VideoDO>().eq("id", i));

                if (videoDO == null) {
                    continue;
                }

                if (videoDO.getLastModifyTime() != null) {
                    videoDO.setLastModifyTime(videoDO.getLastModifyTime());
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(1970, Calendar.JANUARY, 1, 0, 0, 0);
                    Date date = calendar.getTime();
                    videoDO.setLastModifyTime(date);
                }

                if (videoDO.getLastModifyBy() != null) {
                    videoDO.setLastModifyBy(videoDO.getLastModifyBy());
                } else {
                    videoDO.setLastModifyBy("1218init");
                }

                videoDO.setCategoryIds(StringUtils.join(categoryIds, ","));
                updateById(videoDO);
            }
        }
    }
}
