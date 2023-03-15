package com.video.oversea.payment.service.third.google.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Sets;
import com.video.entity.*;
import com.video.oversea.payment.config.prod.GooglePaymentProperties;
import com.video.oversea.payment.domain.dto.app.AppDTO;
import com.video.oversea.payment.domain.dto.google.GooglePayVerifyDTO;
import com.video.oversea.payment.domain.dto.google.GoogleSubNotifyMessageDTO;
import com.video.oversea.payment.domain.dto.google.GoogleSubscriptionDTO;
import com.video.oversea.payment.domain.dto.payment.NotifyResultDTO;
import com.video.oversea.payment.domain.dto.payment.PayOrderDTO;
import com.video.oversea.payment.domain.dto.payment.PayRecordDTO;
import com.video.oversea.payment.domain.dto.third.GooglePayNotifyMessageDTO;
import com.video.oversea.payment.domain.dto.third.GooglePayTradeStatusEnum;
import com.video.oversea.payment.domain.dto.user.GoogleUserAuthDTO;
import com.video.oversea.payment.enums.PkgEnum;
import com.video.oversea.payment.exception.BusinessException;
import com.video.oversea.payment.exception.ErrorCodeEnum;
import com.video.oversea.payment.mapper.GooglePayOrderMapper;
import com.video.oversea.payment.mapper.GoogleSubscriptionMapper;
import com.video.oversea.payment.mapper.PayOrderDownstreamNotifyMapper;
import com.video.oversea.payment.mapper.PayOrderMapper;
import com.video.oversea.payment.service.third.ThirdPaymentService;
import com.video.oversea.payment.service.third.google.GoogleBasePaymentService;
import com.video.oversea.payment.service.third.google.GooglePaymentService;
import com.video.oversea.payment.service.third.google.GoogleUserAuthService;
import com.video.oversea.payment.util.SimpleDateUtils;
import com.video.oversea.payment.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("GOOGLE_PAYMENT")
public class GooglePaymentServiceImpl extends GoogleBasePaymentService implements GooglePaymentService, ThirdPaymentService<GooglePayNotifyMessageDTO, GooglePayNotifyMessageDTO> {

    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    @Autowired
    private GooglePaymentProperties googlePaymentProperties;

    @Autowired
    private GoogleSubscriptionMapper googleSubscriptionMapper;

    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    private GooglePayOrderMapper googlePayOrderMapper;

    @Autowired
    private AppService appService;

