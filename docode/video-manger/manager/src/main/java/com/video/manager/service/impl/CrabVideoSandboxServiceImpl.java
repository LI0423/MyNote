package com.video.manager.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.video.manager.domain.common.*;
import com.video.manager.domain.dto.CrabVideoSandboxDTO;
import com.video.manager.domain.dto.MaterialNewDTO;
import com.video.manager.domain.entity.MaterialNewDO;
import com.video.manager.domain.entity.VideoDO;
import com.video.manager.domain.entity.VideoSandboxCategoryDO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.*;
import com.video.manager.service.CrabVideoSandboxService;
import com.video.manager.service.VideoService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: video-manger
 * @description: CrabVideoSandboxService
 * @author: laojiang
 * @create: 2020-09-07 15:05
 **/
@Service
@DS("mysql_2")
public class CrabVideoSandboxServiceImpl extends ServiceImpl<CrabVideoSandboxMapper, MaterialNewDO>
        implements CrabVideoSandboxService {

    private VideoSandboxCategoryMapper videoSandboxCategoryMapper;
    private VideoMapper videoMapper;

    @Autowired
    public CrabVideoSandboxServiceImpl(VideoSandboxCategoryMapper videoSandboxCategoryMapper,
                                       VideoMapper videoMapper) {
        this.videoSandboxCategoryMapper = videoSandboxCategoryMapper;
        this.videoMapper = videoMapper;
    }

    @Autowired
    VideoService videoService;

    @Override
    public PageResult<List<MaterialNewDTO>> find(PageQuery<MaterialNewDTO> pageQuery) {

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());

        QueryWrapper<MaterialNewDO> queryWrapper = new QueryWrapper();

        if (pageQuery.getQuery().getId() != null) {
            queryWrapper.eq("id", pageQuery.getQuery().getId());
        }

        if (pageQuery.getQuery().getTitle() != null) {
            queryWrapper.like("title", pageQuery.getQuery().getTitle());
        }

        if (pageQuery.getQuery().getTypes() != null) {
            queryWrapper.eq("types", pageQuery.getQuery().getTypes());
        }
        if (pageQuery.getQuery().getStatus() != null) {
            queryWrapper.eq("status", pageQuery.getQuery().getStatus());
        }
        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        IPage<MaterialNewDO> materialNewDOIPage = page(page, queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(materialNewDOIPage.getTotal());
        List<CrabVideoSandboxDTO> materialNewDTOList = Optional
                .ofNullable(materialNewDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(materialNewDO -> {
                    CrabVideoSandboxDTO crabVideoSandboxDTO = new CrabVideoSandboxDTO();
                    BeanUtils.copyProperties(materialNewDO, crabVideoSandboxDTO);
                    crabVideoSandboxDTO.setCoverUrl(materialNewDO.getBsyImgUrl())
                            .setFileUrl(materialNewDO.getBsyUrl())
                            .setCategoryIds(getCategoryIdList(materialNewDO.getId()))
                            .setAuthorHeadUrl(materialNewDO.getBsyHeadUrl())
                            .setDuration(StringUtils.isEmpty(materialNewDO.getVideoTime()) ? 0 : Long.parseLong(materialNewDO.getVideoTime().substring(0, 2)) * 60 + Long.parseLong(materialNewDO.getVideoTime().substring(3, 5)))
                            .setSource(materialNewDO.getSource());
                    return crabVideoSandboxDTO;
                })
                .collect(Collectors.toList());

        pageResult.setLists(materialNewDTOList);

        return pageResult;
    }

    @Override
    public List<Integer> getCategoryIdList(Long videoId) {
        List<VideoSandboxCategoryDO> videoSandboxCategoryDOList = videoSandboxCategoryMapper
                .selectList(new QueryWrapper<VideoSandboxCategoryDO>()
                        .eq("video_sandbox_id", videoId));
        List<Integer> categoryIds = new ArrayList<>();
        for (VideoSandboxCategoryDO videoSandboxCategoryDO : videoSandboxCategoryDOList) {
            categoryIds.add(Integer.parseInt(videoSandboxCategoryDO.getCategoryId() + ""));
        }
        return categoryIds;
    }


    @Override
    @DS("mysql_1")
    @Transactional(rollbackFor = Exception.class)
    public Boolean addCategory(Integer categoryId, Long videoId) {
        int result = 0;

        VideoSandboxCategoryDO videoSandboxCategoryDO = new VideoSandboxCategoryDO();
        videoSandboxCategoryDO.setCategoryId(categoryId.longValue());
        videoSandboxCategoryDO.setVideoSandboxId(videoId);
        result += videoSandboxCategoryMapper.insert(videoSandboxCategoryDO);


        return result > 0;
    }

    @Override
    @DS("mysql_1")
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteCategory(Integer categoryId, Long videoId) {

        Integer result = videoSandboxCategoryMapper.delete(new QueryWrapper<VideoSandboxCategoryDO>().eq("video_sandbox_id", videoId));
        VideoSandboxCategoryDO videoSandboxCategoryDO = new VideoSandboxCategoryDO();
        videoSandboxCategoryDO.setCategoryId(categoryId.longValue());
        videoSandboxCategoryDO.setVideoSandboxId(videoId);
        return result > 0;
    }


    @Override
    public Boolean updateContentLevel(Long videoId, Integer contentLevel) {

        MaterialNewDO materialNewDO = new MaterialNewDO();
        materialNewDO.setContentLevel(ContentLevelEnum.values()[contentLevel]);
        materialNewDO.setId(videoId);

        return updateById(materialNewDO);
    }

    @Override
    @DS("mysql_1")
    @Transactional(rollbackFor = Exception.class)
    //todo:去掉id
    public void publish(MaterialNewDO materialNewDO) {
        QueryWrapper<VideoDO> videoWrapper = new QueryWrapper();
        Long id = materialNewDO.getId();
        if (videoMapper.selectList(videoWrapper.eq("source_video_id", id)).size() > 0) {
            throw new BusinessException(ErrorCodeEnum.VIDEO_REPEAT);
        }
        QueryWrapper<VideoSandboxCategoryDO> queryWrapper = new QueryWrapper();
        if (videoSandboxCategoryMapper.selectCount(queryWrapper.eq("video_sandbox_id", id)) == 0) {
            throw new BusinessException(ErrorCodeEnum.CATEGORY_EMPTY);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        List<Integer> categoryIdList = getCategoryIdList(materialNewDO.getId());
        String categoryIds = StringUtils.join(categoryIdList, ",");
        VideoDO videoDO = new VideoDO();
        videoDO.setFileUrl(materialNewDO.getBsyUrl())
                .setTitle(materialNewDO.getTitle())
                .setCoverUrl(materialNewDO.getBsyImgUrl())
                .setPraiseNum(StringUtils.isEmpty(materialNewDO.getLoveCount()) ? RandomUtils.nextLong(1, 500) : Long.parseLong(materialNewDO.getLoveCount()))
                .setViews(StringUtils.isEmpty(materialNewDO.getWatchCount()) ? RandomUtils.nextLong(1, 500) : Long.parseLong(materialNewDO.getWatchCount()))
                .setAuthor(materialNewDO.getVideoAuthor())
                .setAuthorHeadUrl(materialNewDO.getBsyHeadUrl())
                .setDuration(StringUtils.isEmpty(materialNewDO.getVideoTime()) ? 0 : Long.parseLong(materialNewDO.getVideoTime().substring(0, 2)) * 60 + Long.parseLong(materialNewDO.getVideoTime().substring(3, 5)))
                .setSize(StringUtils.isEmpty(materialNewDO.getVideoSize()) ? 0 : new Double(Double.parseDouble(materialNewDO.getVideoSize()) * 1024 * 1024).longValue())
                .setTypes(materialNewDO.getTypes())
                .setContentLevel(materialNewDO.getContentLevel())
                .setSourceVideoId(String.valueOf(materialNewDO.getId()))
                .setSource(materialNewDO.getSource())
                .setLastModifyTime(date)
                .setCategoryIds(categoryIds)
                .setRedisStatus(0)
                .setCreateTime(new Date());
        //todo：返回id
        int videoId = videoMapper.insert(videoDO);
        videoService.updateCategory(categoryIdList, (long) videoId);
    }

    @Override
    public MaterialNewDO findMaterialNewById(Long id) {
        MaterialNewDO materialNewDO = getById(id);
        return materialNewDO;
    }

    @Override
    public boolean updateMaterialNewById(Long id, CrabVideoSandboxStatusEnum status) {
        MaterialNewDO materialNewDO = findMaterialNewById(id);
        materialNewDO.setStatus(status);
        return updateById(materialNewDO);
    }

}
