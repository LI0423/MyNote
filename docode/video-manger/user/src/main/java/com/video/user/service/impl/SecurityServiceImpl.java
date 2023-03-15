/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.video.user.service.impl;

import com.video.user.config.prop.SecurityProps;
import com.video.user.domain.common.CommonParams;
import com.video.user.exception.BusinessException;
import com.video.user.exception.ErrorCodeEnum;
import com.video.user.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class SecurityServiceImpl implements SecurityService {
    private Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private SecurityProps securityProps;

    @Override
    public void checkParams(CommonParams commonParams) {
        String vcode = DigestUtils
                .md5DigestAsHex((commonParams.getTk()
                        + commonParams.getPkg()
                        + commonParams.getVn()
                        + commonParams.getLang()
                        + commonParams.getTs()
                        + securityProps.getSalt()).getBytes());

        if (!StringUtils.equals(commonParams.getVc(), vcode)) {
            logger.warn("vcode {} va {}", vcode, commonParams.getVc());
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }
    }

}
