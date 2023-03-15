package com.video.payment.service;

import com.video.payment.domain.dto.user.UserAuthDTO;

public interface UserAuthService {

    UserAuthDTO findByUserId(Long userId);
}
