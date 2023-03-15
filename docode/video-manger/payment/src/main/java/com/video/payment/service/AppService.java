package com.video.payment.service;

import com.video.entity.AppDO;
import com.video.entity.AppPaymentConfigDO;
import com.video.entity.AppPaymentWeChatConfigDO;
import com.video.entity.PayOrderChannelEnum;
import com.video.payment.domain.dto.app.AppDTO;
import com.video.payment.domain.dto.app.AppPaymentConfigDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author laojjiang
 */
public interface AppService {

    /**
     * 根据pkg得到app
     * @param pkg
     * @return
     */
    AppDTO find(String pkg);

    /**
     * 根据id得到app
     * @param id
     * @return
     */
    AppDTO find(Long id);

    /**
     * 根据第三方appid获取app信息，支付宝用
     * @param thirdAppId
     * @return
     */
    AppDTO findByAliAppId(String thirdAppId);

    /**
     * 根据第三方appId和商户号获取app信息，微信用
     * @param thirdAppId
     * @param weChatMchid
     * @return
     */
    AppDTO findByWeChatMchid(String thirdAppId, String weChatMchid);

    /**
     * 获取app payment config
     * @param appId app id
     * @return app payment config
     */
    AppPaymentConfigDTO findAppPaymentConfig(Long appId);

    /**
     * 根据ali pay app id获取app payment config
     * @param aliPayAppId 支付宝app id
     * @return app payment config
     */
    AppPaymentConfigDTO findAppPaymentConfigByAliPayAppId(String aliPayAppId);

    /**
     * 获取所有的apiV3Key
     * @return
     */
    List<String> allApiV3Key();

    /**
     * 随机获取一个商户信息
     * @param pkg
     * @return
     */
    AppDTO findRand(String pkg, PayOrderChannelEnum merchantType, Integer mappingType);

    /**
     * 获取某个渠道，某个支付类型(支付,提现)的app信息列表
     * @param pkg
     * @param merchantType
     * @param mappingType
     * @return
     */
    List<AppDTO> findPaymentList(String pkg, PayOrderChannelEnum merchantType, Integer mappingType);

    List<String> allWeChatApiV3Key();

    static AppDTO convertTo(AppDO appDO, AppPaymentConfigDO appPaymentConfigDO) {

        AppDTO appDTO = new AppDTO();

        if (Objects.isNull(appDO) || Objects.isNull(appPaymentConfigDO)) {
            return null;
        }

        BeanUtils.copyProperties(appDO, appDTO);

        BeanUtils.copyProperties(appPaymentConfigDO, appDTO);

        return appDTO;
    }

    static AppDTO convertTo(AppDO appDO, AppPaymentWeChatConfigDO appPaymentWeChatConfigDO) {

        AppDTO appDTO = new AppDTO();

        if (Objects.isNull(appDO) || Objects.isNull(appPaymentWeChatConfigDO)) {
            return null;
        }

        BeanUtils.copyProperties(appDO, appDTO);

        BeanUtils.copyProperties(appPaymentWeChatConfigDO, appDTO);

        return appDTO;
    }
}
