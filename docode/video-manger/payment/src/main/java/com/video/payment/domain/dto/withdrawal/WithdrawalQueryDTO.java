package com.video.payment.domain.dto.withdrawal;

import lombok.Data;

@Data
public class WithdrawalQueryDTO {
    /**
     * 业务订单id
     */
    private String partnerTradeNo;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * pkg
     */
    private String pkg;
    /**
     * 钱数
     */
    private Long amount;
    /**
     * 业务来源
     */
    private String businessSource;

    /**
     * 身份信息， 支付宝登录号，支持邮箱和手机号格式
     */
    private String identity;

    /**
     * 名称
     */
    private String identityName;
    /**
     * 主张标题
     */
    private String title;
    /**
     * 支付方显示的名称
     */
    private String payerShowName;
    /**
     * 0 微信，  1支付宝
     */
    private Integer channel;

    private String remark;
    /**
     * 支付业务id
     */
    private String payBusinessId;

}
