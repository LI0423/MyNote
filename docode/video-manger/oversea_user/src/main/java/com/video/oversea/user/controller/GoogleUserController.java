package com.video.oversea.user.controller;

import com.video.oversea.user.constant.BusinessHeaderConstants;
import com.video.oversea.user.constant.BusinessParamConstants;
import com.video.oversea.user.domain.common.ResponseResult;
import com.video.oversea.user.domain.dto.app.AppDTO;
import com.video.oversea.user.domain.dto.third.google.GoogleUserDTO;
import com.video.oversea.user.domain.dto.user.BaseUserDTO;
import com.video.oversea.user.domain.dto.user.BaseUserLoginResultDTO;
import com.video.oversea.user.domain.dto.user.GoogleUserAuthDTO;
import com.video.oversea.user.exception.ErrorCodeEnum;
//import com.video.oversea.user.service.UserAuthService;
import com.video.oversea.user.service.third.google.GoogleAuthService;
import com.video.oversea.user.service.third.google.GoogleUserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/googleUsers")
public class GoogleUserController extends BaseController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private GoogleUserAuthService googleUserAuthService;

    @GetMapping("/login")
    public ResponseResult login(@RequestParam(name = BusinessParamConstants.CODE, required = true) String idToken,
                                @RequestParam(name = BusinessParamConstants.TYPE, required = true) String type,
                                @RequestParam(name = BusinessParamConstants.PKG, required = true) String pkg,
                                @RequestParam(name = BusinessParamConstants.TK, required = true) String tk,
                                @RequestParam(name = BusinessParamConstants.VN, required = true) String vn) {

        BaseUserDTO baseUserDTO;
        String accessToken;
        // 1）获取app配置，顺便判断pkg的合法性
        AppDTO appDTO = appService.find(pkg);
        if (appDTO == null) {
            return ResponseResult.failure(ErrorCodeEnum.APP_INFO_NOT_FOUND);
        }

        // 2) 谷歌用户认证
        GoogleUserDTO googleUserDTO = googleAuthService.verify(pkg, idToken);

        if (googleUserDTO == null) {
            log.error("idToken:{},verify fail, idToken verify error", idToken);
            return ResponseResult.failure(ErrorCodeEnum.THIRD_AUTH_ERROR);
        }

        // 3) 查询用户是否已存在，不存在就创建新用户
        GoogleUserAuthDTO googleUserAuthDTO = googleUserAuthService.findByGoogleUserId(googleUserDTO.getGoogleUserId());
        if (googleUserAuthDTO == null) {
            baseUserService.googleGenerate(appDTO.getId(), vn, googleUserDTO.getGoogleUserId(), googleUserDTO.getName(), googleUserDTO.getPicture());

            googleUserAuthDTO = googleUserAuthService.findByGoogleUserId(googleUserDTO.getGoogleUserId());

            accessToken = googleUserAuthDTO.getAccessToken();
        } else {
            accessToken = googleUserAuthService.updateAccessToken(googleUserDTO.getGoogleUserId());
        }

        // 4) 查询基本用户信息
        baseUserDTO = baseUserService.find(googleUserAuthDTO.getUserId());

        BaseUserLoginResultDTO baseUserLoginResultDTO = new BaseUserLoginResultDTO();
        baseUserLoginResultDTO.setAccessToken(accessToken);

        BeanUtils.copyProperties(baseUserDTO, baseUserLoginResultDTO);
        return ResponseResult.success(baseUserLoginResultDTO);
    }

    @GetMapping
    public ResponseResult getUserInfo(@RequestHeader(value = BusinessHeaderConstants.ACCESS_TOKEN, required = false) String accessToken) {

        GoogleUserAuthDTO googleUserAuthDTO = null;
        if (accessToken != null) {
            googleUserAuthDTO = googleUserAuthService.findByAccessToken(accessToken);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.PARAM_MISSING);
        }

        if (googleUserAuthDTO == null || googleUserAuthDTO.getUserId() == null) {
            return ResponseResult.failure(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }

        Long userId = googleUserAuthDTO.getUserId();

        // 获取用户信息
        BaseUserDTO baseUserDTO = baseUserService.find(userId);

        return ResponseResult.success(baseUserDTO);
    }

    @RequestMapping("/logout")
    public ResponseResult logout(@RequestHeader(value = BusinessHeaderConstants.ACCESS_TOKEN, required = true) String accessToken) {
        googleUserAuthService.logout(accessToken);
        return ResponseResult.success(null);
    }
}
