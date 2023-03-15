package com.video.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.video.entity.AppDO;
import com.video.entity.AppPaymentConfigDO;
import com.video.entity.AppPaymentConfigMappingDO;
import com.video.entity.AppPaymentWeChatConfigDO;
import com.video.entity.PayOrderChannelEnum;
import com.video.payment.cache.AppCache;
import com.video.payment.cache.AppPaymentConfigCache;
import com.video.payment.domain.dto.app.AppDTO;
import com.video.payment.domain.dto.app.AppPaymentConfigDTO;
import com.video.payment.mapper.AppMapper;
import com.video.payment.mapper.AppPaymentConfigMapper;
import com.video.payment.mapper.AppPaymentConfigMappingMapper;
import com.video.payment.mapper.AppPaymentWeChatConfigMapper;
import com.video.payment.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppPaymentConfigMapper appPaymentConfigMapper;

    @Autowired
    private AppCache appCache;

    @Autowired
    private AppPaymentConfigCache appPaymentConfigCache;

    @Autowired
    private AppPaymentWeChatConfigMapper appPaymentWeChatConfigMapper;

    @Autowired
    private AppPaymentConfigMappingMapper appPaymentConfigMappingMapper;

    private AppDO getAppDoByPkg(String pkg) {
        // 先尝试从缓存获取appDO
        AppDO appDO = appCache.getAppByPkg(pkg);
        //
        if (appDO == null) {
            AppDO condition = new AppDO();
            condition.setPkg(pkg);
            QueryWrapper<AppDO> queryWrapper = new QueryWrapper<>(condition);
            // selectList查询失败后也会返回空list
            List<AppDO> appDOList = appMapper.selectList(queryWrapper);
            if (appDOList.isEmpty()) {
                appCache.setAppByPkgExpire(pkg, null);
                log.warn("AppDO object cannot be obtained through pkg, pkg:{}", pkg);
                return null;
            }
            if (appDOList.size() > 1) {
                log.warn("More than one AppDO object obtained through pkg, pkg:{}", pkg);
            }
            appDO = appDOList.get(0);
            // 从数据库中读到数据后写入缓存
            appCache.setAppByPkgExpire(pkg, appDO);
        } else {
            // 获取到了redis中缓存的非法值
            if (appDO.getPkg() == null) {
                log.warn("AppDO object is illegal, pkg:{}", pkg);
                return null;
            }
        }
        return appDO;
    }

    private AppDO getAppDoById(Long id) {
        // 先尝试从缓存获取appDO
        AppDO appDO = appCache.getAppById(id);
        //
        if (appDO == null) {
            AppDO condition = new AppDO();
            condition.setId(id);
            appDO = appMapper.selectById(condition);
            if (appDO == null) {
                return null;
            }
            // 从数据库中读到数据后写入缓存
            appCache.setAppByIdExpire(id, appDO);
        } else if (appDO.getId() == null) {
            log.warn("AppDO object is illegal, id:{}", id);
            return null;
        }
        return appDO;
    }

    private AppPaymentConfigDO getAppPaymentConfigDoByAppId(Long id) {
        // 先尝试从缓存获取appDO
        AppPaymentConfigDO appPaymentConfigDO = appPaymentConfigCache.getById(id);
        //
        if (appPaymentConfigDO == null) {
            appPaymentConfigDO = appPaymentConfigMapper.selectById(id);
            if (appPaymentConfigDO == null) {
                return null;
            }
            // 从数据库中读到数据后写入缓存
            appPaymentConfigCache.setByIdExpire(id, appPaymentConfigDO);
        } else if (appPaymentConfigDO.getAppId() == null) {
            log.warn("AppPaymentConfigDO object is illegal, id:{}", id);
            return null;
        }
        return appPaymentConfigDO;
    }

    private AppPaymentConfigDO getAppPaymentConfigDoByAliPayAppId(String aliPayAppId) {
        // 先尝试从缓存获取appDO
        AppPaymentConfigDO appPaymentConfigDO = appPaymentConfigCache.getByAliPayAppId(aliPayAppId);
        //
        if (appPaymentConfigDO == null) {
            appPaymentConfigDO = appPaymentConfigMapper.selectByAliPayAppId(aliPayAppId);
            if (appPaymentConfigDO == null) {
                return null;
            }
            // 从数据库中读到数据后写入缓存
            appPaymentConfigCache.setByAliPayAppIdExpire(aliPayAppId, appPaymentConfigDO);
        } else if (appPaymentConfigDO.getAppId() == null) {
            log.warn("AppPaymentConfigDO object is illegal, ali pay id:{}", aliPayAppId);
            return null;
        }
        return appPaymentConfigDO;
    }

    @Override
    public AppDTO find(String pkg) {
        AppDO appDO = getAppDoByPkg(pkg);
        if (appDO == null) {
            return null;
        }
        AppDTO appDTO = new AppDTO();
        BeanUtils.copyProperties(appDO, appDTO);
        return appDTO;
    }

    @Override
    public AppDTO find(Long id) {
        AppDO appDO = getAppDoById(id);
        if (appDO == null) {
            return null;
        }
        AppDTO appDTO = new AppDTO();
        BeanUtils.copyProperties(appDO, appDTO);
        return appDTO;
    }

    @Override
    public AppDTO findByAliAppId(String thirdAppId) {

        AppDTO appDTO = new AppDTO();

        List<AppPaymentConfigDO> appPaymentConfigDOS = appPaymentConfigMapper
                .selectList(new QueryWrapper<AppPaymentConfigDO>().eq("ali_pay_app_id", thirdAppId));
        if (CollectionUtils.isEmpty(appPaymentConfigDOS)) {
            return null;
        }

        AppDTO appDTO2 = find(appPaymentConfigDOS.get(0).getAppId());
        BeanUtils.copyProperties(appDTO2, appDTO);
        BeanUtils.copyProperties(appPaymentConfigDOS.get(0), appDTO);
        appDTO.setId(appDTO2.getId());

        return appDTO;
    }

    @Override
    public AppDTO findByWeChatMchid(String thirdAppId, String weChatMchid) {
        AppDTO appDTO = new AppDTO();
        List<AppPaymentWeChatConfigDO> appPaymentWeChatConfigDOS = appPaymentWeChatConfigMapper
                .selectList(new QueryWrapper<AppPaymentWeChatConfigDO>()
                        .eq("we_chat_app_id", thirdAppId)
                        .eq("we_chat_mchid", weChatMchid));
        if (CollectionUtils.isEmpty(appPaymentWeChatConfigDOS)) {
            return null;
        }

        AppDTO appDTO2 = find(appPaymentWeChatConfigDOS.get(0).getAppId());
        BeanUtils.copyProperties(appDTO2, appDTO);
        BeanUtils.copyProperties(appPaymentWeChatConfigDOS.get(0), appDTO);
        appDTO.setId(appDTO2.getId());
        return appDTO;
    }


    @Override
    public AppPaymentConfigDTO findAppPaymentConfig(Long appId) {
        AppPaymentConfigDO appPaymentConfigDO = getAppPaymentConfigDoByAppId(appId);
        if (Objects.isNull(appPaymentConfigDO)) {
            return null;
        }
        AppPaymentConfigDTO appPaymentConfigDTO = new AppPaymentConfigDTO();
        BeanUtils.copyProperties(appPaymentConfigDO, appPaymentConfigDTO);
        return appPaymentConfigDTO;
    }

    @Override
    public AppPaymentConfigDTO findAppPaymentConfigByAliPayAppId(String aliPayAppId) {
        AppPaymentConfigDO appPaymentConfigDO = getAppPaymentConfigDoByAliPayAppId(aliPayAppId);
        if (Objects.isNull(appPaymentConfigDO)) {
            return null;
        }
        AppPaymentConfigDTO appPaymentConfigDTO = new AppPaymentConfigDTO();
        BeanUtils.copyProperties(appPaymentConfigDO, appPaymentConfigDTO);
        return appPaymentConfigDTO;
    }

    @Override
    public List<String> allApiV3Key() {
        List<String> apiV3Keys = appCache.getAllApiV3Key();
        if (apiV3Keys == null) {
            apiV3Keys = appMapper.selectAllApiV3Key();
            appCache.setAllApiV3Key(apiV3Keys);
        }
        return apiV3Keys;
    }

    @Override
    public AppDTO findRand(String pkg, PayOrderChannelEnum merchantType, Integer mappingType) {
        AppDO appDO = getAppDoByPkg(pkg);
        if (appDO == null) {
            return null;
        }
        AppDTO appDTO = new AppDTO();
        BeanUtils.copyProperties(appDO, appDTO);
        List<AppPaymentConfigMappingDO> appPaymentConfigMappingDOS = appPaymentConfigMappingMapper
                .selectList(new QueryWrapper<AppPaymentConfigMappingDO>()
                        .eq("merchant_type", merchantType.getCode())
                        .eq("mapping_type",mappingType)
                        .eq("status", 0)
                        .eq("app_id", appDO.getId()));

        if (CollectionUtils.isEmpty(appPaymentConfigMappingDOS)) {
            return null;
        }

        Collections.shuffle(appPaymentConfigMappingDOS);
        AppPaymentConfigMappingDO appPaymentConfigMappingDO = appPaymentConfigMappingDOS.get(0);

        // 查出当前app所有的微信商户信息
        if (PayOrderChannelEnum.WE_CHAT == merchantType) {
            List<AppPaymentWeChatConfigDO> appPaymentWeChatConfigDOS = appPaymentWeChatConfigMapper.selectList(
                    new QueryWrapper<AppPaymentWeChatConfigDO>()
                            .eq("id", appPaymentConfigMappingDO.getMerchantId())
                            .eq("frozen_status", 0)
                            .eq("deleted", 1));
            if (CollectionUtils.isEmpty(appPaymentConfigMappingDOS)) {
                return null;
            }
            BeanUtils.copyProperties(appPaymentWeChatConfigDOS.get(0), appDTO);
        } else if (PayOrderChannelEnum.ALI_PAY == merchantType) {
            List<AppPaymentConfigDO> appPaymentConfigDOS = appPaymentConfigMapper.selectList(
                    new QueryWrapper<AppPaymentConfigDO>()
                            .eq("id", appPaymentConfigMappingDO.getMerchantId())
                            .eq("frozen_status", 0)
                            .eq("deleted", 1));
            if (CollectionUtils.isEmpty(appPaymentConfigDOS)) {
                return null;
            }
            BeanUtils.copyProperties(appPaymentConfigDOS.get(0), appDTO);
        }

        appDTO.setId(appDO.getId());
        return appDTO;
    }

    @Override
    public List<AppDTO> findPaymentList(String pkg, PayOrderChannelEnum merchantType, Integer mappingType) {
        AppDO appDO = getAppDoByPkg(pkg);
        if (appDO == null) {
            return Collections.EMPTY_LIST;
        }

        if (PayOrderChannelEnum.WE_CHAT == merchantType) {
            List<AppPaymentWeChatConfigDO> appPaymentWeChatConfigDOS =
                    appPaymentWeChatConfigMapper.selectByFrozenStatusAndDeletedAndSubSelect(
                            0, 1, merchantType.getCode(), mappingType, 0, appDO.getId());

            if (CollectionUtils.isEmpty(appPaymentWeChatConfigDOS)) {
                return Collections.EMPTY_LIST;
            }

            return appPaymentWeChatConfigDOS
                    .stream()
                    .map(appPaymentWeChatConfigDO -> AppService.convertTo(appDO, appPaymentWeChatConfigDO))
                    .collect(Collectors.toList());
        } else if (PayOrderChannelEnum.ALI_PAY == merchantType) {
            List<AppPaymentConfigDO> appPaymentConfigDOS =
                    appPaymentConfigMapper.selectByFrozenStatusAndDeletedAndSubSelect(
                            0, 1, merchantType.getCode(), mappingType, 0, appDO.getId());

            if (CollectionUtils.isEmpty(appPaymentConfigDOS)) {
                return Collections.EMPTY_LIST;
            }

            return appPaymentConfigDOS
                    .stream()
                    .map(appPaymentConfigDO -> AppService.convertTo(appDO, appPaymentConfigDO))
                    .collect(Collectors.toList());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<String> allWeChatApiV3Key() {
        List<String> apiV3Keys = appCache.getAllWeChatApiV3Key();
        if (apiV3Keys == null) {
            apiV3Keys = appPaymentWeChatConfigMapper.selectAllApiV3Key();
            appCache.setAllWeChatApiV3Key(apiV3Keys);
        }
        return apiV3Keys;
    }

}
