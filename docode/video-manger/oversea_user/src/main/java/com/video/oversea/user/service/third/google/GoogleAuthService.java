package com.video.oversea.user.service.third.google;

import com.video.oversea.user.domain.dto.third.google.GoogleUserDTO;

public interface GoogleAuthService {
    GoogleUserDTO verify(String pkg, String code);
}
