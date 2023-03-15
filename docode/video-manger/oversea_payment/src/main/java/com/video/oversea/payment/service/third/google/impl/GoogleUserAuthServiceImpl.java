package com.video.oversea.payment.service.third.google.impl;

import com.video.entity.GoogleUserAuthDO;
import com.video.oversea.payment.domain.dto.user.GoogleUserAuthDTO;
import com.video.oversea.payment.mapper.GoogleUserAuthMapper;
import com.video.oversea.payment.service.third.google.GoogleUserAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleUserAuthServiceImpl implements GoogleUserAuthService {

    @Autowired
    private GoogleUserAuthMapper googleUserAuthMapper;

    @Override
    public GoogleUserAuthDTO findByUserId(Long userId) {
        GoogleUserAuthDO googleUserAuthDO = googleUserAuthMapper.selectByUserId(userId);
        GoogleUserAuthDTO googleUserAuthDTO = new GoogleUserAuthDTO();
        BeanUtils.copyProperties(googleUserAuthDO, googleUserAuthDTO);
        return googleUserAuthDTO;
    }
}
