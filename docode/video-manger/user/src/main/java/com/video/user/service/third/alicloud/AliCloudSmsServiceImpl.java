package com.video.user.service.third.alicloud;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.user.cache.VerificationCodeCache;
import com.video.user.cache.impl.SimpleRedisUtil;
import com.video.user.config.prop.AliCloudSmsProperties;
import com.video.user.config.prop.SmsGlobalProperties;
import com.video.user.constant.RedisContants;
import com.video.user.constant.ThridConstants;
import com.video.user.domain.dto.app.AppDTO;
import com.video.user.domain.dto.third.alicloud.AliCloudVerCodeTemplateParamsDTO;
import com.video.user.exception.BusinessException;
import com.video.user.exception.ErrorCodeEnum;
import com.video.user.jedis.listener.BaseJedisPubSub;
import com.video.user.service.AppService;
import com.video.user.service.third.ThirdSmsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service(ThridConstants.ALI_CLOUD_SMS_SERVICE_NAME)
public class AliCloudSmsServiceImpl implements ThirdSmsService {

    private final AliCloudSmsProperties aliCloudSmsProperties;

    private final SmsGlobalProperties smsGlobalProperties;

    private final AppService appService;

    private final VerificationCodeCache verificationCodeCache;

    private final ObjectMapper objectMapper;

    private final ThreadPoolExecutor workerThreadPoolExecutor;

    private final Jedis listenerRedisClient;

    private final SimpleRedisUtil simpleRedisUtil;

    private final RealSmsEventExecutor realSmsEventExecutor = new RealSmsEventExecutor();

    private final RealSmsEventListener realSmsEventListener = new RealSmsEventListener();

    /* 抢主用key模板 */
    private static final String SEND_VER_CODE_GRAB_MASTER_KEY_TMPL = "SEND_VER_CODE_GRAB_MASTER_%s";

    /* 抢主用value */
    private static final String SEND_VER_CODE_GRAB_MASTER_VALUE = "1";

    /* 发送验证码事件管道名 */
    private static final String SEND_VER_CODE_SMS_CHANNEL_NAME = "CHANNEL:SEND_VER_CODE_SMS";

    /**
     * 构建抢主用key
     * @param phoneNumber
     * @return
     */
    private static String buildSendVerCodeGrabMasterKey(String phoneNumber) {
        return String.format(SEND_VER_CODE_GRAB_MASTER_KEY_TMPL, phoneNumber);
    }

    @Autowired
    public AliCloudSmsServiceImpl(AliCloudSmsProperties aliCloudSmsProperties,
                                  SmsGlobalProperties smsGlobalProperties,
                                  AppService appService,
                                  VerificationCodeCache verificationCodeCache,
                                  ObjectMapper objectMapper,
                                  @Qualifier("aliCloudSmsWorkerThreadPool") ThreadPoolExecutor workerThreadPoolExecutor,
                                  @Qualifier("aliCloudSmsListenerRedisClient") Jedis listenerRedisClient,
                                  SimpleRedisUtil simpleRedisUtil) {
        this.aliCloudSmsProperties = aliCloudSmsProperties;
        this.smsGlobalProperties = smsGlobalProperties;
        this.appService = appService;
        this.objectMapper = objectMapper;
        this.workerThreadPoolExecutor = workerThreadPoolExecutor;
        this.listenerRedisClient = listenerRedisClient;
        this.simpleRedisUtil = simpleRedisUtil;
        this.verificationCodeCache = verificationCodeCache;

        new Thread(realSmsEventExecutor).start();
    }

    @PreDestroy
    private void destroy() {

        // 短信执行者执行标志置位
        realSmsEventExecutor.shutdown();

        // 取消监听器监听
        realSmsEventListener.unsubscribe(SEND_VER_CODE_SMS_CHANNEL_NAME);
    }

    @Override
    public boolean sendVerificationCodeSms(Long appId, String phoneNumber, String code) {
        log.info("sendVerificationCodeSms, aliCloudSmsProperties:[{}]", aliCloudSmsProperties);
        AppDTO app = appService.find(appId);
        if (app.getSmsRegionId() == null || app.getSmsSignName() == null || app.getSmsAccessSecret() == null
                || app.getSmsAccessKeyId() == null || app.getSmsTemplateCode() == null) {
            throw new BusinessException(ErrorCodeEnum.SERVER_SMS_CONFIG_NOT_FOUND,
                    String.format("region id:[%s], sign name:[%s], access secret:[%s], access key id:[%s], template code:[%s]",
                            app.getSmsRegionId(), app.getSmsSignName(), app.getSmsAccessSecret(),
                            app.getSmsAccessKeyId(), app.getSmsTemplateCode()));
        }

        SendDownSmsEvent sendDownSmsEvent = new SendDownSmsEvent();
        sendDownSmsEvent.setPhoneNumber(phoneNumber);
        sendDownSmsEvent.setMessage(code);
        sendDownSmsEvent.setFromAppId(appId);
        sendDownSmsEvent.setType(EventTypeEnum.SEND_VER_CODE_SMS);
        simpleRedisUtil.publish(SEND_VER_CODE_SMS_CHANNEL_NAME, sendDownSmsEvent);
        return true;
    }