    @Autowired
    private GoogleUserAuthService googleUserAuthService;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    protected PayOrderDownstreamNotifyMapper payOrderDownstreamNotifyMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 谷歌支付订单校验
     *
     * @param pkg
     * @param originalJson
     * @param signature
     * @param userId
     * @param thirdOutTradeNo 内部生成的orderId
     * @return
     */
    @Override
    public Boolean subscribe(String pkg, String originalJson,
                          String signature, Long userId, String thirdOutTradeNo) {
        try {
            // 1、做订单校验，这个是纯本地校验，不需要请求google
            Boolean valid = verifyPurchase(originalJson, signature, pkg);
            if (!valid) {
                log.error("[google-subscribe] invalid originalJson {}", originalJson);
                //throw new BusinessException(PAY_GOOGLE_ORDER_INVALID);
            }

            // 2、解析信息
            JsonNode jsonNode = objectMapper.readTree(originalJson);

            // 3、获取 订阅id、购买token、订单id
            String subscriptionId = jsonNode.get("productId").asText();
            String purchaseToken = jsonNode.get("purchaseToken").asText();

            // 4、查询是否处理过
            GoogleSubscriptionDO googleSubscriptionDO = findByPurchaseTokenOrderByCtime(purchaseToken);
            if (googleSubscriptionDO != null) {
                log.error("[google-subscribe] order finished {}", purchaseToken);
                //throw new BusinessException(ErrorCodeEnum.PAY_GOOGLE_ORDER_FINISHED);
            }

            // 5、查询用户是否正在订阅中
//            Boolean expire = findSubscribeIsExpire(userId, System.currentTimeMillis());
//            if (expire) {
//                log.warn("[google-subscription] user {} already has active subscription, system will cancel this one {}", userId,
//                        purchaseToken);
//                cancelSubscription(pkg, subscriptionId, purchaseToken);
//                throw new BusinessException(ErrorCodeEnum.PAY_GOOGLE_ALREADY_SUBSCRIBED);
//            }

            // 5、获取订阅详细信息
            GoogleSubscriptionDTO googleSubscriptionDTO = getSubscription(pkg, subscriptionId, purchaseToken);
            if (googleSubscriptionDTO == null) {
                log.error("[google-subscription] invalid subscription {} {} {}", pkg, subscriptionId, purchaseToken);
                //throw new BusinessException(PAY_GOOGLE_ORDER_INVALID);
            }

//        // 7、查看支付状态
//        // 0. 待付款 1. 已收到付款 2. 免费试用 3. 待延期升级/降级
//        Integer paymentState = subscriptionPurchase.getPaymentState();
//        if (paymentState == null || paymentState < 1 || paymentState > 2) {
//            log.warn("[google-subscription] invalid paymentState {} {} {}", pkg, subscriptionId, purchaseToken);
//            throw new BusinessException(ErrorCodeEnum.PAY_GOOGLE_ORDER_INVALID);
//        }

            googleSubscriptionDO = GoogleSubscriptionDO.builder()
                    .subscriptionId(subscriptionId)
                    .pkg(pkg)
                    .userId(userId)
                    .orderId(googleSubscriptionDTO.getOrderId())
                    .thirdOutTradeNo(thirdOutTradeNo)
                    .amount(googleSubscriptionDTO.getPriceAmountMicros())
                    .country(googleSubscriptionDTO.getCountryCode())
                    .paymentStatus(googleSubscriptionDTO.getPaymentState())
                    .currency(googleSubscriptionDTO.getPriceCurrencyCode())
                    .purchaseToken(purchaseToken)
                    .expiryTimeMillis(googleSubscriptionDTO.getExpiryTimeMillis())
                    .extra(objectMapper.writeValueAsString(googleSubscriptionDTO))
                    .build();


            // 7、谷歌订单信息存储
            googleSubscriptionMapper.insert(googleSubscriptionDO);

            // 8、rpc回调妙趣p图
            GooglePayNotifyMessageDTO googlePayNotifyMessageDTO = new GooglePayNotifyMessageDTO();
            googlePayNotifyMessageDTO.setThirdOutTradeNo(thirdOutTradeNo);
            googlePayNotifyMessageDTO.setTradeState(googleSubscriptionDTO.getPaymentState());
            googlePayNotifyMessageDTO.setOrderId(googleSubscriptionDTO.getOrderId());
            googlePayNotifyMessageDTO.setExpiryTimeMillis(googleSubscriptionDTO.getExpiryTimeMillis());
            googlePayNotifyMessageDTO.setStartTimeMillis(googleSubscriptionDTO.getStartTimeMillis());

            payResultCallback(googlePayNotifyMessageDTO);

            return true;
        } catch (Exception e) {
            log.error("支付信息解析异常: e:{}",e);
        }
        return false;
    }

