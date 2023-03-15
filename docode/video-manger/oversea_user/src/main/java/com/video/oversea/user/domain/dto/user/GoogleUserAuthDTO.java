package com.video.oversea.user.domain.dto.user;

import lombok.Data;

@Data
public class GoogleUserAuthDTO {

    private Long userId;

    private String googleUserId;

    private String accessToken;

    private Long appId;
}