    @Override
    public boolean checkVerificationCode(String phoneNumber, String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Code param can't be null.");
        }
        String verificationCode = verificationCodeCache.getVerificationCode(phoneNumber);
        return code.equals(verificationCode);
    }

    @Override
    public boolean clearVerificationCode(String phoneNumber) {
        Long result = verificationCodeCache.delVerificationCode(phoneNumber);
        // TODO: 后续根据实际情况判断是否需要去掉这行代码
        // 删除成功的线程才可以执行这个操作
        if (Long.valueOf(1L).equals(result)) {
            simpleRedisUtil.del(buildSendVerCodeGrabMasterKey(phoneNumber));
            return true;
        }
        return false;
    }

    /**
     * 发送短信行为枚举
     */
    @Getter
    @AllArgsConstructor
    enum SysActionEnum {

        /* 发给单个用户 */
        SEND_SMS(0, "SendSms");

        private final int code;

        private final String action;
    }

    /**
     * 事件类型枚举类
     */
    @Getter
    @AllArgsConstructor
    enum EventTypeEnum {

        /* 发送验证码 */
        SEND_VER_CODE_SMS(0, "发送验证码");

        private final int code;

        private final String desc;
    }

    /**
     * 发送下行短信事件
     */
    @Data
    static class SendDownSmsEvent implements Serializable {

        /**
         * 事件类型
         */
        private EventTypeEnum type;

        /**
         * 移动电话号码
         */
        private String phoneNumber;

        /**
         * 消息
         */
        private String message;

        /**
         * 来自哪个app
         */
        private Long fromAppId;

    }

    class RealSendDownSmsWorker implements Runnable {

        private final SendDownSmsEvent sendDownSmsEvent;

        public RealSendDownSmsWorker(SendDownSmsEvent sendDownSmsEvent) {
            if (sendDownSmsEvent == null) {
                throw new IllegalArgumentException("sendDownSmsEvent param can't be null.");
            }
            this.sendDownSmsEvent = sendDownSmsEvent;
        }

        @Override
        public void run() {
            // 发送短信
            if (EventTypeEnum.SEND_VER_CODE_SMS.equals(sendDownSmsEvent.type)) {
                if (sendDownSmsEvent.fromAppId == null) {
                    log.error("Send the verification code error. " +
                            "from app id not found. event:[{}]", sendDownSmsEvent);
                    return;
                }

                try {
                    // 抢主
                    String isSuccess = simpleRedisUtil
                            .set(buildSendVerCodeGrabMasterKey(sendDownSmsEvent.getPhoneNumber()),
                                    SEND_VER_CODE_GRAB_MASTER_VALUE, SetParams.setParams()
                                            .px(smsGlobalProperties.getSendVerCodeTimeInterval()));

                    if (RedisContants.SET_NX_SUCCESS_VALUE.equals(isSuccess)) {
                        AppDTO app = appService.find(sendDownSmsEvent.getFromAppId());
                        DefaultProfile profile =
                                DefaultProfile.getProfile(app.getSmsRegionId(),
                                        app.getSmsAccessKeyId(), app.getSmsAccessSecret());

                        AliCloudVerCodeTemplateParamsDTO templateParams =
                                new AliCloudVerCodeTemplateParamsDTO(sendDownSmsEvent.getMessage());

                        IAcsClient client = new DefaultAcsClient(profile);

                        CommonRequest request = new CommonRequest();
                        request.setSysMethod(MethodType.POST);
                        request.setSysDomain(aliCloudSmsProperties.getSysDomain());
                        request.setSysVersion(aliCloudSmsProperties.getSysVersion());
                        request.setSysAction(SysActionEnum.SEND_SMS.getAction());
                        request.putQueryParameter("PhoneNumbers", sendDownSmsEvent.getPhoneNumber());
                        request.putQueryParameter("SignName", app.getSmsSignName());
                        request.putQueryParameter("TemplateCode", app.getSmsTemplateCode());
                        request.putQueryParameter("TemplateParam", objectMapper.writeValueAsString(templateParams));

                        // 先设置验证码到缓存中, 如果最后发送失败, 则让用户再等待60s后再次请求获取验证码即可
                        verificationCodeCache.saveVerificationCode(sendDownSmsEvent.getPhoneNumber(),
                                sendDownSmsEvent.getMessage(), smsGlobalProperties.getVerCodeEffectiveTime());

                        CommonResponse response = client.getCommonResponse(request);
                        if (HttpStatus.SC_OK != response.getHttpStatus()) {
                            log.error("Send the verification code error. response status:[{}], response data:[{}]",
                                    response.getHttpStatus(), response.getData());
                            return;
                        }
                        log.info("Send the verification code:[{}] to phone number:[{}] success.",
                                sendDownSmsEvent.getMessage(), sendDownSmsEvent.getPhoneNumber());
                    }
                } catch (Exception e) {
                    log.error("Unknow exception.", e);
                }
            } else {
                log.warn("Send down sms event type fail. event:[{}]", sendDownSmsEvent);
            }
        }
    }

    class RealSmsEventListener extends BaseJedisPubSub<SendDownSmsEvent> {
        @Override
        protected void onMessage(String channel, SendDownSmsEvent event) {
            workerThreadPoolExecutor.execute(new RealSendDownSmsWorker(event));
        }
    }

    class RealSmsEventExecutor implements Runnable {

        private volatile boolean isRun;

        public RealSmsEventExecutor() {
            this.isRun = true;
        }

        public void shutdown() {
            this.isRun = false;
        }

        @Override
        public void run() {
            try (Jedis jedis = listenerRedisClient) {
                while (this.isRun) {
                    try {
                        jedis.connect();
                        jedis.subscribe(realSmsEventListener, SEND_VER_CODE_SMS_CHANNEL_NAME);
                    } catch (Exception e) {
                        log.warn("Jedis subscribe error.", e);
                    }
                }
            }
        }
    }
}
