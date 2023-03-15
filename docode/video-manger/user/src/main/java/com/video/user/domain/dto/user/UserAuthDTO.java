package com.video.user.domain.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAuthDTO implements Serializable {

    private static final long serialVersionUID = 5751223947738549797L;

    private Long appId;
    private Long userId;
    private String openId;
    private String thirdPartyAccessToken;
    private String accessToken;
    private String token;
}
