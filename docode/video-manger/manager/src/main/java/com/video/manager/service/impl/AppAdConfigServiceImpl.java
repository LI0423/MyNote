package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.video.entity.*;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.dto.*;
import com.video.manager.domain.entity.CityDO;
import com.video.manager.domain.entity.ProvinceDO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.*;
import com.video.manager.service.AppAdConfigService;
import com.video.manager.service.AppService;
import com.video.manager.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: mango
 * @description: AppAdConfigService
 * @author: laojiang
 * @create: 2020-10-21 19:24
 **/
@Service
@Slf4j
public class AppAdConfigServiceImpl implements AppAdConfigService {

    private AppAdConfigMapper appAdConfigMapper;
    private AppAdConfigDetailMapper appAdConfigDetailMapper;
    private AppAdConfigAppVnMapper appAdConfigAppVnMapper;
    private AppAdConfigSidMapper appAdConfigSidMapper;
    private ProvinceMapper provinceMapper;
    private CityMapper cityMapper;
    private AppService appService;

    @Autowired
    ManagerService managerService;

    @Autowired
    public AppAdConfigServiceImpl(AppAdConfigMapper appAdConfigMapper,
                                  AppAdConfigDetailMapper appAdConfigDetailMapper,
                                  AppAdConfigAppVnMapper appAdConfigAppVnMapper,
                                  AppAdConfigSidMapper appAdConfigSidMapper,
                                  ProvinceMapper provinceMapper,
                                  CityMapper cityMapper,
                                  AppService appService) {
        this.appAdConfigMapper = appAdConfigMapper;
        this.appAdConfigDetailMapper = appAdConfigDetailMapper;
        this.appAdConfigAppVnMapper = appAdConfigAppVnMapper;
        this.appAdConfigSidMapper = appAdConfigSidMapper;
        this.provinceMapper = provinceMapper;
        this.cityMapper = cityMapper;
        this.appService = appService;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(CreateAppAdConfigDTO createAppAdConfigDTO) {

        //判断SID是否配置过了
        for (String sid : createAppAdConfigDTO.getSids()) {
            for (String appVn : createAppAdConfigDTO.getAppVns()) {
                Long count = appAdConfigMapper.findAppVnAndSid(createAppAdConfigDTO.getAppId()
                        , appVn, sid,
                        createAppAdConfigDTO.getProvinceCode(),
                        createAppAdConfigDTO.getCityCode());
                if (count > 0) {
                    throw new BusinessException(ErrorCodeEnum.AD_CONFIG_DUPLICATE);
                }
            }
        }

        //生成appAdConfig
        AppAdConfigDO appAdConfigDO = getAppAdConfigDO(createAppAdConfigDTO);
        //如果没有包名，怎查询包名
        if (createAppAdConfigDTO.getAppId() != null) {
            AppDTO appDTO = appService.findOne(createAppAdConfigDTO.getAppId());
            appAdConfigDO.setPkg(appDTO.getPkg());
            appAdConfigDO.setName(appDTO.getName());
        }
        appAdConfigDO.setStatus(AppAdConfigStatusEnum.ONLINE);

        int result = appAdConfigMapper.insert(appAdConfigDO);
        //生成detail
        insertDetail(createAppAdConfigDTO, appAdConfigDO);

        //生成版本信息
        insertAppVn(createAppAdConfigDTO, appAdConfigDO);

        //生成广告位信息
        insertSid(createAppAdConfigDTO, appAdConfigDO);

        return result >= 0 ? true : false;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modify(CreateAppAdConfigDTO createAppAdConfigDTO, Long id) {


        //判断SID是否配置过了
        for (String sid : createAppAdConfigDTO.getSids()) {
            for (String appVn : createAppAdConfigDTO.getAppVns()) {
                Long count = appAdConfigMapper.findAppVnAndSidAndId(createAppAdConfigDTO.getAppId()
                        , appVn, sid,
                        createAppAdConfigDTO.getProvinceCode(),
                        createAppAdConfigDTO.getCityCode(),
                        id
                );
                if (count > 0) {
                    throw new BusinessException(ErrorCodeEnum.AD_CONFIG_DUPLICATE);
                }
            }
        }


        AppAdConfigDO appAdConfigDO = getAppAdConfigDO(createAppAdConfigDTO);
        AppAdConfigDO dbAppAdConfig = appAdConfigMapper.selectById(id);
        appAdConfigDO.setStatus(dbAppAdConfig.getStatus());
        appAdConfigDO.setId(id);

        //如果没有包名，则查询包名
        if (createAppAdConfigDTO.getAppId() != null) {
            AppDTO appDTO = appService.findOne(createAppAdConfigDTO.getAppId());
            appAdConfigDO.setPkg(appDTO.getPkg());
            appAdConfigDO.setName(appDTO.getName());
        }

        int result = appAdConfigMapper.updateById(appAdConfigDO);

        appAdConfigDetailMapper.delete(new QueryWrapper<AppAdConfigDetailDO>().eq("app_ad_config_id", id));
        insertDetail(createAppAdConfigDTO, appAdConfigDO);

        appAdConfigAppVnMapper.delete(new QueryWrapper<AppAdConfigAppVnDO>().eq("app_ad_config_id", id));
        insertAppVn(createAppAdConfigDTO, appAdConfigDO);

        appAdConfigSidMapper.delete(new QueryWrapper<AppAdConfigSidDO>().eq("app_ad_config_id", id));
        insertSid(createAppAdConfigDTO, appAdConfigDO);

        return result >= 0 ? true : false;
    }

    @Override
    public Boolean updateStatus(Long id, AppAdConfigStatusEnum status) {


        AppAdConfigDO appAdConfigDO = new AppAdConfigDO();
        appAdConfigDO.setId(id);
        appAdConfigDO.setStatus(status);

        int result = appAdConfigMapper.updateById(appAdConfigDO);

        return result > 0 ? true : false;
    }

    @Override
    public PageResult<List<AppAdConfigListDTO>> findPage(PageQuery<AppAdConfigQueryDTO> pageQuery) {
        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());

        QueryWrapper<AppAdConfigDO> queryWrapper = new QueryWrapper();

        if (pageQuery.getQuery().getAppId() != null) {
            queryWrapper.eq("app_id", Long.valueOf(pageQuery.getQuery().getAppId()));
        }

        if (pageQuery.getQuery().getAppVn() != null && pageQuery.getQuery().getSid() != null) {
            queryWrapper.and(wrapper -> wrapper.inSql("id",
                    "select app_ad_config_id " +
                            "from app_ad_config_app_vn " +
                            "where vn='" + pageQuery.getQuery().getAppVn() + "'")
                    .and(wrapper2 -> wrapper2.inSql("id", "select app_ad_config_id " +
                            "from app_ad_config_sid where sid='"
                            + pageQuery.getQuery().getSid() + "'")));
        } else if (pageQuery.getQuery().getAppVn() != null) {
            queryWrapper.inSql("id",
                    "select app_ad_config_id from app_ad_config_app_vn where vn='"
                            + pageQuery.getQuery().getAppVn() + "'");
        } else if (pageQuery.getQuery().getSid() != null) {
            queryWrapper.inSql("id",
                    "select app_ad_config_id from app_ad_config_sid where sid='"
                            + pageQuery.getQuery().getSid() + "'");
        }

        List<String> apps = managerService.listApp();

        if (pageQuery.getQuery().getPkg() != null) {
            if (apps.contains("all") || apps.contains(pageQuery.getQuery().getPkg())) {
                queryWrapper.eq("pkg", pageQuery.getQuery().getPkg());
            } else {
                throw new BusinessException(ErrorCodeEnum.APP_FORBIDDEN);
            }
        } else {
            if (!apps.contains("all")) {
                queryWrapper.in("pkg", apps);
            }
        }


        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        IPage<AppAdConfigDO> appAdConfigDOIPage = appAdConfigMapper.selectPage(page, queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(appAdConfigDOIPage.getTotal());

        List<AppAdConfigListDTO> appAdConfigDTOList = Optional
                .ofNullable(appAdConfigDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(appAdConfigDO -> {
                    AppAdConfigListDTO appAdConfigListDTO = new AppAdConfigListDTO();
                    BeanUtils.copyProperties(appAdConfigDO, appAdConfigListDTO);
                    //查询版本信息
                    List<AppAdConfigAppVnDO> appAdConfigAppVnDOList = appAdConfigAppVnMapper.selectList(
                            new QueryWrapper<AppAdConfigAppVnDO>()
                                    .eq("app_ad_config_id", appAdConfigDO.getId()));
                    appAdConfigListDTO.setAppVns(appAdConfigAppVnDOList.stream()
                            .map(AppAdConfigAppVnDO::getVn)
                            .collect(Collectors.joining(",")));

                    //查询广告位信息
                    List<AppAdConfigSidDO> appAdConfigSidDOList = appAdConfigSidMapper.selectList(
                            new QueryWrapper<AppAdConfigSidDO>()
                                    .eq("app_ad_config_id", appAdConfigDO.getId()));
                    appAdConfigListDTO.setSids(appAdConfigSidDOList.stream()
                            .map(AppAdConfigSidDO::getSid)
                            .collect(Collectors.joining(",")));
                    return appAdConfigListDTO;
                })
                .collect(Collectors.toList());

        pageResult.setLists(appAdConfigDTOList);

        return pageResult;
    }

    @Override
    public AppAdConfigDTO findOne(Long id) {

        AppAdConfigDO appAdConfigDO = appAdConfigMapper.selectById(id);
        AppAdConfigDTO appAdConfigDTO = new AppAdConfigDTO();
        BeanUtils.copyProperties(appAdConfigDO, appAdConfigDTO);

        //查询详细信息
        List<AppAdConfigDetailDTO> appAdConfigDetailDTOList = new ArrayList<>();
        List<AppAdConfigDetailDO> appAdConfigDetailDOList = appAdConfigDetailMapper
                .selectList(new QueryWrapper<AppAdConfigDetailDO>()
                        .eq("app_ad_config_id", id));
        for (AppAdConfigDetailDO appAdConfigDetailDO : appAdConfigDetailDOList) {
            AppAdConfigDetailDTO appAdConfigDetailDTO = new AppAdConfigDetailDTO();
            BeanUtils.copyProperties(appAdConfigDetailDO, appAdConfigDetailDTO);
            appAdConfigDetailDTO.setKey(appAdConfigDetailDO.getDataKey());
            appAdConfigDetailDTOList.add(appAdConfigDetailDTO);
        }
        appAdConfigDTO.setDetails(appAdConfigDetailDTOList);

        //查询版本信息
        List<AppAdConfigAppVnDO> appAdConfigAppVnDOList = appAdConfigAppVnMapper.selectList(
                new QueryWrapper<AppAdConfigAppVnDO>().eq("app_ad_config_id", id));
        appAdConfigDTO.setAppVns(appAdConfigAppVnDOList.stream()
                .map(AppAdConfigAppVnDO::getVn)
                .collect(Collectors.joining(",")));

        //查询广告位信息
        List<AppAdConfigSidDO> appAdConfigSidDOList = appAdConfigSidMapper.selectList(
                new QueryWrapper<AppAdConfigSidDO>().eq("app_ad_config_id", id));
        appAdConfigDTO.setSids(appAdConfigSidDOList.stream()
                .map(AppAdConfigSidDO::getSid)
                .collect(Collectors.joining(",")));

        return appAdConfigDTO;
    }

    private void insertSid(CreateAppAdConfigDTO createAppAdConfigDTO, AppAdConfigDO appAdConfigDO) {
        for (String sid : createAppAdConfigDTO.getSids()) {
            AppAdConfigSidDO appAdConfigSidDO = new AppAdConfigSidDO();
            appAdConfigSidDO.setAppAdConfigId(appAdConfigDO.getId());
            appAdConfigSidDO.setSid(sid);

            appAdConfigSidMapper.insert(appAdConfigSidDO);
        }
    }

    private void insertDetail(CreateAppAdConfigDTO createAppAdConfigDTO, AppAdConfigDO appAdConfigDO) {
        for (AppAdConfigDetailDTO appAdConfigDetailDTO : createAppAdConfigDTO.getDetails()) {
            AppAdConfigDetailDO appAdConfigDetailDO = new AppAdConfigDetailDO();
            appAdConfigDetailDO.setAppAdConfigId(appAdConfigDO.getId());
            appAdConfigDetailDO.setDataKey(appAdConfigDetailDTO.getKey());
            appAdConfigDetailDO.setValue(appAdConfigDetailDTO.getValue());

            appAdConfigDetailMapper.insert(appAdConfigDetailDO);
        }
    }

    private void insertAppVn(CreateAppAdConfigDTO createAppAdConfigDTO, AppAdConfigDO appAdConfigDO) {
        for (String vn : createAppAdConfigDTO.getAppVns()) {
            AppAdConfigAppVnDO appAdConfigAppVnDO = new AppAdConfigAppVnDO();
            appAdConfigAppVnDO.setAppAdConfigId(appAdConfigDO.getId());
            appAdConfigAppVnDO.setVn(vn);

            appAdConfigAppVnMapper.insert(appAdConfigAppVnDO);
        }
    }

    private AppAdConfigDO getAppAdConfigDO(CreateAppAdConfigDTO createAppAdConfigDTO) {
        AppAdConfigDO appAdConfigDO = new AppAdConfigDO();
        BeanUtils.copyProperties(createAppAdConfigDTO, appAdConfigDO);

        CityDO cityDO = cityMapper.selectOne(new QueryWrapper<CityDO>()
                .eq("code", createAppAdConfigDTO.getCityCode()));
        if (cityDO != null) {
            appAdConfigDO.setCityName(cityDO.getName());
        } else {
            appAdConfigDO.setCityName("all");
        }

        ProvinceDO provinceDO = provinceMapper.selectOne(new QueryWrapper<ProvinceDO>()
                .eq("code", createAppAdConfigDTO.getProvinceCode()));
        if (provinceDO != null) {
            appAdConfigDO.setProvinceName(provinceDO.getName());
        } else {
            appAdConfigDO.setProvinceName("all");
        }

        appAdConfigDO.setProvinceAndCityName(appAdConfigDO
                .getProvinceName()
                .concat("_")
                .concat(appAdConfigDO.getCityName()));
        return appAdConfigDO;
    }
}
