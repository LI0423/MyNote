package com.video.user.controller;

import com.video.entity.BaseUserDO;
import com.video.entity.UserAuthDO;
import com.video.user.constant.BusinessHeaderConstants;
import com.video.user.constant.BusinessParamConstants;
import com.video.user.constant.BusinessPathVariableConstants;
import com.video.user.constant.ThridConstants;
import com.video.user.domain.common.ResponseResult;
import com.video.user.domain.dto.SimpleResultDTO;
import com.video.user.domain.dto.app.AppDTO;
import com.video.user.domain.dto.third.AuthDTO;
import com.video.user.domain.dto.third.UserInfoDTO;
import com.video.user.domain.dto.user.*;
import com.video.user.exception.BusinessException;
import com.video.user.exception.ErrorCodeEnum;
import com.video.user.service.third.ThirdAuthFactoryService;
import com.video.user.service.third.ThirdAuthService;
import com.video.user.service.third.ThirdSmsFactoryService;
import com.video.user.service.third.ThirdSmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {

    @Autowired
    private ThirdAuthFactoryService thirdAuthFactoryService;

    @Autowired
    private ThirdSmsFactoryService thirdSmsFactoryService;

    @RequestMapping("/login")
    public ResponseResult<BaseUserLoginResultDTO> login(
            @RequestParam(name = BusinessParamConstants.CODE, required = true) String code,
            @RequestParam(name = BusinessParamConstants.TYPE, required = true) String type,
            @RequestParam(name = BusinessParamConstants.PKG, required = true) String pkg,
            @RequestParam(name = BusinessParamConstants.TK, required = true) String tk,
            @RequestParam(name = BusinessParamConstants.VN, required = true) String vn) {

        // 1）获取app配置，顺便判断pkg的合法性
        AppDTO appDTO = appService.find(pkg);
        if (appDTO == null) {
            return ResponseResult.failure(ErrorCodeEnum.APP_INFO_NOT_FOUND);
        }

        ThirdAuthService thirdAuthService = thirdAuthFactoryService.getAuthService("WE_CHAT_AUTH");

        AuthDTO authDTO = thirdAuthService.auth(appDTO.getWeChatAppId(), appDTO.getWeChatAppSecret(), code);
        if (authDTO == null) {
            return ResponseResult.failure(ErrorCodeEnum.THIRD_AUTH_ERROR);
        }

        Boolean needAnonymousUser = appDTO.getNeedAnonymousUser();
        needAnonymousUser = Objects.nonNull(needAnonymousUser) && needAnonymousUser;

        UserAuthDTO userAuthDTO = userAuthService.findByOpenId(authDTO.getOpenId());
        BaseUserDTO baseUserDTO;
        String accessToken;
        if (Objects.nonNull(userAuthDTO)) { // 绑定微信号
            accessToken = userAuthService.updateAccessToken(userAuthDTO.getOpenId());
        } else if (needAnonymousUser) { // 是否需要匿名用户
            // 获取不到用户则尝试创建
            userAuthDTO = userAuthService.findByTokenAndAppId(tk, appDTO.getId());
            if (userAuthDTO == null) {
                baseUserService.generate(tk, appDTO.getId(), vn);
                userAuthDTO = userAuthService.findByTokenAndAppId(tk, appDTO.getId());
            }

            // 匿名用户已经被绑定过了
            if (userAuthDTO.getOpenId() != null) {
                log.info("Anonymous user has been bind. token[{}], bind open id:[{}], open id:[{}]",
                        tk, userAuthDTO.getOpenId(), authDTO.getOpenId());
                return ResponseResult.failure(ErrorCodeEnum.ANONYMOUS_USER_HAS_BEEN_BIND);
            }

            UserInfoDTO userInfoDTO = thirdAuthService.getUserInfo(authDTO.getAccessToken(), authDTO.getOpenId());
            if (userInfoDTO == null) {
                return ResponseResult.failure(ErrorCodeEnum.THIRD_AUTH_ERROR);
            }

            // 绑定微信用户
            baseUserService.bind(userAuthDTO.getUserId(), tk, userInfoDTO.getNickName(), userInfoDTO.getSex(),
                    userInfoDTO.getHeadImgUrl(), userInfoDTO.getOpenId(), authDTO.getAccessToken(), appDTO.getId());

            userAuthDTO = userAuthService.findByOpenId(authDTO.getOpenId());

            accessToken = userAuthDTO.getAccessToken();
        } else {
            UserInfoDTO userInfoDTO = thirdAuthService.getUserInfo(authDTO.getAccessToken(), authDTO.getOpenId());
            if (userInfoDTO == null) {
                return ResponseResult.failure(ErrorCodeEnum.THIRD_AUTH_ERROR);
            }

            baseUserService.generate(appDTO.getId(), vn, userInfoDTO.getNickName(), userInfoDTO.getSex(),
                    userInfoDTO.getHeadImgUrl(), userInfoDTO.getOpenId(), authDTO.getAccessToken());

            userAuthDTO = userAuthService.findByOpenId(authDTO.getOpenId());

            accessToken = userAuthDTO.getAccessToken();
        }

        // 记录 userId token 映射
        userAuthService.saveUserIdTokenMapping(pkg, userAuthDTO.getUserId(), tk);

        baseUserDTO = baseUserService.find(userAuthDTO.getUserId());

        BaseUserLoginResultDTO baseUserLoginResultDTO = new BaseUserLoginResultDTO();
        baseUserLoginResultDTO.setAccessToken(accessToken);

        BeanUtils.copyProperties(baseUserDTO, baseUserLoginResultDTO);
        return ResponseResult.success(baseUserLoginResultDTO);
    }

    @RequestMapping("/logout")
    public ResponseResult logout(
            @RequestHeader(value = BusinessHeaderConstants.ACCESS_TOKEN, required = true) String accessToken) {
        userAuthService.logout(accessToken);
        return ResponseResult.success(null);
    }

    @GetMapping("/{id}")
    public ResponseResult<BaseUserDTO> getUserInfoById(@PathVariable(BusinessPathVariableConstants.ID) Long userId) {

        // 获取用户信息
        BaseUserDTO baseUserDTO = baseUserService.find(userId);

        return ResponseResult.success(baseUserDTO);
    }

    @GetMapping
    public ResponseResult<BaseUserDTO> getUserInfo(
            @RequestHeader(value = BusinessHeaderConstants.ACCESS_TOKEN, required = false) String accessToken,
            @RequestParam(name = BusinessParamConstants.PKG, required = false) String pkg,
            @RequestParam(name = BusinessParamConstants.TK, required = false) String tk) {

        UserAuthDTO userAuthDTO = null;
        if (accessToken != null) {
            userAuthDTO = userAuthService.findByAccessToken(accessToken);
        } else if (pkg != null && tk != null){

            AppDTO appDTO = appService.find(pkg);
            if (appDTO == null) {
                throw new BusinessException(ErrorCodeEnum.APP_INFO_NOT_FOUND,
                        String.format("app[%s]数据查询不到", pkg));
            }

            userAuthDTO = userAuthService.findByTokenAndAppId(tk, appDTO.getId());
        } else {
            return ResponseResult.failure(ErrorCodeEnum.PARAM_MISSING);
        }

        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
            return ResponseResult.failure(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }

        Long userId = userAuthDTO.getUserId();

        // 获取用户信息
        BaseUserDTO baseUserDTO = baseUserService.find(userId);

        // 若没有accessToken说明没有登录，只返回匿名用户(游客)信息
        if (accessToken == null) {
            baseUserDTO = baseUserService.convertToAnonymous(baseUserDTO);
        }

        return ResponseResult.success(baseUserDTO);
    }

    @PostMapping
    public ResponseResult updateUserInfo(
            @RequestHeader(value = BusinessHeaderConstants.ACCESS_TOKEN, required = true) String accessToken,
            @Validated @RequestBody UserUpdateQueryDTO userUpdateQueryDTO) {
        if (userUpdateQueryDTO.getAvatar() == null
                && userUpdateQueryDTO.getName() == null
                && userUpdateQueryDTO.getSex() == null) {
            return ResponseResult.success(null);
        }

        UserAuthDTO userAuthDTO = userAuthService.findByAccessToken(accessToken);
        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
            return ResponseResult.failure(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }

        boolean success = baseUserService.update(userAuthDTO.getUserId(), userUpdateQueryDTO.getName(),
                userUpdateQueryDTO.getSex(), userUpdateQueryDTO.getAvatar());

        if (success) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.NO_ACTION);
        }
    }

    @PostMapping("/{id}")
    public ResponseResult updateUserInfo(
            @PathVariable(value = BusinessPathVariableConstants.ID, required = true) Long id,
            @Validated @RequestBody UserUpdateQueryDTO userUpdateQueryDTO) {
        if (userUpdateQueryDTO.getAvatar() == null
                && userUpdateQueryDTO.getName() == null
                && userUpdateQueryDTO.getSex() == null) {
            return ResponseResult.success(null);
        }

        boolean success = baseUserService.update(id, userUpdateQueryDTO.getName(),
                userUpdateQueryDTO.getSex(), userUpdateQueryDTO.getAvatar());

        if (success) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(ErrorCodeEnum.NO_ACTION);
        }
    }

    @PostMapping("/phone")
    public ResponseResult<SimpleResultDTO> bindPhone(
            @RequestHeader(value = BusinessHeaderConstants.ACCESS_TOKEN, required = true) String accessToken,
            @RequestParam(name = BusinessParamConstants.PKG) String pkg,
            @RequestBody BindPhoneQureyDTO bindPhoneQureyDTO) {

        AppDTO appDTO = appService.find(pkg);
        if (appDTO == null) {
            log.warn("app[{}]数据查询不到", pkg);
            return ResponseResult.failure(ErrorCodeEnum.APP_INFO_NOT_FOUND);
        }

        UserAuthDTO userAuthDTO = userAuthService.findByAccessToken(accessToken);
        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
            log.warn("user by access token[{}]  数据查询不到", accessToken);
            return ResponseResult.failure(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }

        ThirdSmsService thirdSmsService =
                thirdSmsFactoryService.getSmsService(ThridConstants.ALI_CLOUD_SMS_SERVICE_NAME);

        boolean success = thirdSmsService.checkVerificationCode(bindPhoneQureyDTO.getPhone(), bindPhoneQureyDTO.getCode());

        if (!success) {
            log.info("phone number and verification code do not match. phone number:[{}], code:[{}]",
                    bindPhoneQureyDTO.getPhone(), bindPhoneQureyDTO.getCode());
            return ResponseResult.failure(ErrorCodeEnum.PARAM_ERROR);
        }

        baseUserService.bindPhone(userAuthDTO.getUserId(), bindPhoneQureyDTO.getPhone());

        SimpleResultDTO simpleResultDTO = new SimpleResultDTO();
        simpleResultDTO.setSuccess(true);
        simpleResultDTO.setMessage("ok");
        return ResponseResult.success(simpleResultDTO);
    }

    @GetMapping("/message/code")
    public ResponseResult<MessageCodeResultDTO> sendVerificationCode(
            @RequestParam(name = BusinessParamConstants.PKG) String pkg,
            @RequestParam(name = BusinessParamConstants.PHONE_NUMBER) String phoneNumber) {

        AppDTO appDTO = appService.find(pkg);
        if (appDTO == null) {
            log.warn("app[{}]数据查询不到", pkg);
            return ResponseResult.failure(ErrorCodeEnum.APP_INFO_NOT_FOUND);
        }

//        UserAuthDTO userAuthDTO = userAuthService.findByAccessToken(accessToken);
//        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
//            log.warn("user by access token[{}]  数据查询不到", accessToken);
//            return ResponseResult.failure(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
//        }

        ThirdSmsService thirdSmsService =
                thirdSmsFactoryService.getSmsService(ThridConstants.ALI_CLOUD_SMS_SERVICE_NAME);

        Boolean success = thirdSmsService
                .sendVerificationCodeSms(appDTO.getId(), phoneNumber, thirdSmsService.createVerificationCode());

        return ResponseResult.success(new MessageCodeResultDTO(success, null));
    }

    /**
     * 手机号登陆
     * @param pkg
     * @param tk
     * @param bindPhoneQureyDTO
     * @return
     */
    @PostMapping("/loginPhone")
    public ResponseResult<BaseUserLoginResultDTO> login(
            @RequestParam(name = BusinessParamConstants.PKG) String pkg,
            @RequestParam(name = BusinessParamConstants.TK, required = true) String tk,
            @RequestBody BindPhoneQureyDTO bindPhoneQureyDTO) {

        // 1）获取app配置，顺便判断pkg的合法性
        AppDTO appDTO = appService.find(pkg);
        if (appDTO == null) {
            return ResponseResult.failure(ErrorCodeEnum.APP_INFO_NOT_FOUND);
        }

        //验证码校验
        ThirdSmsService thirdSmsService =
                thirdSmsFactoryService.getSmsService(ThridConstants.ALI_CLOUD_SMS_SERVICE_NAME);
        boolean success = thirdSmsService.checkVerificationCode(bindPhoneQureyDTO.getPhone(), bindPhoneQureyDTO.getCode());
        if (!success) {
            log.info("phone number and verification code do not match. phone number:[{}], code:[{}]",
                    bindPhoneQureyDTO.getPhone(), bindPhoneQureyDTO.getCode());
            return ResponseResult.failure(ErrorCodeEnum.VERIFICATION_CODE_INVALID);
        }

        //是否存在
        BaseUserDO baseUserDO = baseUserService.userByPhoneNumber(appDTO.getId(), bindPhoneQureyDTO.getPhone());
        UserAuthDTO userAuthDTO;
        Long userId;
        //未注册过
        if (ObjectUtils.isEmpty(baseUserDO)) {
            //注册
            userAuthDTO = baseUserService.loginPhone(appDTO, tk, bindPhoneQureyDTO.getPhone());
            userId = userAuthDTO.getUserId();
        } else {
            //更新token
            userAuthDTO = userAuthService.updateAccessTokenByUserId(baseUserDO.getId());
            userId = baseUserDO.getId();
        }

        // 记录 userId token 映射
        userAuthService.saveUserIdTokenMapping(pkg, userId, tk);

        BaseUserDTO baseUserDTO = baseUserService.find(userId);
        BaseUserLoginResultDTO baseUserLoginResultDTO = new BaseUserLoginResultDTO();
        baseUserLoginResultDTO.setAccessToken(userAuthDTO.getAccessToken());

        BeanUtils.copyProperties(baseUserDTO, baseUserLoginResultDTO);
        return ResponseResult.success(baseUserLoginResultDTO);
    }

    /**
     * 需求未定， 待测试，请勿使用
     *
     * 微信绑定
     * @param code
     * @param tk
     * @param accessToken
     * @param pkg
     * @return
     */
    @RequestMapping("/bindWeChat")
    public ResponseResult<SimpleResultDTO> bindWeChat(
            @RequestParam(name = BusinessParamConstants.CODE, required = true) String code,
            @RequestParam(name = BusinessParamConstants.TK, required = true) String tk,
            @RequestHeader(value = BusinessHeaderConstants.ACCESS_TOKEN, required = true) String accessToken,
            @RequestParam(name = BusinessParamConstants.PKG) String pkg) {

        // 1）获取app配置，顺便判断pkg的合法性
        AppDTO appDTO = appService.find(pkg);
        if (appDTO == null) {
            return ResponseResult.failure(ErrorCodeEnum.APP_INFO_NOT_FOUND);
        }

        //获取微信认证服务
        ThirdAuthService thirdAuthService = thirdAuthFactoryService.getAuthService("WE_CHAT_AUTH");
        AuthDTO authDTO = thirdAuthService.auth(appDTO.getWeChatAppId(), appDTO.getWeChatAppSecret(), code);
        if (authDTO == null) {
            return ResponseResult.failure(ErrorCodeEnum.THIRD_AUTH_ERROR);
        }
        //获取微信认证信息
        UserInfoDTO userInfoDTO = thirdAuthService.getUserInfo(authDTO.getAccessToken(), authDTO.getOpenId());
        if (userInfoDTO == null) {
            return ResponseResult.failure(ErrorCodeEnum.THIRD_AUTH_ERROR);
        }
        //校验是否已经绑定过
        UserAuthDTO oldUserAuthDTO = userAuthService.findByOpenId(userInfoDTO.getOpenId());
        if (!ObjectUtils.isEmpty(oldUserAuthDTO)) {
            return ResponseResult.failure(ErrorCodeEnum.WECHAT_HAS_BIND);
        }
        //token获取用户信息
        UserAuthDTO userAuthDTO = userAuthService.findByAccessToken(accessToken);
        if (userAuthDTO == null || userAuthDTO.getUserId() == null) {
            log.warn("user by access token[{}]  数据查询不到", accessToken);
            return ResponseResult.failure(ErrorCodeEnum.USER_AUTH_INFO_NOT_FOUND);
        }

        //绑定
        baseUserService.bind(userAuthDTO.getUserId(), tk, userInfoDTO.getNickName(), userInfoDTO.getSex(),
                userInfoDTO.getHeadImgUrl(), userInfoDTO.getOpenId(), authDTO.getAccessToken(), appDTO.getId());

        SimpleResultDTO simpleResultDTO = new SimpleResultDTO();
        simpleResultDTO.setSuccess(true);
        simpleResultDTO.setMessage("ok");
        return ResponseResult.success(simpleResultDTO);
    }

}
