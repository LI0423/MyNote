package com.video.user.domain.dto.third;

import lombok.Data;

@Data
public class WeChatUserInfoQueryDTO {

    private String accessToken;

    private String openId;
}