    @Override
    public GooglePayOrderDO pay(Long userId, String pkg, Long amount, PayOrderPayTypeEnum payType,
                                Integer businessType, String orderDesc, String notifyUrl,
                                String originalJson, String signature) throws Exception{

        // 1、做订单校验，这个是纯本地校验，不需要请求google
        Boolean valid = verifyPurchase(originalJson, signature, pkg);
        if (!valid) {
            log.warn("[google-pay] invalid originalJson {}", originalJson);
            throw new BusinessException(ErrorCodeEnum.PAY_GOOGLE_ORDER_INVALID);
        }

        // 2、解析信息
        JsonNode jsonNode = objectMapper.readTree(originalJson);

        // 3、获取商品购买状态
        // purchaseState：0. 已购买 1. 已取消 2. 待定
        if (jsonNode.get("purchaseState").asInt() != 0) {
            log.warn("[google-pay] wrong order state {}", originalJson);
            throw new BusinessException(ErrorCodeEnum.PAY_GOOGLE_WRONG_ORDER_STATE);
        }

        // 产品id
        String productId = jsonNode.get("productId").asText();
        // 三方的订单id
        String thirdOrderId = jsonNode.get("orderId").asText();
        String purchaseToken = jsonNode.get("purchaseToken").asText();

        GooglePayOrderDO googlePayOrderDO = googlePayOrderMapper.findByThirdOrderId(thirdOrderId);
        if (googlePayOrderDO != null) {
            log.warn("[google-pay] order finished {}", thirdOrderId);
            throw new BusinessException(ErrorCodeEnum.PAY_GOOGLE_ORDER_FINISHED);
        }

        // 获取购买的
        ProductPurchase productPurchase = getProductPurchase(pkg, productId, purchaseToken);
        if (productPurchase == null) {
            log.warn("[google-subscription] invalid subscription {} {} {}", pkg, productId, purchaseToken);
            throw new BusinessException(ErrorCodeEnum.PAY_GOOGLE_ORDER_INVALID);
        }

        googlePayOrderDO = GooglePayOrderDO.builder()
                .pkg(pkg)
                .userId(userId)
                .orderId(productPurchase.getOrderId())
                .amount(null)
                .country(null)
                .status(PayOrderStatusEnum.SUCCESS)
                .channel(PayOrderChannelEnum.GOOGLE_PAY)
                .thirdOrderId(productPurchase.getOrderId())
                .extra(objectMapper.writeValueAsString(productPurchase))
                .build();

        // 谷歌订单信息存储
        googlePayOrderMapper.insert(googlePayOrderDO);

        return googlePayOrderDO;
    }

    @Override
    public void googleSubscribeNotify(String eventJson) {
        JsonNode subscriptionNode = null;
        JsonNode pkgNode = null;
        try {
            JsonNode eventNode = objectMapper.readTree(eventJson);
            JsonNode messageDataNode = eventNode.path("message").path("data");
            if (messageDataNode.isMissingNode()) {
                return;
            }
            byte[] decodedBytes = Base64.getDecoder().decode(messageDataNode.asText());
            String decodedData = new String(decodedBytes);
            log.info("[google-subscription] decodedData {}", decodedData);
            JsonNode dataNode = objectMapper.readTree(decodedData);
            subscriptionNode = dataNode.path("subscriptionNotification");
            pkgNode = dataNode.path("packageName");
            if (subscriptionNode.isMissingNode() || pkgNode.isMissingNode()) {
                return;
            }
        } catch (JsonProcessingException e) {
            log.error("自动订阅的请求解析失败");
        }
        String pkg = pkgNode.asText();
        String purchaseToken = subscriptionNode.get("purchaseToken").asText();
        String subscriptionId = subscriptionNode.get("subscriptionId").asText();

        updateSubscription(pkg, subscriptionId, purchaseToken);
    }

