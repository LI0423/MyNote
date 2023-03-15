package com.video.payment.cache;

import com.video.entity.AppDO;

import java.util.List;

public interface AppCache {

    AppDO getAppByPkg(String pkg);

    void setAppByPkgExpire(String pkg, AppDO appDO);

    AppDO getAppById(Long id);

    void setAppByIdExpire(Long id, AppDO appDO);

    AppDO getThirdAppById(String thirdAppId);

    void setAppByThirdAppIdExpire(String thirdAppId, AppDO appDO);

    List<String> getAllApiV3Key();

    void setAllApiV3Key(List<String> apiV3Keys);

    List<String> getAllWeChatApiV3Key();

    void setAllWeChatApiV3Key(List<String> apiV3Keys);
}
