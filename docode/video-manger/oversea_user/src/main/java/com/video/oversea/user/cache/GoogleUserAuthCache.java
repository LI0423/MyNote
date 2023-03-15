package com.video.oversea.user.cache;

import com.video.oversea.user.domain.dto.user.GoogleUserAuthDTO;

public interface GoogleUserAuthCache {
    GoogleUserAuthDTO getGoogleUserAuthDtoByGoogleUserId(String googleUserId);

    void setGoogleUserAuthDtoByGoogleUserId(String googleUserId, GoogleUserAuthDTO googleUserAuthDTO);

    void delGoogleUserAuthDtoByGoogleUserId(String googleUserId);

    GoogleUserAuthDTO getGoogleUserAuthDtoByAccessToken(String accessToken);

    void setGoogleUserAuthDtoByAccessToken(String accessToken, GoogleUserAuthDTO googleUserAuthDTO);

    void delGoogleUserAuthDtoByAccessToken(String accessToken);
}