    private void updateSubscription(String pkg, String subscriptionId, String purchaseToken) {

        try {
            // 拿到订单信息
            GoogleSubscriptionDTO subscriptionPurchase = getSubscription(pkg, subscriptionId, purchaseToken);
            if (subscriptionPurchase == null) {
                log.warn("[google-subscription] invalid subscription {} {} {}", pkg, subscriptionId, purchaseToken);
                return;
            }

            GoogleSubscriptionDO currentSubscription = googleSubscriptionMapper.findByPurchaseToken(purchaseToken);
            String extra = objectMapper.writeValueAsString(subscriptionPurchase);
            Date now = new Date();
            if (currentSubscription != null) {
                if (subscriptionPurchase.getCancelReason() != null) {
                    currentSubscription.setCancelReason(subscriptionPurchase.getCancelReason());
                    currentSubscription.setUserCancellationTimeMillis(subscriptionPurchase.getUserCancellationTimeMillis());
                    googleSubscriptionMapper.updateById(currentSubscription);
                    return;
                }

                // 0：代付款，1：已付款
                if (subscriptionPurchase.getPaymentState() != 1) {
                    log.error("订单未完成支付，支付状态为：{}", subscriptionPurchase.getPaymentState());
                    return;
                }

                String thirdOutTradeNo = currentSubscription.getThirdOutTradeNo();
                PayOrderDO oldPayOrder = payOrderMapper.selectByThirdOutTradeNo(thirdOutTradeNo);

                PayOrderDO payOrderDO = new PayOrderDO();
                BeanUtils.copyProperties(oldPayOrder, payOrderDO);
                payOrderDO.setCreateTime(now);
                payOrderDO.setId(null);

                // 插入新的订单
                payOrderMapper.insert(payOrderDO);

                // 插入新的订单明细
                PayRecordDO payRecordDO = new PayRecordDO();
                payRecordDO.setPayOrderId(payOrderDO.getId());
                payRecordDO.setResultStatus(PayRecordStatusEnum.SUCCESS);
                payRecordDO.setEventType(StringUtils.EMPTY);
                payRecordDO.setSummary(StringUtils.EMPTY);
                payRecordDO.setResult(StringUtils.EMPTY);
                payRecordDO.setCreateTime(now);
                payRecordDO.setModifyTime(now);
                payRecordMapper.insert(payRecordDO);

                PayOrderDO updateCondition = new PayOrderDO();
                updateCondition.setId(payOrderDO.getId());
                updateCondition.setThirdOutTradeNo(payOrderDO.getId().toString());
                payOrderMapper.updateById(updateCondition);

                // 更新过期时间和本地生成的三方id
                GoogleSubscriptionDO updateGoogleSub = new GoogleSubscriptionDO();
                updateGoogleSub.setExpiryTimeMillis(subscriptionPurchase.getExpiryTimeMillis());
                updateGoogleSub.setThirdOutTradeNo(payOrderDO.getThirdOutTradeNo());
                updateGoogleSub.setExtra(extra);
                updateGoogleSub.setId(currentSubscription.getId());
                googleSubscriptionMapper.updateExpiryTime(currentSubscription.getId(),
                        subscriptionPurchase.getExpiryTimeMillis(), payOrderDO.getThirdOutTradeNo(), extra);

                // 回调妙趣p图，生成订单，给会员
                subNotify(currentSubscription.getUserId(), payOrderDO.getType(), pkg, oldPayOrder.getId(),
                        payOrderDO.getId(), subscriptionPurchase.getExpiryTimeMillis(), subscriptionPurchase.getStartTimeMillis());

            } else {
                log.error("[google-subscription] can't find old subscription, purchaseToken:{}", purchaseToken);
            }

        } catch (Exception e) {
            log.error("updateSubscription method parsing error");
        }
    }

    @Async
    public void subNotify(Long userId, Integer businessType, String pkg, Long oldOrderId, Long newOrderId, Long expiryTimeMillis, Long startTimeMillis) {

        // 这里需要处理成通过pkg获取url
        String notifyUrl = googlePaymentProperties.getNotifyUrl();

        AppDTO appDTO = appService.find(pkg);
        if (appDTO == null) {
            log.error(String.format("app[%s]数据查询不到", pkg));
            return;
        }

        GoogleSubNotifyMessageDTO notifyMessageDTO = new GoogleSubNotifyMessageDTO();
        notifyMessageDTO.setUserId(userId);
        notifyMessageDTO.setPkg(appDTO.getPkg());
        notifyMessageDTO.setBusinessType(businessType);
        notifyMessageDTO.setOldOrderId(oldOrderId);
        notifyMessageDTO.setNewOrderId(newOrderId);
        notifyMessageDTO.setExpiryTimeMillis(expiryTimeMillis);
        notifyMessageDTO.setStartTimeMillis(startTimeMillis);

        // 请求下游接口
        // 重试机制
        ResponseEntity<String> responseEntity = null;
        for (int i = 0; i <= 2; i++) {
            try {
                responseEntity = restTemplate.postForEntity(notifyUrl,
                                notifyMessageDTO, String.class);

                log.info("Pay notify downstream result:[{}], pay order id:[{}]",
                        responseEntity.getBody(), userId);

                break;
            } catch (Exception e) {
                log.error("Payment notify post request error. retry num:" + i + ", pay order id:", e);
                continue;
            }
        }

        // TODO: 失败处理，后面需要重新发送
    }

