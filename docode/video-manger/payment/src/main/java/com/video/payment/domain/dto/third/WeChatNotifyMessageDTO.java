package com.video.payment.domain.dto.third;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 微信支付通知消息
 */
@Data
public class WeChatNotifyMessageDTO {

    /**
     * 通知ID
     */
    private String id;

    /**
     * 通知创建时间
     */
    @JsonProperty("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private Date createTime;

    /**
     * 通知类型
     */
    @JsonProperty("event_type")
    private String eventType;

    /**
     * 通知数据类型
     */
    @JsonProperty("resource_type")
    private String resourceType;

    /**
     * 通知资源数据
     */
    private Resource resource;

    /**
     * 回调摘要
     */
    private String summary;

    /**
     * 通知数据
     */
    @Data
    public static class Resource {

        /**
         * 对开启结果数据进行加密的加密算法，目前只支持AEAD_AES_256_GCM
         */
        private String algorithm;

        /**
         * 数据密文
         */
        private String ciphertext;

        /**
         * 附加数据
         */
        @JsonProperty("associated_data")
        private String associatedData;

        /**
         * 原始类型
         */
        @JsonProperty("original_type")
        private String originalType;

        /**
         * 加密使用的随机串
         */
        private String nonce;
    }
}
