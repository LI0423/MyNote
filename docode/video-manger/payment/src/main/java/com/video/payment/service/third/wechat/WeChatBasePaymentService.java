package com.video.payment.service.third.wechat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.cache.Cache;
import com.video.payment.config.prod.WeChatPaymentProperties;
import com.video.payment.constant.BeanNameConstants;
import com.video.payment.constant.LocalCacheKey;
import com.video.payment.domain.dto.app.AppDTO;
import com.video.payment.domain.dto.third.WeChatErrorMessageDTO;
import com.video.payment.domain.dto.third.WeChatResponseResultDTO;
import com.video.payment.exception.BusinessException;
import com.video.payment.exception.ErrorCodeEnum;
import com.video.payment.service.AppService;
import com.video.payment.service.UserAuthService;
import com.video.payment.service.third.ThirdBasePaymentService;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * 微信支付基础service
 */
@Slf4j
public abstract class WeChatBasePaymentService extends ThirdBasePaymentService {

    @Autowired
    protected WeChatPaymentProperties weChatPaymentProperties;

    @Autowired
    protected AppService appService;

    @Autowired
    protected UserAuthService userAuthService;

    @Qualifier(BeanNameConstants.WE_CHAT_PAY_HTTP_CLIENT_BUILDER_CACHE)
    @Autowired
    private Cache<String, WechatPayHttpClientBuilder> wechatPayHttpClientBuilderCache;

    /**
     * aes key 加解密key长度
     */
    private static final int AES_KEY_LENGTH_BYTE = 32;

    /**
     * 附加数据长度
     */
    private static final int AES_TAG_LENGTH_BIT = 128;

    /**
     * 进行aes解密
     * @param apiV3Key api v3 key
     * @param associatedData 附加数据(可能为空)
     * @param nonce 随机串
     * @param ciphertext 数据密文
     * @return 解密内容
     */
    protected static String aesDecryptToString(String apiV3Key, String associatedData, String nonce, String ciphertext) {
        byte[] apiV3KeyBytes = apiV3Key.getBytes();
        if (apiV3KeyBytes.length != AES_KEY_LENGTH_BYTE) {
            throw new IllegalArgumentException("无效的ApiV3Key，长度必须为32个字节, api v3 key:[" + apiV3Key + "]");
        }
        byte[] associatedDataBytes = StringUtils.isBlank(associatedData) ? null : associatedData.getBytes();
        byte[] nonceBytes = nonce.getBytes();

        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            SecretKeySpec key = new SecretKeySpec(apiV3KeyBytes, "AES");
            GCMParameterSpec spec = new GCMParameterSpec(AES_TAG_LENGTH_BIT, nonceBytes);

            cipher.init(Cipher.DECRYPT_MODE, key, spec);

            if (associatedDataBytes != null) {
                cipher.updateAAD(associatedDataBytes);
            }

            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeyException
                | InvalidAlgorithmParameterException
                | BadPaddingException
                | IllegalBlockSizeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 关闭CloseableHttpClient客户端
     * @param httpClient 可关闭的http客户端
     */
    protected void httpClientClose(CloseableHttpClient httpClient) {
        if (Objects.nonNull(httpClient)) {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("Http client close exception.", e);
            }
        }
    }

    protected <R> R convertFromHttpEntity(HttpEntity entity, Class<R> respClass) throws IOException {
        String bodyAsString = EntityUtils.toString(entity);
        return objectMapper.readValue(bodyAsString, respClass);
    }

