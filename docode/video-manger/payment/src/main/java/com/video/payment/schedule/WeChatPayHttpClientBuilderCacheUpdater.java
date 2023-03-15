package com.video.payment.schedule;

import com.google.common.cache.Cache;
import com.video.entity.AppDO;
import com.video.payment.constant.BeanNameConstants;
import com.video.payment.constant.LocalCacheKey;
import com.video.payment.mapper.AppMapper;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class WeChatPayHttpClientBuilderCacheUpdater {

    @Qualifier(BeanNameConstants.WE_CHAT_PAY_HTTP_CLIENT_BUILDER_CACHE)
    @Autowired
    private Cache<String, WechatPayHttpClientBuilder> weChatPayHttpClientBuilderCache;

    @Autowired
    private AppMapper appMapper;

    /**
     * 每个小时更新一次weChatPayHttpClientBuilderCache
     */
    @Scheduled(cron = "00 00 */1 * * *")
    public void updateWeChatPayHttpClientBuilderCache() {
        List<AppDO> appDOS = appMapper.selectAllApp();

        for (AppDO app : appDOS) {
            String mchId = app.getWeChatMchid();
            String weChatMchPrivateKey = app.getWeChatMchPrivateKey();
            String weChatApiV3Key = app.getWeChatApiV3Key();
            String weChatMchCertSerialNo = app.getWeChatMchCertSerialNo();
            if (StringUtils.isBlank(mchId)
                    || StringUtils.isBlank(weChatMchPrivateKey)
                    || StringUtils.isBlank(weChatApiV3Key)
                    || StringUtils.isBlank(weChatMchCertSerialNo)) {
                log.info(
                        String.format("app:[%s], mchId[%s]、we_chat_mch_private_key[%s]、" +
                                        "weChatApiV3Key[%s]、weChatMchCertSerialNo[%s],数据查询不到",
                                app.getName(), mchId, weChatMchPrivateKey, weChatApiV3Key, weChatMchCertSerialNo));
                continue;
            }
            // 加载商户私钥（privateKey：私钥字符串）
            PrivateKey merchantPrivateKey = PemUtil
                    .loadPrivateKey(new ByteArrayInputStream(weChatMchPrivateKey.getBytes(StandardCharsets.UTF_8)));

            // 加载平台证书（mchId：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3密钥）
            AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                    new WechatPay2Credentials(mchId, new PrivateKeySigner(weChatMchCertSerialNo, merchantPrivateKey)),
                    weChatApiV3Key.getBytes(StandardCharsets.UTF_8));

            // 初始化httpClient
            WechatPayHttpClientBuilder wechatPayHttpClientBuilder = WechatPayHttpClientBuilder.create()
                    .withMerchant(mchId, weChatMchCertSerialNo, merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier));

            if (Objects.isNull(wechatPayHttpClientBuilder)) {
                log.info("Build we chat pay http client fail. app id[{}], app name:[{}]",
                        app.getId(), app.getName());
                continue;
            }

            String key = String.format(LocalCacheKey.WE_CHAT_PAY_HTTP_CLIENT_BUILDER_BY_KEY,
                    mchId, weChatMchPrivateKey, weChatApiV3Key, weChatMchCertSerialNo);

            weChatPayHttpClientBuilderCache.put(key, wechatPayHttpClientBuilder);
            log.info("app[{}], app id[{}] we chat pay http client 构建成功.", app.getName(), app.getId());
        }
    }
}
