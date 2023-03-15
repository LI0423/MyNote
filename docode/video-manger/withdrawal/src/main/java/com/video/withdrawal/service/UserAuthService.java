package com.video.withdrawal.service;


import com.video.withdrawal.domain.dto.user.UserAuthDTO;

public interface UserAuthService {

    UserAuthDTO findByUserId(Long userId);
}