    private WechatPayHttpClientBuilder buildWechatPayHttpClientBuilder(String mchId, String weChatMchPrivateKey,
                                                                       String weChatApiV3Key, String weChatMchCertSerialNo) {

        String key = String.format(LocalCacheKey.WE_CHAT_PAY_HTTP_CLIENT_BUILDER_BY_KEY, mchId,
                weChatMchPrivateKey, weChatApiV3Key, weChatMchCertSerialNo);

        WechatPayHttpClientBuilder wechatPayHttpClientBuilder = null;
        try {
            // app信息修改了/缓存还失效了，但更新任务还没执行，也可以主动获取
            wechatPayHttpClientBuilder = wechatPayHttpClientBuilderCache.get(key, () -> {
                if (StringUtils.isBlank(mchId)
                        || StringUtils.isBlank(weChatMchPrivateKey)
                        || StringUtils.isBlank(weChatApiV3Key)
                        || StringUtils.isBlank(weChatMchCertSerialNo)) {
                    log.error(
                            String.format("mchId[%s]、we_chat_mch_private_key[%s]、" +
                                            "weChatApiV3Key[%s]、weChatMchCertSerialNo[%s],数据查询不到",
                                    mchId, weChatMchPrivateKey, weChatApiV3Key, weChatMchCertSerialNo));
                }

                // 加载商户私钥（privateKey：私钥字符串）
                PrivateKey merchantPrivateKey = PemUtil
                        .loadPrivateKey(new ByteArrayInputStream(weChatMchPrivateKey.getBytes(StandardCharsets.UTF_8)));

                // 加载平台证书（mchId：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3密钥）
                AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                        new WechatPay2Credentials(mchId, new PrivateKeySigner(weChatMchCertSerialNo, merchantPrivateKey)),
                        weChatApiV3Key.getBytes(StandardCharsets.UTF_8));

                // 初始化httpClient
                return WechatPayHttpClientBuilder.create()
                        .withMerchant(mchId, weChatMchCertSerialNo, merchantPrivateKey)
                        .withValidator(new WechatPay2Validator(verifier));
            });

        } catch (ExecutionException e) {
            log.error("Build wechat pay http client builder error.", e);
        }

