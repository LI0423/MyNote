package com.video.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.video.entity.AppDO;
import com.video.entity.AppVersionConfigDO;
import com.video.manager.domain.common.Constants;
import com.video.manager.domain.common.PageQuery;
import com.video.manager.domain.common.PageResult;
import com.video.manager.domain.common.ResponseResult;
import com.video.manager.domain.dto.*;
import com.video.manager.domain.entity.ManagerDO;
import com.video.manager.exception.BusinessException;
import com.video.manager.exception.ErrorCodeEnum;
import com.video.manager.mapper.AppMapper;
import com.video.manager.mapper.AppVersionConfigMapper;
import com.video.manager.mapper.SspMediationMapper;
import com.video.manager.service.AppService;
import com.video.manager.service.AppSidService;
import com.video.manager.service.UserService;
import com.video.manager.shiro.MyShiroCasRealm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.Subject.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: video-manger
 * @description: app
 * @author: laojiang
 * @create: 2020-09-03 16:57
 **/
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, AppDO> implements AppService {

    public static final String NAME = "name";

    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Autowired
    private AppSidService appSidService;
    @Autowired
    ManagerServiceImpl managerService;
    @Autowired
    private AppVersionConfigMapper appVersionConfigMapper;
    @Autowired
    private SspMediationMapper sspMediationMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    private static final String STRING_ABLE_CREATE_USER_BY_APPID_VERSIONCODE_KEY_PREFIX = "spredis:ableCreateUser:appId:%s:versionCode:%s";


    @Override
    public UploadDTO upload(MultipartFile fileByte) {
        String buffStr = "";
        try {
            buffStr = Base64.encodeBase64String(fileByte.getBytes());
        } catch (IOException e) {
            logger.error("上传失败! error is : " + e);
        }
        return UploadDTO.builder().weChatApiCert(buffStr).build();
    }


    @Override
    public Boolean insert(AppDTO appDTO) {

        AppDO appDO = new AppDO();

        List<String> sidList = appSidService.findSid(appDTO.getPkg());
        if (sidList != null && sidList.size() != 0) {
            String sids = String.join(",", sidList);
            appDO.setSids(sids);
        }

        BeanUtils.copyProperties(appDTO, appDO);

        appDO.setNeedAnonymousUser(true);
        List<String> apps = new ArrayList<>(managerService.listApp());
        if (!apps.contains("all")) {
            apps.add(appDTO.getPkg());
            managerService.update(String.join(",", apps));
        }
        return save(appDO);
    }

    @Override
    public Boolean update(AppDTO appDTO, Long id) {
        if (findOne(id) == null) {
            return false;
        }
        AppDO appDO = new AppDO();
        BeanUtils.copyProperties(appDTO, appDO);
        appDO.setId(id);

        List<String> sidList = appSidService.findSid(appDTO.getPkg());
        if (sidList != null && sidList.size() != 0) {
            String sids = String.join(",", sidList);
            appDO.setSids(sids);
        }
        return updateById(appDO);
    }

    @Override
    public Boolean delete(Long id) {
        AppDTO appDTO = findOne(id);
        if (appDTO != null) {
            return removeById(id);
        }
        return false;
    }

    @Override
    public List<AppIdAndNameDTO> findIdAndName() {
        List<String> apps = managerService.listApp();

        QueryWrapper query = new QueryWrapper();
        query.select("id", "name", "pkg");
        if (!apps.contains("all")) {
            query.in("pkg", apps);
        }
        query.orderByAsc("CONVERT( name USING gbk)");

        List<com.video.entity.AppDO> appDOList = list(query);

        List<AppIdAndNameDTO> appIdAndNameDTOList = Optional
                .ofNullable(appDOList)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(
                        appDO -> {
                            AppIdAndNameDTO appIdAndNameDTO = new AppIdAndNameDTO();
                            BeanUtils.copyProperties(appDO, appIdAndNameDTO);
                            return appIdAndNameDTO;
                        }
                )
                .collect(Collectors.toList());


        return appIdAndNameDTOList;
    }

    @Override
    public PageResult<List<AppDTO>> find(PageQuery<AppQueryDTO> pageQuery) {
        AppDO query = new AppDO();
        BeanUtils.copyProperties(pageQuery.getQuery(), query);
        QueryWrapper queryWrapper = new QueryWrapper(query);

        List<String> apps = managerService.listApp();
        if (!apps.contains("all")) {
            if (StringUtils.isEmpty(query.getPkg())) {
                queryWrapper.in("pkg", apps);
            } else {
                if (!apps.contains(query.getPkg())) {
                    throw new BusinessException(ErrorCodeEnum.APP_FORBIDDEN);
                }
            }
        }

        String orderBy = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageQuery.getOrderBy());
        if (NAME.equals(pageQuery.getOrderBy())) {
            orderBy = "CONVERT(" + orderBy + " USING gbk)";
        }
        if (Constants.ASCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByAsc(orderBy);
        }

        if (Constants.DESCENDING.equals(pageQuery.getSequence())) {
            queryWrapper.orderByDesc(orderBy);
        }

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());

        IPage<AppDO> appDOIPage = page(page, queryWrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(appDOIPage.getTotal());

        List<AppDTO> appDTOList = Optional
                .ofNullable(appDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(
                        appDO -> {
                            AppDTO appDTO = new AppDTO();
                            BeanUtils.copyProperties(appDO, appDTO);
                            return appDTO;
                        }
                )
                .collect(Collectors.toList());

        pageResult.setLists(appDTOList);

        return pageResult;
    }

    @Override
    public AppDTO findOne(Long id) {
        AppDO appDO = getById(id);

        List<String> apps = managerService.listApp();
        if (!apps.contains("all") && !apps.contains(appDO.getPkg())) {
            throw new BusinessException(ErrorCodeEnum.APP_FORBIDDEN);
        }
        AppDTO appDTO = new AppDTO();
        BeanUtils.copyProperties(appDO, appDTO);
        return appDTO;
    }


    @Override
    public List<Long> listAppIds() {
        List<String> apps = managerService.listApp();

        QueryWrapper queryWrapper = new QueryWrapper();
        if (!apps.contains("all")) {
            queryWrapper.in("pkg", apps);
        }

        List<com.video.entity.AppDO> appDOList = list(queryWrapper);

        List<Long> ids = new ArrayList<>();
        appDOList.forEach(appDO -> ids.add(appDO.getId()));
        return ids;
    }

    @Override
    public Boolean addAppVersionConfig(AppVersionConfigDTO appVersionConfigDTO) {
        AppVersionConfigDO appVersionConfigDO = appVersionConfigMapper.selectByAppIdAndVersion(appVersionConfigDTO.getAppId(), appVersionConfigDTO.getVersionCode());
        if (!ObjectUtils.isEmpty(appVersionConfigDO)) {
            throw new BusinessException(ErrorCodeEnum.VIDEO_REPEAT);
        }

        AppVersionConfigDO data = new AppVersionConfigDO();
        BeanUtils.copyProperties(appVersionConfigDTO, data);
        appVersionConfigMapper.insert(data);

        //缓存删除
        String key = String.format(STRING_ABLE_CREATE_USER_BY_APPID_VERSIONCODE_KEY_PREFIX, data.getAppId(), data.getVersionCode());
        redisTemplate.delete(key);

        return true;
    }

    @Override
    public Boolean updateAppVersionConfig(AppVersionConfigDTO appVersionConfigDTO) {
        AppVersionConfigDO appVersionConfigDO = appVersionConfigMapper.selectById(appVersionConfigDTO.getId());
        if (ObjectUtils.isEmpty(appVersionConfigDO)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }
        AppVersionConfigDO data = new AppVersionConfigDO();
        data.setId(appVersionConfigDTO.getId());
        data.setCreateAnonymousUser(appVersionConfigDTO.getCreateAnonymousUser());

        appVersionConfigMapper.updateById(data);
        //缓存删除
        String key = String.format(STRING_ABLE_CREATE_USER_BY_APPID_VERSIONCODE_KEY_PREFIX, appVersionConfigDO.getAppId(), appVersionConfigDO.getVersionCode());
        redisTemplate.delete(key);

        return true;
    }

    @Override
    public Boolean deleteAppVersionConfig(Long id) {
        AppVersionConfigDO appVersionConfigDO = appVersionConfigMapper.selectById(id);
        if (ObjectUtils.isEmpty(appVersionConfigDO)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }
        //缓存删除
        String key = String.format(STRING_ABLE_CREATE_USER_BY_APPID_VERSIONCODE_KEY_PREFIX, appVersionConfigDO.getAppId(), appVersionConfigDO.getVersionCode());
        redisTemplate.delete(key);

        appVersionConfigMapper.deleteById(id);
        return true;
    }

    @Override
    public List<AppVersionConfigDTO> findAppVersionConfigList(Long appId) {
        List<AppVersionConfigDO> data = appVersionConfigMapper.selectByAppId(appId);
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MediationDTO> getAidList(String pkg) {
        return sspMediationMapper.getAidList(pkg);
    }

    public AppVersionConfigDTO toDTO(AppVersionConfigDO appVersionConfigDO) {
        if (ObjectUtils.isEmpty(appVersionConfigDO)) {
            return null;
        }

        AppVersionConfigDTO data = new AppVersionConfigDTO();
        BeanUtils.copyProperties(appVersionConfigDO, data);
        return data;
    }

    @Override
    @Scheduled(cron = "0 0/30 *  * * ? ")
    public void updateSid() {
        List<AppDO> appDOList = this.list(new QueryWrapper<AppDO>().select("id", "last_modify_by", "last_modify_time"));

        for (AppDO appDO : appDOList) {
            String sids = appSidService.findSid(appDO.getPkg())
                    .stream()
                    .collect(Collectors.joining(","));
            if (sids != null && sids.length() > 10) {
                update(new UpdateWrapper<AppDO>().eq("id", appDO.getId()).set("sids", sids));
            }
        }
    }
}
