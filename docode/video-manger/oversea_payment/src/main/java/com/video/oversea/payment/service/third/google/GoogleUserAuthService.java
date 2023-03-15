package com.video.oversea.payment.service.third.google;

import com.video.oversea.payment.domain.dto.user.GoogleUserAuthDTO;

public interface GoogleUserAuthService {
    GoogleUserAuthDTO findByUserId(Long userId);
}
