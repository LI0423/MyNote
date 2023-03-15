/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.video.oversea.user.interceptor;

import com.video.oversea.user.domain.common.CommonParams;
import com.video.oversea.user.exception.BusinessException;
import com.video.oversea.user.exception.ErrorCodeEnum;
import com.video.oversea.user.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ApiIntercepter implements HandlerInterceptor {

    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        CommonParams commonParams = toCommonParams(request);
        securityService.checkParams(commonParams);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private CommonParams toCommonParams(HttpServletRequest request) {
        String token = request.getParameter("tk");
        String pkg = request.getParameter("pkg");
        String vn = request.getParameter("vn");
        String lang = request.getParameter("lang");
        String ts = request.getParameter("ts");
        String vc = request.getParameter("vc");
        if (StringUtils.isAnyBlank(token, pkg, vn, lang, ts, vc)) {
            log.error("token:{},pke:{},vn:{},lang:{},ts:{},vc:{}", token,pkg,vn,lang,ts,vc);
            throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
        }
        return CommonParams.builder().tk(token).pkg(pkg).vn(vn).lang(lang).ts(ts).vc(vc).build();
    }
}
