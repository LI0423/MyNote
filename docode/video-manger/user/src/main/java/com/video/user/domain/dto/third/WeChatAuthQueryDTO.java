package com.video.user.domain.dto.third;

import lombok.Data;

@Data
public class WeChatAuthQueryDTO {

    private String appId;

    private String secret;

    private String code;

}