    private ProductPurchase getProductPurchase(String pkg, String productId, String purchaseToken) throws Exception {
        AndroidPublisher androidPublisher = getAndroidPublisher(pkg);
        ProductPurchase productPurchase = androidPublisher.purchases().products().get(pkg, productId, purchaseToken).execute();
        log.info("[google-subscription] subscription {} {} {} {}", pkg, productId, purchaseToken,
                objectMapper.writeValueAsString(productPurchase));
        return productPurchase;
    }

    private GoogleSubscriptionDO findByPurchaseTokenOrderByCtime(String purchaseToken) {
        QueryWrapper<GoogleSubscriptionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("purchase_token", purchaseToken).orderByDesc("create_time").last("limit 1");
        GoogleSubscriptionDO googleSubscriptionDO = googleSubscriptionMapper.selectOne(queryWrapper);
        return googleSubscriptionDO;
    }

    private GoogleSubscriptionDTO getSubscription(String pkg, String subscriptionId, String purchaseToken) throws Exception {
        GooglePayVerifyDTO googlePayVerifyDTO = new GooglePayVerifyDTO();
        googlePayVerifyDTO.setSubscriptionId(subscriptionId);
        googlePayVerifyDTO.setPkg(pkg);
        googlePayVerifyDTO.setPurchaseToken(purchaseToken);
        // 请求下游接口
        // 重试机制
        ResponseEntity<GoogleSubscriptionDTO> responseEntity = null;
        for (int i = 0; i <= 2; i++) {
            try {
                responseEntity =
                        // 此处请求香港节点的接口
                        restTemplate.postForEntity("http://119.13.88.37:8090/api/oversea/verify/order",
                                googlePayVerifyDTO, GoogleSubscriptionDTO.class);

                log.info("Pay notify downstream result:[{}], subscriptionId:[{}], purchaseToken:[{}]",
                        responseEntity.getBody(), subscriptionId, purchaseToken);

                break;
            } catch (Exception e) {
                log.error("Payment notify post request error. retry num:" + i +
                        ", subscriptionId:" + subscriptionId + ", purchaseToken:" + purchaseToken + ",{}", e);
                continue;
            }
        }
        return responseEntity.getBody();
    }

    private AndroidPublisher getAndroidPublisher(String pkg) throws Exception {
        // 此处需要一个根据pkg找到的json
        GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream(PkgEnum.getByPkg(pkg) + "_credential.json"))
                .createScoped(Sets.newHashSet(AndroidPublisherScopes.ANDROIDPUBLISHER));

