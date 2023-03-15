package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.CrabAddressStatusEnum;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.CrabAddressDTO;
import com.video.manager.domain.dto.CrabAddressQueryDTO;
import com.video.manager.domain.dto.GetStatusFromMaterialFeedDTO;
import com.video.manager.domain.entity.CrabAddressDO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.CrabAddressMapper;
import com.video.manager.service.CrabAddressService;
import com.video.manager.util.HttpUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: video-manger
 * @description: crab address service impl
 * @author: laojiang
 * @create: 2020-09-07 14:17
 **/
@Service(value = "crabAddressService")
public class CrabAddressServiceImpl extends ServiceImpl<CrabAddressMapper, CrabAddressDO> implements CrabAddressService {
    @Value("${url.materialFeed}")
    private String materialFeedUrl;
    @Autowired
    private ObjectMapper objectMapper;

    private CrabAddressDTO requestGoServer(CrabAddressDTO crabAddressDTO) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("crawl_url", crabAddressDTO.getUrl());
            body.put("channel", crabAddressDTO.getSource());
            body.put("auto","aaa");
            String result = HttpUtil.postResponseWithParamsInMap(materialFeedUrl, body);
            if (result == null) {
                throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
            } else {
                GetStatusFromMaterialFeedDTO statusFormMaterialFeedDTO = objectMapper.readValue(result, GetStatusFromMaterialFeedDTO.class);
                if (Constants.SUCCESS.equals(statusFormMaterialFeedDTO.getResult())) {
                    crabAddressDTO.setStatus(CrabAddressStatusEnum.CRAB_COMPLETE);
                } else {
                    crabAddressDTO.setStatus(CrabAddressStatusEnum.INPUT_FAIL);
                }
            }
        } catch (Exception e) {
            log.error("access token GET error", e);
            return null;
        }
        return crabAddressDTO;
    }

    @Override
    public Boolean insert(CrabAddressDTO crabAddressDTO) {
        CrabAddressDTO result = requestGoServer(crabAddressDTO);
        CrabAddressDO crabAddressDO = new CrabAddressDO();
        BeanUtils.copyProperties(result, crabAddressDO);
        return save(crabAddressDO);
    }

    @Override
    public Boolean update(CrabAddressDTO crabAddressDTO, Long id) {
        CrabAddressDTO result = requestGoServer(crabAddressDTO);
        CrabAddressDO crabAddressDO = new CrabAddressDO();
        BeanUtils.copyProperties(result, crabAddressDO);
        crabAddressDO.setId(id);
        return updateById(crabAddressDO);
    }

    @Override
    public Boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    public PageResult<List<CrabAddressDTO>> find(PageQuery<CrabAddressQueryDTO> pageQuery) {

        CrabAddressQueryDTO crabAddressQueryDTO = pageQuery.getQuery();

        QueryWrapper queryWrapper = new QueryWrapper();

        if (crabAddressQueryDTO != null && crabAddressQueryDTO.getSource() != null) {
            queryWrapper.eq("source", crabAddressQueryDTO.getSource());
        }

        if (crabAddressQueryDTO != null && crabAddressQueryDTO.getUrl() != null) {
            queryWrapper.like("url", crabAddressQueryDTO.getUrl());
        }

        if (crabAddressQueryDTO != null && crabAddressQueryDTO.getStartDate() != null && crabAddressQueryDTO.getEndDate() != null) {
            queryWrapper.between("create_time",
                    crabAddressQueryDTO.getStartDate(),
                    crabAddressQueryDTO.getEndDate());
        }

        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());

        IPage<CrabAddressDO> crabAddressDOIPage = page(page, queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(crabAddressDOIPage.getTotal());

        List<CrabAddressDTO> crabAddressDTOList = Optional
                .ofNullable(crabAddressDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(
                        crabAddressDO -> {
                            CrabAddressDTO crabAddressDTO = new CrabAddressDTO();
                            BeanUtils.copyProperties(crabAddressDO, crabAddressDTO);
                            return crabAddressDTO;
                        }
                )
                .collect(Collectors.toList());

        pageResult.setLists(crabAddressDTOList);

        return pageResult;
    }
}
