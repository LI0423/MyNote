package com.video.oversea.payment.domain.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoogleUserAuthDTO implements Serializable {
    private static final long serialVersionUID = -717266554772661024L;

    private Long appId;

    private Long userId;

    private String googleUserId;

    private String accessToken;
}
