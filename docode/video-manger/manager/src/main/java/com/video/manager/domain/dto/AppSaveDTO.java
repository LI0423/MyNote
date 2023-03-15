package com.video.manager.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: video-manger
 * @description: app save dto
 * @author: laojiang
 * @create: 2020-09-03 13:53
 **/
@Data
public class AppSaveDTO implements Serializable {

    private static final long serialVersionUID = -3135533720975911382L;

    private String name;
    private String pkg;
    private String weChatAppId;
    private String weChatAppSecret;
    private Long goldCoinRatio;
    private Boolean hasWechatWithdrawal;
    private Boolean hasContentLevel;
}
