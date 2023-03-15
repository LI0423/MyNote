package com.video.oversea.user.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.oversea.user.constant.BusinessHeaderConstants;
import com.video.oversea.user.constant.BusinessParamConstants;
import com.video.oversea.user.domain.common.ResponseResult;
import com.video.oversea.user.domain.dto.app.AppDTO;
import com.video.oversea.user.domain.dto.user.GoogleUserAuthDTO;
//import com.video.oversea.user.domain.dto.user.UserAuthDTO;
import com.video.oversea.user.exception.ErrorCodeEnum;
import com.video.oversea.user.service.AppService;
//import com.video.oversea.user.service.UserAuthService;
import com.video.oversea.user.service.third.google.GoogleUserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final ResponseResult NOT_ADMIN_RESPONSE_RESULT = ResponseResult.failure(ErrorCodeEnum.USER_NOT_ADMIN);

    private static final ResponseResult USER_APP_MISMATCH_RESPONSE_RESULT = ResponseResult.failure(ErrorCodeEnum.USER_APP_MISMATCH);

    private ObjectMapper objectMapper;

    //private UserAuthService userAuthService;

    private GoogleUserAuthService googleUserAuthService;

    private AppService appService;

    @Autowired
    public AdminInterceptor(ObjectMapper objectMapper, AppService appService, GoogleUserAuthService googleUserAuthService) {
        this.objectMapper = objectMapper;
        //this.userAuthService = userAuthService;
        this.appService = appService;
        this.googleUserAuthService = googleUserAuthService;
    }

    private void printErrorMessage(HttpServletResponse response, ResponseResult responseResult) throws IOException {
        response.setHeader("Content-Type", "application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(objectMapper.writeValueAsString(responseResult));
        printWriter.flush();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader(BusinessHeaderConstants.ACCESS_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            log.debug("accessToken not found!");
            printErrorMessage(response, NOT_ADMIN_RESPONSE_RESULT);
            return false;
        }
        // 先尝试从cache中获取
        GoogleUserAuthDTO googleUserAuthDTO = googleUserAuthService.findByAccessToken(accessToken);
        if (googleUserAuthDTO == null) {
            log.debug("用户不存在,access token:{}", accessToken);
            printErrorMessage(response, NOT_ADMIN_RESPONSE_RESULT);
            return false;
        }

        String appPkg = request.getParameter(BusinessParamConstants.PKG);
        if (StringUtils.isBlank(appPkg)) {
            log.debug("app pkg not found!");
            printErrorMessage(response, USER_APP_MISMATCH_RESPONSE_RESULT);
            return false;
        }

        AppDTO appDTO = appService.find(appPkg);
        if (appDTO == null) {
            log.debug("app不存在,app pkg:{}", appPkg);
            printErrorMessage(response, USER_APP_MISMATCH_RESPONSE_RESULT);
            return false;
        }

        if (!appDTO.getId().equals(googleUserAuthDTO.getAppId())) {
            log.debug("用户与app不匹配,access token:{}, app pkg:{}", accessToken, appPkg);
            printErrorMessage(response, USER_APP_MISMATCH_RESPONSE_RESULT);
            return false;
        }

        return true;
    }
}