        return new AndroidPublisher.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)).build();
    }

    private void cancelSubscription(String pkg, String subscriptionId, String purchaseToken) throws Exception {
        AndroidPublisher androidPublisher = getAndroidPublisher(pkg);
        androidPublisher.purchases().subscriptions().cancel(pkg, subscriptionId, purchaseToken).execute();
    }

    private Boolean findSubscribeIsExpire(Long userId, long currentTimeMillis) {
        log.error("userId:{},currentTime", userId);
        QueryWrapper<GoogleSubscriptionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<GoogleSubscriptionDO> googleSubscriptionDOS = googleSubscriptionMapper.selectList(queryWrapper);
        if (googleSubscriptionDOS.isEmpty()) {
            log.error("googleSubscriptionDOS is empty");
            return false;
        }
        if (googleSubscriptionDOS.get(0).getExpiryTimeMillis() > currentTimeMillis) {
            log.error("googleSubscriptionDOS.get(0):{}", googleSubscriptionDOS.get(0));
            return true;
        }
        return false;
    }

    /**
     * 验证订单的合法性
     *
     * @param originalJson 谷歌返回给客户端的订单json信息
     * @param signature 谷歌返回的加密信息
     * @return
     */
    private Boolean verifyPurchase(String originalJson, String signature, String pkg) throws Exception {
        return verify(originalJson, signature, googlePaymentProperties.getPubKey());
    }

    /**
     * 使用signaute和公钥验证数据有效性
     */
    public static boolean verify(String originalJson, String signature, String publicKeyStr)
            throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        log.error("进入verify");
        PublicKey publicKey = loadPublicKeyByStr(publicKeyStr);
        // RSASSA-PKCS1-v1_5
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(publicKey);
        sig.update(originalJson.getBytes());
        return sig.verify(Base64.getDecoder().decode(signature));
    }

    /**
     * 从公钥字符串产生公钥
     */
    private static PublicKey loadPublicKeyByStr(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.error("进入产生公钥");
        byte[] buffer = Base64.getDecoder().decode(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return keyFactory.generatePublic(keySpec);
    }

    @Override
    public PayOrderDTO prepay(Long userId, String pkg, Long amount, PayOrderPayTypeEnum payType, Integer businessType, String orderDesc, String notifyUrl) {

        if (StringUtils.isBlank(notifyUrl)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR, "必要请求参数notifyUrl没找到");
        }

        try {
            URL notifyURL = new URL(notifyUrl);
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR,
                    String.format("请求参数notifyUrl格式错误, notifyUrl:[%s]", notifyUrl));
        }

        AppDTO appDTO = appService.find(pkg);
        if (appDTO == null) {
            throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                    String.format("pkg[%s]数据查询不到", pkg));
        }

        GoogleUserAuthDTO googleUserAuthDTO = googleUserAuthService.findByUserId(userId);
        if (googleUserAuthDTO == null || googleUserAuthDTO.getUserId() == null) {
            throw new BusinessException(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND,
                    String.format("user_auth[%s]数据查询不到", userId));
        }

        PayOrderDTO payOrderDTO = save(StringUtils.EMPTY, StringUtils.EMPTY, payType, appDTO.getId(),
                PayOrderChannelEnum.GOOGLE_PAY, userId, businessType, amount, SimpleDateUtils.addMinute(new Date(),
                        googlePaymentProperties.getDefaultOrderTimeExpireMinute()), orderDesc, notifyUrl);

        return payOrderDTO;
    }

    /**
     * 保存订单以及通知url信息
     * @return
     */
    protected PayOrderDTO save(String thirdMchid, String thirdAppId, PayOrderPayTypeEnum payType, Long appId, PayOrderChannelEnum channel,
                               Long userId, Integer businessType, Long amount, Date prepayExpireTime, String shortDesc, String notifyUrl) {

        Date now = new Date();
        // 预支付订单关闭时间
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setChannel(channel);
        payOrderDO.setThirdMchid(thirdMchid);
        payOrderDO.setThirdAppId(thirdAppId);
        payOrderDO.setThirdPayType(payType);
        payOrderDO.setAppId(appId);
        payOrderDO.setUserId(userId);
        payOrderDO.setType(businessType);
        payOrderDO.setAmount(amount);
        payOrderDO.setPrepayExpireTime(prepayExpireTime);
        payOrderDO.setShortDesc(shortDesc);
        payOrderDO.setCreateTime(now);
        payOrderDO.setModifyTime(now);
        payOrderMapper.insert(payOrderDO);

        PayOrderDO updateCondition = new PayOrderDO();
        updateCondition.setId(payOrderDO.getId());
        updateCondition.setThirdOutTradeNo(payOrderDO.getId().toString());
        payOrderMapper.updateById(updateCondition);

        // 记录下游商户与订单关联的信息
        PayOrderDownstreamNotifyDO payOrderDownstreamNotifyDO = new PayOrderDownstreamNotifyDO();
        payOrderDownstreamNotifyDO.setPayOrderId(payOrderDO.getId());
        payOrderDownstreamNotifyDO.setNotifyUrl(notifyUrl);
        payOrderDownstreamNotifyMapper.insert(payOrderDownstreamNotifyDO);

        return ThirdPaymentService.convertTo(payOrderDO);
    }

    @Override
    public NotifyResultDTO payResultCallback(GooglePayNotifyMessageDTO googlePayNotifyMessageDTO) {

        PayOrderDO payOrderDO = payOrderMapper.selectByThirdOutTradeNo(googlePayNotifyMessageDTO.getThirdOutTradeNo());
        if (payOrderDO == null) {
            log.warn("支付订单未找到, out trade no:[{}], resource:[{}]",
                    googlePayNotifyMessageDTO.getThirdOutTradeNo(), googlePayNotifyMessageDTO);
            return NotifyResultDTO.fail();
        }

        if (PayOrderStatusEnum.SUCCESS.equals(payOrderDO.getStatus())) {
            log.info("支付订单已完成, out trade no:[{}], resource:[{}]",
                    googlePayNotifyMessageDTO.getThirdOutTradeNo(), googlePayNotifyMessageDTO);
            return NotifyResultDTO.fail();
        }

        Date now = new Date();
        PayRecordDTO payRecordDTO = new PayRecordDTO();
        // 支付成功则更新交易订单信息, 交易失败只将失败记录记录到表中
        if (GooglePayTradeStatusEnum.SUCCESS.getCode() == googlePayNotifyMessageDTO.getTradeState() &&
                !PayOrderStatusEnum.SUCCESS.equals(payOrderDO.getStatus())) {
            // 修改支付状态
            payOrderMapper.updatePayOrderById(payOrderDO.getId(), googlePayNotifyMessageDTO.getOrderId(), PayOrderStatusEnum.SUCCESS.getCode());

            PayRecordDO payRecordDO = new PayRecordDO();
            payRecordDO.setPayOrderId(payOrderDO.getId());
            payRecordDO.setResultStatus(PayRecordStatusEnum.SUCCESS);
            payRecordDO.setEventType(StringUtils.EMPTY);
            payRecordDO.setSummary(StringUtils.EMPTY);
            payRecordDO.setResult(StringUtils.EMPTY);
            payRecordDO.setCreateTime(now);
            payRecordDO.setModifyTime(now);
            payRecordMapper.insert(payRecordDO);
            log.info("google notify message:[{}]", googlePayNotifyMessageDTO);

            payRecordDTO = ThirdPaymentService.convertTo(payRecordDO);

            payOrderCache.delPayOrderDTOById(payOrderDO.getId());
            payOrderCache.delPayOrderDTOByUserIdAndAppIdAndPrepayId(payOrderDO.getUserId(),
                    payOrderDO.getAppId(), payOrderDO.getThirdPrepayId());
        } else {
            PayRecordDO payRecordDO = new PayRecordDO();
            payRecordDO.setPayOrderId(payOrderDO.getId());
            payRecordDO.setResultStatus(PayRecordStatusEnum.FAIL);
            payRecordDO.setEventType(StringUtils.EMPTY);
            payRecordDO.setSummary(StringUtils.EMPTY);
            payRecordDO.setResult(StringUtils.EMPTY);
            payRecordDO.setCreateTime(now);
            payRecordDO.setModifyTime(now);
            payRecordMapper.insert(payRecordDO);

            payRecordDTO = ThirdPaymentService.convertTo(payRecordDO);
            log.info("google notify message:[{}]", googlePayNotifyMessageDTO);

        }

        // 通知app的服务端
        payNotifyDownstreamMerchant(payOrderDO.getAppId(), googlePayNotifyMessageDTO.getThirdOutTradeNo(), payRecordDTO, googlePayNotifyMessageDTO.getStartTimeMillis(), googlePayNotifyMessageDTO.getExpiryTimeMillis());

        return NotifyResultDTO.success();
    }

}
