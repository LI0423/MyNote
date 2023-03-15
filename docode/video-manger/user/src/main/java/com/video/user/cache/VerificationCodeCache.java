package com.video.user.cache;

public interface VerificationCodeCache {

    /**
     * 存储[手机号码 -> 验证码]对
     * @param phoneNumber 手机号码
     * @param verificationCode 验证码
     * @param expire 验证码有效时间(单位:ms)
     */
    void saveVerificationCode(String phoneNumber, String verificationCode, long expire);

    /**
     * 获取phoneNumber对应的验证码
     * @param phoneNumber 手机号码
     * @return 验证码
     */
    String getVerificationCode(String phoneNumber);

    /**
     * 删除手机对应的验证码数据
     * @param phoneNumber 手机号码
     */
    Long delVerificationCode(String phoneNumber);

}
