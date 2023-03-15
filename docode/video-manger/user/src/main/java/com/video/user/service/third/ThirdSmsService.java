package com.video.user.service.third;

import org.apache.commons.lang3.StringUtils;

public interface ThirdSmsService {

    /**
     * 发送验证码短信
     * @param phoneNumber 电话号码
     * @param code 验证码
     * @return 成功与否
     */
    boolean sendVerificationCodeSms(Long appId, String phoneNumber, String code);

    /**
     * 检查验证码是否正确
     * @param phoneNumber 电话号码
     * @param code 验证码
     * @return 验证码是不是此电话号码的
     */
    boolean checkVerificationCode(String phoneNumber, String code);

    /**
     * 清理手机的验证码相关的数据
     * @param phoneNumber 电话号码
     */
    boolean clearVerificationCode(String phoneNumber);

    /**
     * 生成验证码,多机器和并发情况下可能产生重复的字符串
     * @return 验证码字符串
     */
    default String createVerificationCode() {
        long currentTimeMillis = System.currentTimeMillis();
        String currentTimeMillisStr = Long.toString(currentTimeMillis);
        int currentTimeMillisStrLen = currentTimeMillisStr.length();
        return StringUtils.substring(currentTimeMillisStr, currentTimeMillisStrLen - 6);
    }
}
