package com.video.oversea.user.service.third.google;

import com.video.oversea.user.domain.dto.user.GoogleUserAuthDTO;

public interface GoogleUserAuthService {

    GoogleUserAuthDTO findByGoogleUserId(String googleUserId);

    void createByGoogleUserId(Long id, String googleUserId, Long appId);

    String updateAccessToken(String googleUserId);

    GoogleUserAuthDTO findByAccessToken(String accessToken);

    void logout(String accessToken);
}
