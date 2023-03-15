package com.video.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.video.entity.*;
import com.video.user.cache.UserCache;
import com.video.user.constant.ThridConstants;
import com.video.user.domain.dto.app.AppDTO;
import com.video.user.domain.dto.user.UserAuthDTO;
import com.video.user.domain.dto.user.BaseUserDTO;
import com.video.user.exception.BusinessException;
import com.video.user.exception.ErrorCodeEnum;
import com.video.user.service.AppService;
import com.video.user.service.UserAuthService;
import com.video.user.service.BaseUserService;
import com.video.user.service.third.ThirdSmsFactoryService;
import com.video.user.service.third.ThirdSmsService;
import com.video.user.util.concurrent.DistributedLock;
import com.video.user.util.concurrent.DistributedLockFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BaseUserServiceImpl extends BaseServiceImpl implements BaseUserService {

    private static final String USER_GENERATE_LOCK_PREFIX = "user:generate:lock:tk:%s";

    private static final String USER_GENERATE_BY_OPEN_ID_LOCK_PREFIX = "user:generate:lock:open_id:%s";

    private static final String USER_LOGIN_BY_PHONE_LOCK_PREFIX = "user:login:phone:lock:phone:%s";

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 自己注入自己，保证调用内部的方法时事务不失效
     */
    @Autowired
    private BaseUserServiceImpl baseUserServiceImpl;

    @Autowired
    private AppService appService;

    @Autowired
    private ThirdSmsFactoryService thirdSmsFactoryService;

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Autowired
    private UserCache userCache;

    @Override
    public BaseUserDTO find(Long userId) {

        // 通过缓存查询对象
        BaseUserDTO baseUserDTO = userCache.getBaseUserDTOById(userId);
        if (baseUserDTO != null) {
            return baseUserDTO.getUserId() == null ? null : baseUserDTO;
        }

        BaseUserDO baseUserDO = baseUserMapper.selectById(userId);
        if (baseUserDO == null) {
            // 设置空缓存
            userCache.setBaseUserDTOByIdExpire(userId, null);
            return null;
        }

        baseUserDTO = new BaseUserDTO();
        baseUserDTO.setUserId(baseUserDO.getId());
        BeanUtils.copyProperties(baseUserDO, baseUserDTO);

        // 设置缓存
        userCache.setBaseUserDTOByIdExpire(userId, baseUserDTO);
        return baseUserDTO;
    }

    @Override
    public BaseUserDTO convertToAnonymous(BaseUserDTO baseUserDTO) {
        try {
            BaseUserDTO anonymouUser = baseUserDTO.copy();
            anonymouUser.setName(null);
            anonymouUser.setSex(null);
            anonymouUser.setAvatar(null);
            return anonymouUser;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("clone no supported.", e);
        }
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void realGenerate(String token, Long appId, String appVersion) {
        Date now = new Date();
        BaseUserDO baseUserDO = new BaseUserDO();
        baseUserDO.setCreateTime(now);
        baseUserDO.setAppId(appId);
        baseUserMapper.insert(baseUserDO);

        userAuthCache.delUserAuthDtoByTokenAndAppId(token, appId);
        userAuthService.create(baseUserDO.getId(), token, appId);
        userCache.delBaseUserDTOById(baseUserDO.getId());
    }

    @Override
    public void generate(String token, Long appId, String appVersion) {
        String lockKey = String.format(USER_GENERATE_LOCK_PREFIX, token);
        DistributedLock lock = distributedLockFactory.create(lockKey);
        // 第一次获取不是null就没必要走后面的加锁、创建用户的流程了(双锁检测)
        UserAuthDTO userAuthDTO = userAuthService.findByTokenAndAppId(token, appId);
        if (userAuthDTO == null) {
            try {
                lock.lock();
                userAuthDTO = userAuthService.findByTokenAndAppId(token, appId);
                // 二次检测, 可能不是第一个获取到锁的线程, 那么前面的线程已经成功创建了user数据到数据库中
                if (userAuthDTO == null) {
                    baseUserServiceImpl.realGenerate(token, appId, appVersion);
                }
            } catch (Exception e) {
                throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, String.format("DistributedLock lock error. %s", e.getMessage()));
            } finally {
                try {
                    lock.unlock();
                } catch (Exception e) {
                    throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, String.format("DistributedLock unlock error. %s", e.getMessage()));
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void realGenerate(Long appId, String appVersion, String name, UserSexEnum sex,
                             String avatar, String openId, String thirdPartyAccessToken) {
        Date now = new Date();
        BaseUserDO baseUserDO = new BaseUserDO();
        baseUserDO.setName(name);
        baseUserDO.setSex(sex);
        baseUserDO.setAvatar(avatar);
        baseUserDO.setCreateTime(now);
        baseUserDO.setAppId(appId);
        baseUserMapper.insert(baseUserDO);

        userAuthCache.delUserAuthDtoByOpenId(openId);
        userAuthService.createByOpenId(baseUserDO.getId(), openId, appId, thirdPartyAccessToken);
    }

    @Override
    public void generate(Long appId, String appVersion, String name, UserSexEnum sex,
                         String avatar, String openId, String thirdPartyAccessToken) {
        String lockKey = String.format(USER_GENERATE_BY_OPEN_ID_LOCK_PREFIX, openId);
        DistributedLock lock = distributedLockFactory.create(lockKey);
        // 第一次获取不是null就没必要走后面的加锁、创建用户的流程了(双锁检测)
        UserAuthDTO userAuthDTO = userAuthService.findByOpenId(openId);
        if (userAuthDTO == null) {
            try {
                lock.lock();
                userAuthDTO = userAuthService.findByOpenId(openId);
                // 二次检测, 可能不是第一个获取到锁的线程, 那么前面的线程已经成功创建了user数据到数据库中
                if (userAuthDTO == null) {
                    baseUserServiceImpl.realGenerate(appId, appVersion, name, sex, avatar, openId, thirdPartyAccessToken);
                }
            } catch (Exception e) {
                throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, String.format("DistributedLock lock error. %s", e.getMessage()));
            } finally {
                try {
                    lock.unlock();
                } catch (Exception e) {
                    throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, String.format("DistributedLock unlock error. %s", e.getMessage()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void bind(Long userId, String token, String name, UserSexEnum sex, String avatar, String openId,
                     String thirdPartyAccessToken, Long appId) {
        BaseUserDO baseUserDO = baseUserMapper.selectById(userId);
        baseUserDO.setName(name);
        baseUserDO.setSex(sex);
        baseUserDO.setAvatar(avatar);
        baseUserMapper.updateById(baseUserDO);

        userAuthService.bind(userId, appId, token, openId, thirdPartyAccessToken);
        userCache.delBaseUserDTOById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Long userId, String name, UserSexEnum sex, String avatar) {

        BaseUserDO baseUserDO = new BaseUserDO();
        baseUserDO.setId(userId);
        baseUserDO.setName(name);
        baseUserDO.setSex(sex);
        baseUserDO.setAvatar(avatar);
        baseUserMapper.updateById(baseUserDO);
        userCache.delBaseUserDTOById(userId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean phoneNumberAlreadyBind(Long appId, String phoneNumber) {
        List<BaseUserDO> baseUserDOList = baseUserMapper.selectList(
                new QueryWrapper<BaseUserDO>()
                        .eq("app_id", appId)
                        .eq("phone_number", phoneNumber));
        return !baseUserDOList.isEmpty();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindPhone(Long userId, String phoneNumber) {

        // TODO: 是否需要判断手机号绑定情况
        BaseUserDO baseUserDO = baseUserMapper.selectById(userId);
        if (phoneNumberAlreadyBind(baseUserDO.getAppId(), phoneNumber)) {
            log.info("Phone[{}] has bind.", phoneNumber);
            throw new BusinessException(ErrorCodeEnum.PHONE_HAS_BIND);
        }

        ThirdSmsService thirdSmsService =
                thirdSmsFactoryService.getSmsService(ThridConstants.ALI_CLOUD_SMS_SERVICE_NAME);

        BaseUserDO updateCondition = new BaseUserDO();
        updateCondition.setId(userId);
        updateCondition.setPhoneNumber(phoneNumber);
        baseUserMapper.updateById(updateCondition);

        // 清理手机验证码相关缓存
        boolean success = thirdSmsService.clearVerificationCode(phoneNumber);
        if (!success) {
            throw new BusinessException(ErrorCodeEnum.VERIFICATION_CODE_INVALID,
                    String.format("手机验证码失效或者已经被其他人使用, user:[%s], phone number:[%s]", userId, phoneNumber));
        }
        userCache.delBaseUserDTOById(userId);
        log.info("手机号绑定成功, user[{}], phone number:[{}]", userId, phoneNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserAuthDTO loginPhone(AppDTO appDTO, String tk, String phone) {
        String lockKey = String.format(USER_LOGIN_BY_PHONE_LOCK_PREFIX, phone);
        DistributedLock lock = distributedLockFactory.create(lockKey);
        // 第一次获取不是null就没必要走后面的加锁、创建用户的流程了(双锁检测)
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        try {
            lock.lock();
            BaseUserDO baseUserDO = userByPhoneNumber(appDTO.getId(), phone);
            // 二次检测, 可能不是第一个获取到锁的线程, 那么前面的线程已经成功创建了user数据到数据库中
            if (baseUserDO == null) {
                return baseUserServiceImpl.loginByPhone(appDTO, tk, phone);
            }
            UserAuthDO userAuthDO = userAuthMapper.selectByUserId(baseUserDO.getId());
            BeanUtils.copyProperties(userAuthDO, userAuthDTO);
            return userAuthDTO;
        }catch (BusinessException e) {
            log.error("userService:loginPhone:error: phone:{},  e:{}", phone, e);
            throw new BusinessException(ErrorCodeEnum.VERIFICATION_CODE_INVALID, String.format("DistributedLock lock error. %s", e.getMessage()));
        } catch (Exception e) {
            log.error("userService:loginPhone:error: phone:{},  e:{}", phone, e);
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, String.format("DistributedLock lock error. %s", e.getMessage()));
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, String.format("DistributedLock unlock error. %s", e.getMessage()));
            }
        }
    }

    public UserAuthDTO loginByPhone(AppDTO appDTO, String tk, String phone) {
        Date now = new Date();
        StringBuffer str = new StringBuffer();
        str.append(String.valueOf(now.getTime()).substring(2, 10));
        str.reverse();
        str.insert(0, "用户");
        String name = str.toString();
        BaseUserDO baseUserDO = new BaseUserDO();
        baseUserDO.setName(name);
        baseUserDO.setAvatar("https://xmall-res.xdplt.com/product/1642144382461/145503.png");
        baseUserDO.setCreateTime(now);
        baseUserDO.setAppId(appDTO.getId());
        baseUserDO.setPhoneNumber(phone);
        baseUserMapper.insert(baseUserDO);

        UserAuthDO userAuthDO =  userAuthService.createAuth(baseUserDO.getId(), tk, appDTO.getId());
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        BeanUtils.copyProperties(userAuthDO, userAuthDTO);
        return userAuthDTO;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseUserDO userByPhoneNumber(Long appId, String phoneNumber) {
        List<BaseUserDO> baseUserDOList = baseUserMapper.selectList(
                new QueryWrapper<BaseUserDO>()
                        .eq("app_id", appId)
                        .eq("phone_number", phoneNumber));

        if (CollectionUtils.isEmpty(baseUserDOList)) {
            return null;
        }
        return baseUserDOList.get(0);
    }
}
