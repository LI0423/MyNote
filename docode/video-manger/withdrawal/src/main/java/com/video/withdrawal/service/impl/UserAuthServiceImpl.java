package com.video.withdrawal.service.impl;

import com.video.entity.UserAuthDO;
import com.video.withdrawal.domain.dto.user.UserAuthDTO;
import com.video.withdrawal.mapper.UserAuthMapper;
import com.video.withdrawal.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public UserAuthDTO findByUserId(Long userId) {
        UserAuthDO userAuthDO = userAuthMapper.selectByUserId(userId);
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        BeanUtils.copyProperties(userAuthDO, userAuthDTO);
        return userAuthDTO;
    }
}
