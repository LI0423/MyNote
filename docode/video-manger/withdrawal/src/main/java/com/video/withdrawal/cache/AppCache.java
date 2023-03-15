package com.video.withdrawal.cache;

import com.video.entity.AppDO;

public interface AppCache {

    AppDO getAppByPkg(String pkg);

    void setAppByPkgExpire(String pkg, AppDO appDO);

    AppDO getAppById(Long id);

    void setAppByIdExpire(Long id, AppDO appDO);
}
