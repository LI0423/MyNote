package com.video.manager.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: video-manger
 * @description: app dto
 * @author: laojiang
 * @create: 2020-09-03 13:51
 **/
@Data
public class AppDTO implements Serializable {
    private static final long serialVersionUID = 4715450797526805605L;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String name;
    private String pkg;
    private String weChatAppId;
    private String weChatAppSecret;
    private String weChatMchid;
    private Long goldCoinRatio;
    private Boolean hasWechatWithdrawal;
    private Boolean hasContentLevel;
    private Date createTime;
    private String createBy;
    private Date lastModifyTime;
    private String lastModifyBy;
    private String weChatApiKey;
    private String weChatApiCert;
    private String appVns;
    private String sids;

    /**
     * 是否开启debug模式
     */
    private Boolean isDebugModel;
}
