package com.video.withdrawal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.video.entity.AppDO;
import com.video.withdrawal.cache.AppCache;
import com.video.withdrawal.domain.dto.app.AppDTO;
import com.video.withdrawal.mapper.AppMapper;
import com.video.withdrawal.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppCache appCache;

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

}