        return wechatPayHttpClientBuilder;
    }

    /**
     * 基础微信请求方法
     * @param thirdAppId 三方的appId
     * @param httpRequestBase 请求体
     * @param respClass 返回内容类型
     * @param <R> 返回内容类型
     * @return 包装后的返回内容
     */
    protected <R> WeChatResponseResultDTO<R> weChatRequest(String thirdAppId, String thirdMchid, HttpRequestBase httpRequestBase, Class<R> respClass) {

        AppDTO appDTO = appService.findByWeChatMchid(thirdAppId, thirdMchid);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("thirdAppId[%s]数据查询不到", thirdAppId));
        }

        long nonFindAppStartTime = System.currentTimeMillis();
        String mchId = appDTO.getWeChatMchid();
        String weChatMchPrivateKey = appDTO.getWeChatMchPrivateKey();
        String weChatApiV3Key = appDTO.getWeChatApiV3Key();
        String weChatMchCertSerialNo = appDTO.getWeChatMchCertSerialNo();
        if (StringUtils.isBlank(mchId)
                || StringUtils.isBlank(weChatMchPrivateKey)
                || StringUtils.isBlank(weChatApiV3Key)
                || StringUtils.isBlank(weChatMchCertSerialNo)) {
            throw new BusinessException(ErrorCodeEnum.RESOURCE_NOT_FOUND,
                    String.format("mchId[%s]、we_chat_mch_private_key[%s]、" +
                                    "weChatApiV3Key[%s]、weChatMchCertSerialNo[%s],数据查询不到",
                            mchId, weChatMchPrivateKey, weChatApiV3Key, weChatMchCertSerialNo));
        }

        // 加载商户私钥（privateKey：私钥字符串）
        CloseableHttpClient httpClient = null;
        try {
            // 初始化httpClient
            long buildWechatPayHttpClientBuilderStartTime = System.currentTimeMillis();
            WechatPayHttpClientBuilder weChatPayHttpClientBuilder =
                    buildWechatPayHttpClientBuilder(mchId, weChatMchPrivateKey, weChatApiV3Key, weChatMchCertSerialNo);

            if (weChatPayHttpClientBuilder == null) {
                throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR,
                        String.format("build we chat pay http client error. app id[%s]", appDTO.getId()));
            }
            long buildWechatPayHttpClientBuilderEndTime = System.currentTimeMillis();
            log.info("Build we chat pay http client consuming:[{}]",
                    (buildWechatPayHttpClientBuilderEndTime - buildWechatPayHttpClientBuilderStartTime));

            httpClient = weChatPayHttpClientBuilder.build();

            long executeStartTime = System.currentTimeMillis();
            CloseableHttpResponse httpResponse = httpClient.execute(httpRequestBase);
            long executeEndTime = System.currentTimeMillis();
            log.info("We chat http client execute consuming:[{}]", (executeEndTime - executeStartTime));
            HttpEntity httpResponseEntity = httpResponse.getEntity();
            StatusLine statusLine = httpResponse.getStatusLine();

            // 包装错误返回结果
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                WeChatErrorMessageDTO weChatErrorMessage
                        = convertFromHttpEntity(httpResponseEntity, WeChatErrorMessageDTO.class);

                return WeChatResponseResultDTO.failure(weChatErrorMessage, respClass);
            }

            // 包装正确返回结果
            R respBody = convertFromHttpEntity(httpResponseEntity, respClass);
            return WeChatResponseResultDTO.success(respBody);
        } catch (UnsupportedEncodingException e) {
            log.error("We chat mch private key格式错误.", e);
            throw new BusinessException(ErrorCodeEnum.RESOURCE_ERROR,
                    String.format("we_chat_mch_private_key[%s]数据错误", weChatMchPrivateKey));
        } catch (ClientProtocolException e) {
            log.error("Http协议错误", e);
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
        } catch (IOException e) {
            log.error("Http请求IO异常", e);
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
        } finally {
            httpClientClose(httpClient);
            long nonFindAppEndTime = System.currentTimeMillis();
            log.info("We chat non find app request consuming:[{}]", (nonFindAppEndTime - nonFindAppStartTime));
        }
    }

    /**
     * 对微信接口发起post请求
     * @param <B> body类型
     * @param <R> 返回内容类型
     * @param thirdAppId 三方的appId
     * @param url 请求Url
     * @param body 请求体
     * @param respClass 返回内容类型class对象
     * @return 包装后的返回内容
     */
    protected <B, R> WeChatResponseResultDTO<R> weChatPostRequest(String thirdAppId, String thirdMchid, String url, B body, Class<R> respClass) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HttpHeaders.ACCEPT, "application/json");
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE,"application/json; charset=utf-8");
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(body), StandardCharsets.UTF_8));
            return weChatRequest(thirdAppId, thirdMchid, httpPost, respClass);
        } catch (JsonProcessingException e) {
            log.error("对象转JSON处理错误.", e);
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR,
                    String.format("Request Body[%s]数据转JSON错误", body.toString()));
        }
    }

    /**
     * 对微信接口发起Get请求
     * @param thirdAppId 三方的appId
     * @param url 请求Url
     * @param args 请求参数
     * @param respClass 返回内容类型class对象
     * @param <R> 返回内容类型
     * @return 包装后的返回内容
     */
    protected <R> WeChatResponseResultDTO<R> weChatGetRequest(String thirdAppId, String thirdMchid, String url, Map<String, Object> args, Class<R> respClass) {

        // 构建请求参数
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, Object> entry : args.entrySet()) {
            params.append(entry.getKey())
                    .append("=")
                    .append(Optional
                            .ofNullable(entry.getValue())
                            .orElse("null").toString())
                    .append("&");
        }

        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }

        // 构建url
        if (params.length() > 0) {
            url = url + "?" + params.toString();
        }

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(HttpHeaders.ACCEPT, "application/json");

        return weChatRequest(thirdAppId, thirdMchid, httpGet, respClass);
    }
}
