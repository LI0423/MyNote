package com.video.oversea.user.service.impl;

import com.video.entity.BaseUserDO;
import com.video.oversea.user.cache.GoogleUserAuthCache;
import com.video.oversea.user.cache.UserCache;
import com.video.oversea.user.domain.dto.user.BaseUserDTO;
import com.video.oversea.user.domain.dto.user.GoogleUserAuthDTO;
import com.video.oversea.user.exception.BusinessException;
import com.video.oversea.user.exception.ErrorCodeEnum;
import com.video.oversea.user.service.BaseUserService;
import com.video.oversea.user.service.third.google.GoogleUserAuthService;
import com.video.oversea.user.util.concurrent.DistributedLock;
import com.video.oversea.user.util.concurrent.DistributedLockFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class BaseUserServiceImpl extends BaseServiceImpl implements BaseUserService {

    private static final String USER_GENERATE_LOCK_PREFIX = "user:generate:lock:tk:%s";

    private static final String USER_GENERATE_BY_OPEN_ID_LOCK_PREFIX = "user:generate:lock:open_id:%s";

    private static final String USER_GENERATE_BY_GOOGLE_USER_ID_LOCK_PREFIX = "user:generate:lock:google_user_id:%s";

    /**
     * 自己注入自己，保证调用内部的方法时事务不失效
     */
    @Autowired
    private BaseUserServiceImpl baseUserServiceImpl;

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Autowired
    private UserCache userCache;

    @Autowired
    private GoogleUserAuthService googleUserAuthService;

    @Autowired
    private GoogleUserAuthCache googleUserAuthCache;

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
    public void googleGenerate(Long appId, String appVersion, String googleUserId, String googleUserName, String googleUserPicture) {
        String lockKey = String.format(USER_GENERATE_BY_GOOGLE_USER_ID_LOCK_PREFIX, googleUserId);
        DistributedLock lock = distributedLockFactory.create(lockKey);
        // 第一次获取不是null就没必要走后面的加锁、创建用户的流程了(双锁检测)
        GoogleUserAuthDTO googleUserAuthDTO = googleUserAuthService.findByGoogleUserId(googleUserId);
        if (googleUserAuthDTO == null) {
            try {
                lock.lock();
                googleUserAuthDTO = googleUserAuthService.findByGoogleUserId(googleUserId);
                // 二次检测, 可能不是第一个获取到锁的线程, 那么前面的线程已经成功创建了user数据到数据库中
                if (googleUserAuthDTO == null) {
                    baseUserServiceImpl.realGoogleGenerate(appId, appVersion, googleUserId, googleUserName, googleUserPicture);
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
    public void realGoogleGenerate(Long appId, String appVersion,
                                   String googleUserId, String googleUserName, String googleUserPicture) {
        Date now = new Date();
        BaseUserDO baseUserDO = new BaseUserDO();
        baseUserDO.setName(googleUserName);
        baseUserDO.setSex(null);
        baseUserDO.setAvatar(googleUserPicture);
        baseUserDO.setCreateTime(now);
        baseUserDO.setAppId(appId);
        baseUserMapper.insert(baseUserDO);

        googleUserAuthCache.delGoogleUserAuthDtoByGoogleUserId(googleUserId);
        googleUserAuthService.createByGoogleUserId(baseUserDO.getId(), googleUserId, appId);
    }
}
